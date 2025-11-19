package com.extrator.model;

import java.util.List;

public class VideoRequest {
    private String url;
    private List<String> timestamps;      // lista simples de intervalos (ex: "00:00-03:15")
    private String textoTimestamps;       // texto bruto com tempos e títulos
    private String bitrate = "256k";      // taxa de compressão MP3 (default 256k)

    // Getters e Setters
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<String> timestamps) {
        this.timestamps = timestamps;
    }

    public String getTextoTimestamps() {
        return textoTimestamps;
    }

    public void setTextoTimestamps(String textoTimestamps) {
        this.textoTimestamps = textoTimestamps;
    }

    public String getBitrate() {
        return bitrate;
    }

    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }
}