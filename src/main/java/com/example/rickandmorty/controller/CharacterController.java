package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.CharacterResponse;
import com.example.rickandmorty.service.CharacterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/character")
@AllArgsConstructor
public class CharacterController {
    private final CharacterService characterService;

    @GetMapping()
    public ResponseEntity<List<CharacterResponse>> getAllCharacters() {
        return ResponseEntity.ok().body(characterService.getAllCharacters());
    }

    @GetMapping("/{ids}")
    public ResponseEntity<List<CharacterResponse>> getCharactersByIds(@PathVariable List<String> ids) {
        return ResponseEntity.ok().body(characterService.getCharactersByIds(ids));
    }

    @GetMapping("/filtered_by_species_status_gender")
    public ResponseEntity<Long> getCountOfCharactersWithStatusSpeciesGender() {
        return ResponseEntity.ok().body(characterService.getCountOfCharactersWithStatusSpeciesGender());
    }

    @GetMapping("/planet")
    public ResponseEntity<String> getNameOfMostPopularOriginPlanet() {
        return ResponseEntity.ok().body(characterService.getMostPopularOriginPlanet());
    }

}
