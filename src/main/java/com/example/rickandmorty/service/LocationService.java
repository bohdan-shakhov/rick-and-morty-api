package com.example.rickandmorty.service;

import com.example.rickandmorty.dto.location.LocationDTO;
import com.example.rickandmorty.dto.location.PageLocation;
import com.example.rickandmorty.entity.Location;
import com.example.rickandmorty.repository.LocationRepository;
import com.example.rickandmorty.response.LocationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import static com.example.rickandmorty.constant.ProgrammConstant.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class LocationService {

    private ModelMapper modelMapper = new ModelMapper();
    private LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Iterable<Location> list() {
        return locationRepository.findAll();
    }

    public Location save(Location location) {
        return locationRepository.save(location);
    }

    public void save(List<Location> locations) {
        locationRepository.saveAll(locations);
    }

    public void saveToDatabase(RestTemplate restTemplate) {
        PageLocation pageLocation = restTemplate.getForObject(LOCATION_URL, PageLocation.class);
        List<PageLocation> pageLocationList = new ArrayList<>();

        while (true) {
            pageLocationList.add(pageLocation);
            pageLocation = restTemplate.getForObject(pageLocation.getInfo().getNext(), PageLocation.class);
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
            });
        });
        save(locations);
    }

    public List<LocationResponse> getAllLocations() {
        return LongStream.iterate(1, i -> i + 1)
                .mapToObj(id -> locationRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .limit(126)
                .map(location -> modelMapper.map(location, LocationResponse.class))
                .collect(Collectors.toList());
    }

    public List<LocationResponse> getLocationsByIds(List<String> ids) {
        return ids.stream()
                .map(id -> locationRepository.findById(Long.valueOf(id)).orElseGet(null))
                .filter(Objects::nonNull)
                .map(location -> modelMapper.map(location, LocationResponse.class))
                .collect(Collectors.toList());
    }
}
