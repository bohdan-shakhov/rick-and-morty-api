package com.example.rickandmorty.controller;

import com.example.rickandmorty.exception_handling.IncorrectData;
import com.example.rickandmorty.exception_handling.NoSuchDataException;
import com.example.rickandmorty.response.CharacterResponse;
import com.example.rickandmorty.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {
    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

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

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(NoSuchDataException exception) {
        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }
}
