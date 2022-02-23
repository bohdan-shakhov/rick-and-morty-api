package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.CharacterResponse;
import com.example.rickandmorty.service.CharacterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/character")
@AllArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping()
    public List<CharacterResponse> getAllCharacters() {
        return characterService.getAllCharacters();
    }

    @GetMapping("/{ids}")
    public List<CharacterResponse> getCharactersByIds(@PathVariable List<String> ids) {
        return characterService.getCharactersByIds(ids);
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
