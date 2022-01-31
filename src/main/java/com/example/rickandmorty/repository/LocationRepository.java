package com.example.rickandmorty.repository;

import com.example.rickandmorty.entity.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Optional<Location> findByName(String name);
}
