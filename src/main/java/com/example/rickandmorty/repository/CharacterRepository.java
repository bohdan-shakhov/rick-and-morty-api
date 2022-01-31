package com.example.rickandmorty.repository;

import com.example.rickandmorty.entity.Characters;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<Characters, Long> {
}
