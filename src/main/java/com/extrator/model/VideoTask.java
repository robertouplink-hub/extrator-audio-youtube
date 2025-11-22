package com.extrator.model;

import java.time.LocalDateTime;
import java.util.List;

public class VideoTask {

    private Long id;
    private String url;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> resultFiles;

    private int totalSteps;
    private int completedSteps;
    private String progressMsg;

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

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<String> getResultFiles() {
        return resultFiles;
    }

    public int getTotalSteps() {
        return totalSteps;
    }

    public int getCompletedSteps() {
        return completedSteps;
    }

    public String getProgressMsg() {
        return progressMsg;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setResultFiles(List<String> resultFiles) {
        this.resultFiles = resultFiles;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    public void setCompletedSteps(int completedSteps) {
        this.completedSteps = completedSteps;
    }

    public void setProgressMsg(String progressMsg) {
        this.progressMsg = progressMsg;
    }
}