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
        this.locationService = locationService;
        LOGGER.info("autowired " + LocationService.class.getName() + " into " + LocationController.class.getName());
    }

    @GetMapping()
    public List<LocationResponse> getAllLocations() {
        List<LocationResponse> locations = null;
        try {
            locations = locationService.getAllLocations();
            LOGGER.info(LocationController.class.getName() + " getting all locations in response body");
            return locations;
        } catch (Exception e) {
            LOGGER.error("error to get all locations", e);
        }
        return locations;
    }

    @GetMapping("/{ids}")
    public List<LocationResponse> getLocationsByArrayOfIds(@PathVariable List<String> ids) {
        List<LocationResponse> locations = null;
        try {
            locations = locationService.getLocationsByIds(ids);
            LOGGER.info(LocationController.class.getName() + " getting locations by id/ids " + ids);
            return locations;
        } catch (Exception e) {
            LOGGER.error("error to get locations by id/ids " + ids, e);
        }
        return locations;
    }

}
