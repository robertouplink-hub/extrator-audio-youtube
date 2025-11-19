package com.extrator.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlaylistService {

    private final FfmpegService ffmpegService;

    public PlaylistService(FfmpegService ffmpegService) {
        this.ffmpegService = ffmpegService;
    }

    /**
     * Processa uma playlist do YouTube, baixando todos os vídeos e convertendo para MP3.
     *
     * @param url        URL da playlist
     * @param pastaSaida pasta onde os arquivos serão salvos
     * @param bitrate    taxa de compressão MP3 (ex.: "128k", "192k", "256k", "320k")
     * @return lista de arquivos MP3 gerados
     */
    public List<String> processarPlaylist(String url, String pastaSaida, String bitrate) {
        List<String> arquivosGerados = new ArrayList<>();
        new File(pastaSaida).mkdirs();

        try {
            // yt-dlp baixa playlist inteira
            // -o "%(playlist_index)s - %(title)s.%(ext)s" garante nomes bonitos
            String comando = ffmpegService.executarComando(
                    "yt-dlp",
                    "-x",
                    "--audio-format", "mp3",
                    "--audio-quality", bitrate,
                    "--yes-playlist",
                    "-o", pastaSaida + "%(playlist_index)s - %(title)s.%(ext)s",
                    url
            );

            System.out.println("PlaylistService: Download concluído\n" + comando);

            // Varre a pasta e coleta todos os arquivos MP3 gerados
            File pasta = new File(pastaSaida);
            File[] files = pasta.listFiles((dir, name) -> name.endsWith(".mp3"));
            if (files != null) {
                for (File f : files) {
                    arquivosGerados.add(f.getPath());
                }
            }

        } catch (Exception e) {
            System.err.println("PlaylistService: Erro ao processar playlist - " + e.getMessage());
        }

        return arquivosGerados;
    }
}