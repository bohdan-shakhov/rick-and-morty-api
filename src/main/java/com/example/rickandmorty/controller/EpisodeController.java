package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.EpisodeResponse;
import com.example.rickandmorty.service.EpisodeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/episode")
public class EpisodeController {
    private static final Logger LOGGER = Logger.getLogger(EpisodeController.class);
    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
        LOGGER.info("autowired " + EpisodeService.class.getName() + " into " + EpisodeController.class.getName());
    }

    @GetMapping()
    public List<EpisodeResponse> getAllEpisodes() {
        List<EpisodeResponse> episodes = null;
        try {
            episodes = episodeService.getAllEpisodes();
            LOGGER.info(EpisodeController.class.getName() + " getting all episodes in response body");
            return episodes;
        } catch (Exception e) {
            LOGGER.error("error to get all episodes", e);
        }
        return episodes;
    }

    @GetMapping("/{ids}")
    public List<EpisodeResponse> getEpisodesByIds(@PathVariable List<String> ids) {
        List<EpisodeResponse> episodes = null;
        try {
            episodes = episodeService.getEpisodesByIds(ids);
            LOGGER.info(EpisodeController.class.getName() + " getting episodes by id/ids " + ids);
            return episodes;
        } catch (Exception e) {
            LOGGER.error("error to get episodes by id/ids " + ids, e);
        }
        return episodes;
    }
}
