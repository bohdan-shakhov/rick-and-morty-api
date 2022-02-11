package com.example.rickandmorty;

import com.example.rickandmorty.backup.BackupDatabase;
import com.example.rickandmorty.service.CharacterService;
import com.example.rickandmorty.service.EpisodeService;
import com.example.rickandmorty.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class RickAndMortyAppApplication {
    private static final Logger LOGGER = Logger.getLogger(RickAndMortyAppApplication.class);
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
                             BackupDatabase backupDatabase) {
        return args -> {
            LOGGER.info("start saving locations to database");
            locationService.saveToDatabase();
            LOGGER.info("start saving episodes to database");
            episodeService.saveToDatabase();
            LOGGER.info("start saving characters to database");
            characterService.saveToDatabase();

//            backupDatabase.backup();
//            backupDatabase.restore();
        };
    }
}
