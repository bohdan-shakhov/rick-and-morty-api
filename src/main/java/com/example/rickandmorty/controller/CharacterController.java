package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.CharacterResponse;
import com.example.rickandmorty.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/character")
public class CharacterController {
    private final CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/{id}")
    public CharacterResponse getCharacterById(@PathVariable("id") long id) {
        System.out.println(id);
        System.out.println("===============================");
        CharacterResponse character = characterService.getCharacterById(id);
        System.out.println(character);
        return character;
    }

    @GetMapping("/many/{ids}")
    public List<CharacterResponse> getCharactersByIds(@PathVariable List<String> ids) {
        System.out.println(ids);
        System.out.println("====================");
        List<CharacterResponse> characters = characterService.getCharactersByIds(ids);
        System.out.println(characters);
        return characters;
    }
}
