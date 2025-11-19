package com.extrator.util;

import java.util.ArrayList;
import java.util.List;

public class TimestampParser {

    public static class TimestampInfo {
        private String inicio;
        private String duracao;
        private String titulo;

        public TimestampInfo(String inicio, String duracao, String titulo) {
            this.inicio = inicio;
            this.duracao = duracao;
            this.titulo = titulo;
        }

        public String getInicio() { return inicio; }
        public String getDuracao() { return duracao; }
        public String getTitulo() { return titulo; }
    }

    // Converte "HH:MM:SS" ou "MM:SS" em segundos
    private static long toSeconds(String tempo) {
        String[] partes = tempo.split(":");
        int h = 0, m = 0, s = 0;
        if (partes.length == 3) {
            h = Integer.parseInt(partes[0]);
            m = Integer.parseInt(partes[1]);
            s = Integer.parseInt(partes[2]);
        } else if (partes.length == 2) {
            m = Integer.parseInt(partes[0]);
            s = Integer.parseInt(partes[1]);
        } else {
            s = Integer.parseInt(partes[0]);
        }
        return h * 3600 + m * 60 + s;
    }

    // Converte segundos em HH:MM:SS
    private static String toHHMMSS(long segundos) {
        long h = segundos / 3600;
        long m = (segundos % 3600) / 60;
        long s = segundos % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    // Faz o parsing do texto bruto
    public static List<TimestampInfo> parse(String texto) {
        List<TimestampInfo> lista = new ArrayList<>();
        String[] linhas = texto.split("\\r?\\n");

        List<Long> tempos = new ArrayList<>();
        List<String> titulos = new ArrayList<>();

        for (String linha : linhas) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;

            // Divide em tempo + resto
            String[] partes = linha.split("\\s+", 2);
            String tempo = partes[0];
            String titulo = partes.length > 1 ? partes[1].replaceFirst("^-\\s*", "") : "";

            tempos.add(toSeconds(tempo));
            titulos.add(titulo);
        }

        // Calcula durações
        for (int i = 0; i < tempos.size(); i++) {
            long inicio = tempos.get(i);
            long fim = (i < tempos.size() - 1) ? tempos.get(i + 1) : -1;
            long duracaoSegundos = (fim > 0) ? (fim - inicio) : 0;

            String inicioFmt = toHHMMSS(inicio);
            String duracaoFmt = duracaoSegundos > 0 ? toHHMMSS(duracaoSegundos) : null;
            String titulo = titulos.get(i);

            lista.add(new TimestampInfo(inicioFmt, duracaoFmt, titulo));
        }

        return lista;
    }
}