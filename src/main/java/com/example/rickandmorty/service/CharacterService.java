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
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.example.rickandmorty.constant.ProgrammConstant.*;

@Service
public class CharacterService {

    private ModelMapper modelMapper = new ModelMapper();

    private CharacterRepository characterRepository;
    private LocationRepository locationRepository;
    private EpisodeRepository episodeRepository;

    public CharacterService(CharacterRepository characterRepository,
                            LocationRepository locationRepository,
                            EpisodeRepository episodeRepository) {
        this.characterRepository = characterRepository;
        this.locationRepository = locationRepository;
        this.episodeRepository = episodeRepository;
    }

    public Characters save(Characters characters) {
        return characterRepository.save(characters);
    }

    public void saveToDatabase(RestTemplate restTemplate) {
        PageCharacter pageCharacter = restTemplate.getForObject(CHARACTER_URL, PageCharacter.class);

        List<PageCharacter> pageCharacterList = new ArrayList<>();
        while (true) {
            pageCharacterList.add(pageCharacter);
            pageCharacter = restTemplate.getForObject(pageCharacter.getInfo().getNext(), PageCharacter.class);
            if (pageCharacter.getInfo().getNext() == null) {
                pageCharacterList.add(pageCharacter);
                break;
            }
        }

        pageCharacterList.forEach(pageCharacterElement -> {
            List<CharacterDTO> results = pageCharacterElement.getResults();
            results.forEach(result -> {
                Characters characters = modelMapper.map(result, Characters.class);

                characters.setStatus(Status.valueOf(result.getStatus().toUpperCase(Locale.ROOT)));
                characters.setGender(Gender.valueOf(result.getGender().toUpperCase(Locale.ROOT)));

                Optional<Location> location = locationRepository.findByName(characters.getLocation().getName());
                Optional<Location> origin = locationRepository.findByName(characters.getOrigin().getName());

                if (!origin.isPresent()) {
                    characters.setOrigin(null);
                }
                if (!location.isPresent()) {
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
                save(characters);
            });
        });
    }

    public List<CharacterResponse> getAllCharacters() {
        return LongStream.iterate(1, i -> i + 1)
                .mapToObj(id -> characterRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .limit(826)
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
}
