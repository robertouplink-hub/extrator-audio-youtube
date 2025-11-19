package com.extrator.util;

public class FileNamingService {

    /**
     * Gera um nome de arquivo no formato:
     * 01 - Nome_da_Musica.mp3
     *
     * @param ordem  número da música na ordem dos timestamps
     * @param titulo título da música informado pelo usuário
     * @return nome do arquivo formatado
     */
    public static String gerarNomeArquivo(int ordem, String titulo) {
        // Sanitiza o título: remove caracteres perigosos e normaliza espaços
        String tituloSanitizado = titulo
                .replaceAll("[^\\p{L}\\p{N}\\s-]", "") // mantém letras, números, espaços e hífen
                .trim()
                .replaceAll("\\s+", "_"); // substitui espaços por underscore

        // Limita tamanho para evitar problemas em sistemas de arquivos
        if (tituloSanitizado.length() > 80) {
            tituloSanitizado = tituloSanitizado.substring(0, 80);
        }

        // Formata com dois dígitos na ordem
        return String.format("%02d - %s.mp3", ordem, tituloSanitizado);
    }
}