package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.LocationResponse;
import com.example.rickandmorty.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/location")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
        log.info("autowired {} into {}", LocationService.class.getName(), LocationController.class.getName());
    }

    @GetMapping()
    public List<LocationResponse> getAllLocations() {
        List<LocationResponse> locations = null;
        try {
            locations = locationService.getAllLocations();
            log.info(LocationController.class.getName() + " getting all locations in response body");
            return locations;
        } catch (Exception e) {
            log.error("error to get all locations", e);
        }
        return locations;
    }

    @GetMapping("/{ids}")
    public List<LocationResponse> getLocationsByArrayOfIds(@PathVariable List<String> ids) {
        List<LocationResponse> locations = null;
        try {
            locations = locationService.getLocationsByIds(ids);
            log.info("getting locations by id/ids {}", ids);
            return locations;
        } catch (Exception e) {
            log.error("error to get locations by id/ids {}", ids, e);
        }
        return locations;
    }

}
