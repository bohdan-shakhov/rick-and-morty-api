package com.example.rickandmorty.service;

import com.example.rickandmorty.dto.episode.EpisodeDTO;
import com.example.rickandmorty.dto.episode.PageEpisode;
import com.example.rickandmorty.entity.Episode;
import com.example.rickandmorty.repository.EpisodeRepository;
import com.example.rickandmorty.response.EpisodeResponse;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.example.rickandmorty.constant.ProgrammConstant.EPISODE_URL;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Service
public class EpisodeService {
    private static final Logger LOGGER = Logger.getLogger(EpisodeService.class);
    private final ModelMapper modelMapper = new ModelMapper();
    private final EpisodeRepository episodeRepository;
    private final RestTemplate restTemplate;

    public EpisodeService(EpisodeRepository episodeRepository, RestTemplate restTemplate) {
        this.episodeRepository = episodeRepository;
        this.restTemplate = restTemplate;
    }

    public List<Episode> list() {
        LOGGER.info("getting all episodes from database");
        List<Episode> episodes = new ArrayList<>();
        for (Episode episode : episodeRepository.findAll()) {
            episodes.add(episode);
        }

        return episodes;
    }

    public void save(List<Episode> episodes) {
        LOGGER.info("save List of episodes (all) into database");
        episodeRepository.saveAll(episodes);
    }

    @Scheduled(cron = "0 2 21 1 * ?")
    public void saveToDatabase() {
        PageEpisode pageEpisode = restTemplate.getForObject(EPISODE_URL, PageEpisode.class);
        List<PageEpisode> pageEpisodeList = new ArrayList<>();
        while (true) {
            pageEpisodeList.add(pageEpisode);
            pageEpisode = restTemplate.getForObject(pageEpisode.getInfo().getNext(), PageEpisode.class);
            if (pageEpisode.getInfo().getNext() == null) {
                pageEpisodeList.add(pageEpisode);
                break;
            }
        }

        ArrayList<Episode> episodes = new ArrayList<>();
        pageEpisodeList.forEach(pageEpisodeElement -> {
            List<EpisodeDTO> results = pageEpisodeElement.getResults();
            results.forEach(result -> {
                Episode episode = modelMapper.map(result, Episode.class);
                episodes.add(episode);
                LOGGER.info("getting results of each episodes and map to entity");
            });
        });
        save(episodes);
        LOGGER.info("save episodes entities to database");
    }

    public List<EpisodeResponse> getAllEpisodes() {
        LOGGER.debug("getAllEpisodes() method");
        return LongStream.iterate(1, i -> i + 1)
                .mapToObj(id -> episodeRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .limit(51)
                .map(episode -> modelMapper.map(episode, EpisodeResponse.class))
                .collect(Collectors.toList());
    }

    public List<EpisodeResponse> getEpisodesByIds(List<String> ids) {
        LOGGER.debug("getEpisodesByIds(List<String> ids) method. ID(S): " + ids);
        return ids.stream()
                .map(id -> episodeRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .map(episode -> modelMapper.map(episode, EpisodeResponse.class))
                .collect(Collectors.toList());
    }

    public List<String> getTopFiveMostFrequentlyCharacters() {
        List<String> mostPopularCharacters = new ArrayList<>();
        Stream<List<String>> listStream = LongStream.iterate(1, i -> i + 1)
                .mapToObj(id -> episodeRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .limit(51)
                .map(episode -> modelMapper.map(episode, EpisodeResponse.class))
                .map(EpisodeResponse::getCharacters);

        listStream.forEach(mostPopularCharacters::addAll);
        return mostPopularCharacters.stream()
                .collect(groupingBy(identity(), counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .limit(5)
                .collect(Collectors.toList());

    }
}
