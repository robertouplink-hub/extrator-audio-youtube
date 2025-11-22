package com.extrator.model;

public class VideoRequest {

    private String url;
    private String bitrate;
    private String textoTimestamps;

    public VideoRequest() {
        // Construtor padrão necessário para desserialização JSON
    }

    public VideoRequest(String url, String bitrate, String textoTimestamps) {
        this.url = url;
        this.bitrate = bitrate;
        this.textoTimestamps = textoTimestamps;
    }

    // Getters e Setters

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }

    public String getTextoTimestamps() {
        return textoTimestamps;
    }

    public void setTextoTimestamps(String textoTimestamps) {
        this.textoTimestamps = textoTimestamps;
    }
}