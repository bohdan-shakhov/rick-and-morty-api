package com.example.rickandmorty.service;

import com.example.rickandmorty.dto.character.CharacterDTO;
import com.example.rickandmorty.dto.character.PageCharacter;
import com.example.rickandmorty.entity.Characters;
import com.example.rickandmorty.entity.Episode;
import com.example.rickandmorty.entity.Location;
import com.example.rickandmorty.enums.Gender;
import com.example.rickandmorty.enums.Status;
import com.example.rickandmorty.repository.CharacterRepository;
import com.example.rickandmorty.repository.EpisodeRepository;
import com.example.rickandmorty.repository.LocationRepository;
import com.example.rickandmorty.response.CharacterResponse;
import com.example.rickandmorty.response.LocationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.rickandmorty.constant.ProgrammConstant.CHARACTER_URL;

@Service
public class CharacterService {
    private final ModelMapper modelMapper = new ModelMapper();

    private final CharacterRepository characterRepository;
    private final LocationRepository locationRepository;
    private final EpisodeRepository episodeRepository;
    private final RestTemplate restTemplate;

    public CharacterService(CharacterRepository characterRepository,
                            LocationRepository locationRepository,
                            EpisodeRepository episodeRepository,
                            RestTemplate restTemplate) {
        this.characterRepository = characterRepository;
        this.locationRepository = locationRepository;
        this.episodeRepository = episodeRepository;
        this.restTemplate = restTemplate;
    }

    public void save(List<Characters> characters) {
        characterRepository.saveAll(characters);
    }

    @Scheduled(cron = "0 4 21 1 * ?")
    public void saveToDatabase() {
        PageCharacter pageCharacter = restTemplate.getForObject(CHARACTER_URL, PageCharacter.class);
        List<PageCharacter> pageCharacterList = new ArrayList<>();
        while (true) {
            pageCharacterList.add(pageCharacter);
            pageCharacter = restTemplate.getForObject(Objects.requireNonNull(pageCharacter).getInfo().getNext(), PageCharacter.class);
            if (Objects.requireNonNull(pageCharacter).getInfo().getNext() == null) {
                pageCharacterList.add(pageCharacter);
                break;
            }
        }

        List<Characters> charactersList = new ArrayList<>();
        pageCharacterList.forEach(pageCharacterElement -> {
            List<CharacterDTO> results = pageCharacterElement.getResults();
            results.forEach(result -> {
                Characters characters = modelMapper.map(result, Characters.class);

                characters.setStatus(Status.valueOf(result.getStatus().toUpperCase(Locale.ROOT)));
                characters.setGender(Gender.valueOf(result.getGender().toUpperCase(Locale.ROOT)));

                Optional<Location> location = locationRepository.findByName(characters.getLocation().getName());
                Optional<Location> origin = locationRepository.findByName(characters.getOrigin().getName());

                if (origin.isEmpty()) {
                    characters.setOrigin(null);
                }
                if (location.isEmpty()) {
                    characters.setLocation(null);
                }
                origin.ifPresent(characters::setOrigin);
                location.ifPresent(characters::setLocation);

                List<Episode> episodeList = new ArrayList<>();
                result.getEpisode().forEach(episode -> {
                    Optional<Episode> optionalEpisode = episodeRepository.findByUrl(episode);
                    optionalEpisode.ifPresent(episodeList::add);
                });
                characters.setEpisode(episodeList);
                charactersList.add(characters);
            });
        });
        save(charactersList);
    }

    public List<CharacterResponse> getAllCharacters() {
        return Stream.of(characterRepository.findAll())
                .flatMap(Collection::stream)
                .map(character -> modelMapper.map(character, CharacterResponse.class))
                .collect(Collectors.toList());
    }

    public List<CharacterResponse> getCharactersByIds(List<String> ids) {
        return ids.stream()
                .map(id -> characterRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .map(character -> modelMapper.map(character, CharacterResponse.class))
                .collect(Collectors.toList());
    }

    public Long getCountOfCharactersWithStatusSpeciesGender() {
        return Stream.of(characterRepository.findAll())
                .flatMap(Collection::stream)
                .map(character -> modelMapper.map(character, CharacterResponse.class))
                .filter(characterResponse -> characterResponse.getGender().equals("MALE"))
                .filter(characterResponse -> characterResponse.getStatus().equals("DEAD"))
                .filter(characterResponse -> characterResponse.getSpecies().equals("Human"))
                .count();
    }

    public String getMostPopularOriginPlanet() {
        return Stream.of(characterRepository.findAll())
                .flatMap(Collection::stream)
                .map(character -> modelMapper.map(character, CharacterResponse.class))
                .map(CharacterResponse::getOrigin)
                .filter(Objects::nonNull)
                .filter(locationResponse -> "Planet".equals(locationResponse.getType()))
                .collect(Collectors.groupingBy(LocationResponse::getName, Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);
    }
}
