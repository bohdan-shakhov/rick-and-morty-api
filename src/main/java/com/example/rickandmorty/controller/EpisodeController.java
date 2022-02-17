package com.example.rickandmorty.controller;

import com.example.rickandmorty.exception_handling.IncorrectData;
import com.example.rickandmorty.exception_handling.NoSuchDataException;
import com.example.rickandmorty.response.EpisodeResponse;
import com.example.rickandmorty.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/episode")
public class EpisodeController {
    private final EpisodeService episodeService;

    @Autowired
    public EpisodeController(EpisodeService episodeService) {
        this.episodeService = episodeService;
    }

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

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(NoSuchDataException exception) {
        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }
}
