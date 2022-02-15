package com.example.rickandmorty.repository;

import com.example.rickandmorty.entity.Characters;
import com.example.rickandmorty.entity.Episode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterRepository extends CrudRepository<Characters, Long> {
    List<Characters> findAll();
}
