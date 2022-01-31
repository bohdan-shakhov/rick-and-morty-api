package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.EpisodeResponse;
import com.example.rickandmorty.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/episode")
public class EpisodeController {

    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

    @GetMapping("/{id}")
    public EpisodeResponse getEpisodeById(@PathVariable("id") long id) {
        System.out.println(id);
        System.out.println("=================================");
        EpisodeResponse episode = episodeService.getEpisodeById(id);
        System.out.println(episode);
        return episode;
    }

    @GetMapping("/many/{ids}")
    public List<EpisodeResponse> getEpisodesByIds(@PathVariable List<String> ids) {
        System.out.println(ids);
        System.out.println("=============");
        List<EpisodeResponse> episodes = episodeService.getEpisodesByIds(ids);
        System.out.println(episodes);
        return episodes;
    }
}
