package com.example.rickandmorty.repository;

import com.example.rickandmorty.entity.Episode;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EpisodeRepository extends PagingAndSortingRepository<Episode, Long>, JpaSpecificationExecutor<Episode> {
    Optional<Episode> findByUrl(String url);
}
