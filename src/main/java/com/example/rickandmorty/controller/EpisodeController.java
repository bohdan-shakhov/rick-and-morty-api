package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.EpisodeResponse;
import com.example.rickandmorty.service.EpisodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/episode")
@AllArgsConstructor
public class EpisodeController {
    private final EpisodeService episodeService;

    @GetMapping()
    public List<EpisodeResponse> getAllEpisodes() {
        return episodeService.getAllEpisodes();
    }

    @GetMapping("/{ids}")
    public List<EpisodeResponse> getEpisodesByIds(@PathVariable List<String> ids) {
        return episodeService.getEpisodesByIds(ids);
    }

    @GetMapping("/frequently_characters")
    public List<String> getFiveTheMostFrequentlyCharacters() {
        return episodeService.getTopFiveMostFrequentlyCharacters();
    }

}
