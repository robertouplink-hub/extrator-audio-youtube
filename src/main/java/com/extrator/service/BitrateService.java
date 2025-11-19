package com.extrator.util;

import com.extrator.service.FfmpegService;

public class BitrateService {

    private final FfmpegService ffmpegService;

    public BitrateService(FfmpegService ffmpegService) {
        this.ffmpegService = ffmpegService;
    }

    /**
     * Aplica o bitrate desejado a um arquivo MP3.
     *
     * @param arquivoEntrada caminho do arquivo original
     * @param arquivoSaida   caminho do arquivo convertido
     * @param bitrate        taxa de compressão (ex.: "128k", "192k", "256k", "320k")
     * @return saída do processo ffmpeg
     */
    public String aplicarBitrate(String arquivoEntrada, String arquivoSaida, String bitrate) {
        return ffmpegService.executarComando(
                "ffmpeg", "-y", "-i", arquivoEntrada,
                "-b:a", bitrate,
                arquivoSaida
        );
    }

    /**
     * Corta um trecho de áudio aplicando o bitrate desejado.
     *
     * @param arquivoEntrada caminho do arquivo original
     * @param inicio         tempo inicial (ex.: "00:00:30")
     * @param duracao        duração do trecho (ex.: "00:03:15")
     * @param arquivoSaida   caminho do arquivo convertido
     * @param bitrate        taxa de compressão (ex.: "128k", "192k", "256k", "320k")
     * @return saída do processo ffmpeg
     */
    public String cortarComBitrate(String arquivoEntrada, String inicio, String duracao,
                                   String arquivoSaida, String bitrate) {
        return ffmpegService.executarComando(
                "ffmpeg", "-y", "-i", arquivoEntrada,
                "-ss", inicio, "-t", duracao,
                "-b:a", bitrate,
                arquivoSaida
        );
    }
}