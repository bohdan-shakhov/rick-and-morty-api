package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.CharacterResponse;
import com.example.rickandmorty.service.CharacterService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {
    private static final Logger LOGGER = Logger.getLogger(CharacterController.class);
    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        LOGGER.info("autowired" + CharacterService.class.getName() + " into " + CharacterController.class.getName());
        this.characterService = characterService;
    }

    @GetMapping()
    public List<CharacterResponse> getAllCharacters() {
        LOGGER.info(CharacterController.class.getName() + " getting all characters in response body");
        return characterService.getAllCharacters();
    }

    @GetMapping("/{ids}")
    public List<CharacterResponse> getCharactersByIds(@PathVariable List<String> ids) {
        LOGGER.info(CharacterController.class.getName() + " getting characters by id/ids " + ids);
        return characterService.getCharactersByIds(ids);
    }
}
