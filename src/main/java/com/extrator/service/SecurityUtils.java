package com.extrator.util;

public class SecurityUtils {

    /**
     * Verifica se a URL é válida e pertence ao YouTube.
     *
     * @param url URL informada pelo usuário
     * @return true se for uma URL válida do YouTube
     */
    public static boolean validarUrlYoutube(String url) {
        if (url == null || url.isBlank()) return false;

        String lower = url.toLowerCase();
        return (lower.startsWith("http://") || lower.startsWith("https://"))
                && (lower.contains("youtube.com") || lower.contains("youtu.be"));
    }

    /**
     * Sanitiza nomes de arquivos para evitar caracteres perigosos.
     *
     * @param nome título ou nome original
     * @return nome seguro para uso em arquivos
     */
    public static String sanitizarNomeArquivo(String nome) {
        if (nome == null) return "arquivo";

        String sanitizado = nome
                .replaceAll("[^\\p{L}\\p{N}\\s-_]", "") // mantém letras, números, espaços, hífen e underscore
                .trim()
                .replaceAll("\\s+", "_"); // substitui espaços por underscore

        if (sanitizado.isEmpty()) {
            sanitizado = "arquivo";
        }

        // Limita tamanho para evitar problemas em sistemas de arquivos
        if (sanitizado.length() > 80) {
            sanitizado = sanitizado.substring(0, 80);
        }

        return sanitizado;
    }
}