package com.extrator.controller;

import com.extrator.model.VideoRequest;
import com.extrator.model.VideoTask;
import com.extrator.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    // Endpoint para iniciar processamento
    @PostMapping("/processar")
    public VideoTask processar(@RequestBody VideoRequest request) {
        return videoService.criarTarefa(request);
    }

    // Endpoint para consultar status
    @GetMapping("/status/{id}")
    public VideoTask status(@PathVariable Long id) {
        return videoService.consultarTarefa(id);
    }

    // Endpoint para obter resultado
    @GetMapping("/resultado/{id}")
    public VideoTask resultado(@PathVariable Long id) {
        return videoService.resultadoTarefa(id);
    }
}