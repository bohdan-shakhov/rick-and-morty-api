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
        this.characterService = characterService;
        LOGGER.info("autowired" + CharacterService.class.getName() + " into " + CharacterController.class.getName());
    }

    @GetMapping()
    public List<CharacterResponse> getAllCharacters() {
        List<CharacterResponse> characters = null;
        try {
            characters = characterService.getAllCharacters();
            LOGGER.info("getting all characters from response body");
            return characters;
        } catch (Exception e) {
            LOGGER.error("error to get all characters", e);
        }
        return characters;
    }

    @GetMapping("/{ids}")
    public List<CharacterResponse> getCharactersByIds(@PathVariable List<String> ids) {
        List<CharacterResponse> characters = null;
        try {
            characters = characterService.getCharactersByIds(ids);
            LOGGER.info(CharacterController.class.getName() + " getting characters by id/ids " + ids);
            return characters;
        } catch (Exception e) {
            LOGGER.error("error to get characters by id/ids " + ids, e);
        }
        return characters;
    }

    @GetMapping("/filtered_by_species_status_gender")
    public Long getCountOfCharactersWithStatusSpeciesGender() {
        return characterService.getCountOfCharactersWithStatusSpeciesGender();
    }

    @GetMapping("/planet")
    public String getNameOfMostPopularOriginPlanet() {
        return characterService.getMostPopularOriginPlanet();
    }
}
