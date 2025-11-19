package com.extrator.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class FfmpegService {

    // Executa um comando externo e retorna a saída como String
    public String executarComando(String... comando) {
        StringBuilder saida = new StringBuilder();
        try {
            ProcessBuilder pb = new ProcessBuilder(comando);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    saida.append(linha).append("\n");
                }
            }

            int exitCode = process.waitFor();
            saida.append("Processo finalizado com código: ").append(exitCode).append("\n");
        } catch (Exception e) {
            saida.append("Erro ao executar comando: ").append(e.getMessage()).append("\n");
        }
        return saida.toString();
    }

    // Baixa áudio de um vídeo usando yt-dlp (formato original)
    public String baixarAudio(String url, String destino) {
        return executarComando("yt-dlp", "-x", "--audio-format", "mp3", "-o", destino, url);
    }

    // Corta trecho de áudio usando ffmpeg com bitrate configurável
    public String cortarTrecho(String arquivoEntrada, String inicio, String duracao, String arquivoSaida, String bitrate) {
        return executarComando("ffmpeg", "-y", "-i", arquivoEntrada,
                "-ss", inicio, "-t", duracao, "-b:a", bitrate, arquivoSaida);
    }

    // Converte arquivo inteiro para bitrate desejado
    public String aplicarBitrate(String arquivoEntrada, String arquivoSaida, String bitrate) {
        return executarComando("ffmpeg", "-y", "-i", arquivoEntrada,
                "-b:a", bitrate, arquivoSaida);
    }

    // Extrai capítulos do vídeo usando yt-dlp
    public String extrairCapitulos(String url) {
        return executarComando("yt-dlp", "--print", "chapters", url);
    }
}