package com.extrator.service;

import com.extrator.model.VideoRequest;
import com.extrator.model.VideoTask;
import com.extrator.util.FileNamingService;
import com.extrator.util.ZipService;
import com.extrator.util.CleanupService;
import com.extrator.util.ProgressService;
import com.extrator.util.SecurityUtils;
import com.extrator.util.BitrateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoService.class);

    private final Map<Long, VideoTask> tarefas = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private final FfmpegService ffmpegService;
    private final PlaylistService playlistService;
    private final BitrateService bitrateService;

    public VideoService(FfmpegService ffmpegService) {
        this.ffmpegService = ffmpegService;
        this.playlistService = new PlaylistService(ffmpegService);
        this.bitrateService = new BitrateService(ffmpegService);
    }

    // Cria uma nova tarefa
    public VideoTask criarTarefa(VideoRequest request) {
        Long id = idGenerator.getAndIncrement();

        VideoTask task = new VideoTask(
                id,
                request.getUrl(),
                "PENDENTE",
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<>()
        );

        tarefas.put(id, task);

        executor.submit(() -> processarTarefa(task, request));

        return task;
    }

    // Consulta status
    public VideoTask consultarTarefa(Long id) {
        return tarefas.getOrDefault(id, null);
    }

    // Resultado da tarefa
    public VideoTask resultadoTarefa(Long id) {
        return tarefas.get(id);
    }

    // Processamento real
    private void processarTarefa(VideoTask task, VideoRequest request) {
        try {
            if (!SecurityUtils.validarUrlYoutube(request.getUrl())) {
                task.setStatus("ERRO");
                task.setProgressMsg("URL inválida ou não suportada.");
                return;
            }

            task.setStatus("PROCESSANDO");
            task.setUpdatedAt(LocalDateTime.now());

            String pastaSaida = "output/";
            new File(pastaSaida).mkdirs();

            List<String> arquivosGerados = new ArrayList<>();

            // Inicializa progresso
            ProgressService.iniciar(task, 4); // download + cortes/playlist + zip + limpeza

            // Detecta se é playlist
            if (request.getUrl().contains("list=")) {
                ProgressService.atualizar(task, "Baixando playlist...");
                arquivosGerados = playlistService.processarPlaylist(request.getUrl(), pastaSaida, request.getBitrate());
            } else {
                ProgressService.atualizar(task, "Baixando vídeo...");
                String destino = pastaSaida + "video_" + task.getId() + ".mp3";
                ffmpegService.baixarAudio(request.getUrl(), destino);

                if (request.getTextoTimestamps() != null && !request.getTextoTimestamps().isBlank()) {
                    ProgressService.atualizar(task, "Processando cortes...");
                    List<com.extrator.util.TimestampParser.TimestampInfo> infos =
                            com.extrator.util.TimestampParser.parse(request.getTextoTimestamps());
                    int i = 1;
                    for (var info : infos) {
                        if (info.getDuracao() == null) continue;
                        String nomeArquivo = FileNamingService.gerarNomeArquivo(i, info.getTitulo());
                        String destinoCorte = pastaSaida + nomeArquivo;
                        bitrateService.cortarComBitrate(destino, info.getInicio(), info.getDuracao(),
                                destinoCorte, request.getBitrate());
                        arquivosGerados.add(destinoCorte);
                        i++;
                    }
                } else {
                    ProgressService.atualizar(task, "Aplicando bitrate...");
                    String destinoFinal = pastaSaida + "video_" + task.getId() + ".mp3";
                    bitrateService.aplicarBitrate(destino, destinoFinal, request.getBitrate());
                    arquivosGerados.add(destinoFinal);
                }
            }

            ProgressService.atualizar(task, "Compactando arquivos...");
            String zipFile = pastaSaida + "video_" + task.getId() + ".zip";
            ZipService.criarZip(arquivosGerados, zipFile);

            task.setResultFiles(Collections.singletonList("output/video_" + task.getId() + ".zip"));
            task.setStatus("CONCLUIDO");
            task.setUpdatedAt(LocalDateTime.now());

            ProgressService.concluir(task);

            // Agenda limpeza automática após 1 hora
            List<String> todosArquivos = new ArrayList<>(arquivosGerados);
            todosArquivos.add(zipFile);
            CleanupService.agendarLimpeza(todosArquivos, 1);

            log.info("Tarefa {} concluída com sucesso", task.getId());

        } catch (Exception e) {
            task.setStatus("ERRO");
            task.setUpdatedAt(LocalDateTime.now());
            ProgressService.erro(task, e.getMessage());
            log.error("Erro na tarefa {}: {}", task.getId(), e.getMessage());
        }
    }
}