package com.example.rickandmorty;

import com.example.rickandmorty.backup.BackupDatabase;
import com.example.rickandmorty.entity.Role;
import com.example.rickandmorty.entity.User;
import com.example.rickandmorty.service.CharacterService;
import com.example.rickandmorty.service.EpisodeService;
import com.example.rickandmorty.service.LocationService;
import com.example.rickandmorty.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static com.example.rickandmorty.constant.ProgrammConstant.ROLE_ADMIN;
import static com.example.rickandmorty.constant.ProgrammConstant.ROLE_USER;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class RickAndMortyAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(RickAndMortyAppApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(EpisodeService episodeService,
                             CharacterService characterService,
                             LocationService locationService,
                             UserService userService,
                             BackupDatabase backupDatabase) {
        return args -> {
            locationService.saveToDatabase();
            episodeService.saveToDatabase();
            characterService.saveToDatabase();
            userService.saveRole(new Role(null, ROLE_USER));
            userService.saveRole(new Role(null, ROLE_ADMIN));
            userService.saveUser(new User(null, "Ivan Ivanov", "ivan", "password1", new ArrayList<>()));
            userService.saveUser(new User(null, "Artem Artemov", "artem", "password2", new ArrayList<>()));
            userService.addRoleToUser("ivan", ROLE_USER);
            userService.addRoleToUser("ivan", ROLE_ADMIN);
            userService.addRoleToUser("artem", ROLE_USER);
//            backupDatabase.backup();
//            backupDatabase.restore();
        };
    }
}
