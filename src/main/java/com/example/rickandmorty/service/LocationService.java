package com.example.rickandmorty.service;

import com.example.rickandmorty.dto.location.LocationDTO;
import com.example.rickandmorty.dto.location.PageLocation;
import com.example.rickandmorty.entity.Location;
import com.example.rickandmorty.repository.LocationRepository;
import com.example.rickandmorty.response.LocationResponse;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.example.rickandmorty.constant.ProgrammConstant.LOCATION_URL;

@Service
public class LocationService {
    private static final Logger LOGGER = Logger.getLogger(LocationService.class);
    private final ModelMapper modelMapper = new ModelMapper();
    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Iterable<Location> list() {
        LOGGER.info("getting all locations from database");
        return locationRepository.findAll();
    }

    public void save(List<Location> locations) {
        LOGGER.info("save List of locations (all) into database");
        locationRepository.saveAll(locations);
    }

    public void saveToDatabase(RestTemplate restTemplate) {
        PageLocation pageLocation = restTemplate.getForObject(LOCATION_URL, PageLocation.class);
        LOGGER.info("getting locations from " + (Integer.parseInt(pageLocation.getInfo().getNext()) - 1) + " page");
        List<PageLocation> pageLocationList = new ArrayList<>();

        while (true) {
            pageLocationList.add(pageLocation);
            pageLocation = restTemplate.getForObject(pageLocation.getInfo().getNext(), PageLocation.class);
            LOGGER.info("getting locations from " + (Integer.parseInt(pageLocation.getInfo().getNext()) - 1) + " page");
            if (pageLocation.getInfo().getNext() == null) {
                pageLocationList.add(pageLocation);
                break;
            }
        }

        ArrayList<Location> locations = new ArrayList<>();
        pageLocationList.forEach(pageLocationElement -> {
            List<LocationDTO> results = pageLocationElement.getResults();
            results.forEach(result -> {
                Location location = modelMapper.map(result, Location.class);
                locations.add(location);
                LOGGER.info("getting results of each locations and map to entity");
            });
        });
        save(locations);
        LOGGER.info("save locations entities to database");
    }

    @Cacheable("locations")
    public List<LocationResponse> getAllLocations() {
        LOGGER.debug("getAllLocations() method");
        return LongStream.iterate(1, i -> i + 1)
                .mapToObj(id -> locationRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .limit(126)
                .map(location -> modelMapper.map(location, LocationResponse.class))
                .collect(Collectors.toList());
    }

    @Cacheable("locations")
    public List<LocationResponse> getLocationsByIds(List<String> ids) {
        LOGGER.debug("getLocationsByIds(List<String> ids) method. ID(S): " + ids);
        return ids.stream()
                .map(id -> locationRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .map(location -> modelMapper.map(location, LocationResponse.class))
                .collect(Collectors.toList());
    }
}
