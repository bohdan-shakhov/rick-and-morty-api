package com.example.rickandmorty;

import com.example.rickandmorty.service.CharacterService;
import com.example.rickandmorty.service.EpisodeService;
import com.example.rickandmorty.service.LocationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
public class RickAndMortyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RickAndMortyAppApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    CommandLineRunner runner(EpisodeService episodeService,
                             CharacterService characterService,
                             LocationService locationService,
                             RestTemplate restTemplate) {
        return args -> {
//            locationService.saveToDatabase(restTemplate);
//            episodeService.saveToDatabase(restTemplate);
//            characterService.saveToDatabase(restTemplate);
        };
    }
}
