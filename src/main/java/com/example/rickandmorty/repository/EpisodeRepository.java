package com.example.rickandmorty.repository;

import com.example.rickandmorty.entity.Episode;
import com.example.rickandmorty.entity.Location;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface EpisodeRepository extends PagingAndSortingRepository<Episode, Long>, JpaSpecificationExecutor<Episode> {
    Optional<Episode> findByUrl(String url);
    List<Episode> findAll();
}
