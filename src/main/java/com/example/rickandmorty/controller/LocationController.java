package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.LocationResponse;
import com.example.rickandmorty.service.LocationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    private static final Logger LOGGER = Logger.getLogger(LocationController.class);
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        LOGGER.info("autowired " + LocationService.class.getName() + " into " + LocationController.class.getName());
        this.locationService = locationService;
    }

    @GetMapping()
    public List<LocationResponse> getAllLocations() {
        LOGGER.info(LocationController.class.getName() + " getting all locations in response body");
        return locationService.getAllLocations();
    }

    @GetMapping("/{ids}")
    public List<LocationResponse> getLocationsByArrayOfIds(@PathVariable List<String> ids) {
        LOGGER.info(LocationController.class.getName() + " getting locations by id/ids " + ids);
        return locationService.getLocationsByIds(ids);
    }


}
