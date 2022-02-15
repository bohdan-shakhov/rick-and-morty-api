package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.EpisodeResponse;
import com.example.rickandmorty.service.EpisodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/episode")
public class EpisodeController {
    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
        log.info("autowired {} into {}", EpisodeService.class.getName(), EpisodeController.class.getName());
    }

    @GetMapping()
    public List<EpisodeResponse> getAllEpisodes() {
        List<EpisodeResponse> episodes = null;
        try {
            episodes = episodeService.getAllEpisodes();
            log.info("getting all episodes in response body - {}", EpisodeController.class.getName());
            return episodes;
        } catch (Exception e) {
            log.error("error to get all episodes", e);
        }
        return episodes;
    }

    @GetMapping("/{ids}")
    public List<EpisodeResponse> getEpisodesByIds(@PathVariable List<String> ids) {
        List<EpisodeResponse> episodes = null;
        try {
            episodes = episodeService.getEpisodesByIds(ids);
            log.info("{} - getting episodes by id/ids {}", EpisodeController.class.getName(), ids);
            return episodes;
        } catch (Exception e) {
            log.error("error to get episodes by id/ids {}", ids, e);
        }
        return episodes;
    }

    @GetMapping("/frequently_characters")
    public List<String> getFiveTheMostFrequentlyCharacters() {
        return episodeService.getTopFiveMostFrequentlyCharacters();
    }
}
