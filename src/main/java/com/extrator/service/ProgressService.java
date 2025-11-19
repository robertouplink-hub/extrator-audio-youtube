package com.extrator.util;

import com.extrator.model.VideoTask;

public class ProgressService {

    /**
     * Inicializa o progresso da tarefa com número total de etapas.
     *
     * @param task       tarefa que será monitorada
     * @param totalSteps número total de etapas previstas
     */
    public static void iniciar(VideoTask task, int totalSteps) {
        task.setTotalSteps(totalSteps);
        task.setCompletedSteps(0);
        task.setProgressMsg("Tarefa iniciada...");
    }

    /**
     * Atualiza o progresso da tarefa.
     *
     * @param task     tarefa que será monitorada
     * @param mensagem mensagem amigável sobre a etapa atual
     */
    public static void atualizar(VideoTask task, String mensagem) {
        task.setCompletedSteps(task.getCompletedSteps() + 1);
        task.setProgressMsg(mensagem);
    }

    /**
     * Finaliza a tarefa com mensagem de conclusão.
     *
     * @param task tarefa que será monitorada
     */
    public static void concluir(VideoTask task) {
        task.setCompletedSteps(task.getTotalSteps());
        task.setProgressMsg("Tarefa concluída!");
    }

    /**
     * Marca erro na tarefa.
     *
     * @param task tarefa que será monitorada
     * @param erro mensagem de erro
     */
    public static void erro(VideoTask task, String erro) {
        task.setProgressMsg("Erro: " + erro);
    }
}