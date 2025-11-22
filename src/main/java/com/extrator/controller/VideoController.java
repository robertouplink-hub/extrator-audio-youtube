package com.extrator.controller;

import com.extrator.model.VideoRequest;
import com.extrator.model.VideoTask;
import com.extrator.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * Cria uma nova tarefa de extração.
     */
    @PostMapping
    public ResponseEntity<VideoTask> criar(@RequestBody VideoRequest req) {
        if (req == null || req.getUrl() == null || req.getUrl().isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }

        VideoTask task = videoService.criarTarefa(req);
        if (task == null || task.getId() == null) {
            return ResponseEntity.internalServerError().body(null);
        }

        return ResponseEntity.ok(task);
    }

    /**
     * Consulta o status da tarefa via path.
     */
    @GetMapping("/status/{id}")
    public ResponseEntity<VideoTask> statusPath(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        VideoTask task = videoService.consultarTarefa(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }

    /**
     * Consulta o status da tarefa via query param (alternativa segura).
     */
    @GetMapping("/status")
    public ResponseEntity<VideoTask> statusQuery(@RequestParam(required = false) Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        VideoTask task = videoService.consultarTarefa(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }
}