package com.extrator.model;

import java.time.LocalDateTime;
import java.util.List;

public class VideoTask {

    private Long id;                  // Identificador único da tarefa
    private String url;               // URL do vídeo
    private String status;            // Status da tarefa (ex: "PENDENTE", "PROCESSANDO", "CONCLUIDO", "ERRO")
    private LocalDateTime createdAt;  // Data/hora de criação
    private LocalDateTime updatedAt;  // Última atualização
    private List<String> resultFiles; // Lista de arquivos gerados (links ou nomes)

    // Construtor vazio
    public VideoTask() {}

    // Construtor completo
    public VideoTask(Long id, String url, String status,
                     LocalDateTime createdAt, LocalDateTime updatedAt,
                     List<String> resultFiles) {
        this.id = id;
        this.url = url;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.resultFiles = resultFiles;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getResultFiles() {
        return resultFiles;
    }

    public void setResultFiles(List<String> resultFiles) {
        this.resultFiles = resultFiles;
    }
}