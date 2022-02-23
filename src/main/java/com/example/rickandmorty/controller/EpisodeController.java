package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.EpisodeResponse;
import com.example.rickandmorty.service.EpisodeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/episode")
@AllArgsConstructor
public class EpisodeController {
    private final EpisodeService episodeService;

    @GetMapping()
    public ResponseEntity<List<EpisodeResponse>> getAllEpisodes() {
        return ResponseEntity.ok().body(episodeService.getAllEpisodes());
    }

    @GetMapping("/{ids}")
    public ResponseEntity<List<EpisodeResponse>> getEpisodesByIds(@PathVariable List<String> ids) {
        return ResponseEntity.ok().body(episodeService.getEpisodesByIds(ids));
    }

    @GetMapping("/frequently_characters")
    public ResponseEntity<List<String>> getFiveTheMostFrequentlyCharacters() {
        return ResponseEntity.ok().body(episodeService.getTopFiveMostFrequentlyCharacters());
    }

}
