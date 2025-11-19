package com.extrator.util;

import java.io.File;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CleanupService {

    // Executor único para agendar tarefas de limpeza
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Agenda a limpeza de arquivos após um tempo definido.
     *
     * @param arquivos lista de caminhos absolutos dos arquivos a serem apagados
     * @param horas    tempo em horas até a limpeza
     */
    public static void agendarLimpeza(List<String> arquivos, int horas) {
        scheduler.schedule(() -> {
            for (String caminho : arquivos) {
                try {
                    File file = new File(caminho);
                    if (file.exists()) {
                        boolean deletado = file.delete();
                        System.out.println("CleanupService: Arquivo " + caminho +
                                (deletado ? " removido com sucesso." : " não pôde ser removido."));
                    }
                } catch (Exception e) {
                    System.err.println("CleanupService: Erro ao remover arquivo " + caminho + " - " + e.getMessage());
                }
            }
        }, horas, TimeUnit.HOURS);
    }
}