package com.example.rickandmorty.controller;

import com.example.rickandmorty.entity.Location;
import com.example.rickandmorty.repository.LocationRepository;
import com.example.rickandmorty.response.LocationResponse;
import com.example.rickandmorty.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/{ids}")
    public List<LocationResponse> getLocationsByArrayOfIds(@PathVariable List<String> ids) {
        List<LocationResponse> locations = locationService.getLocationsByIds(ids);
        return locations;
    }


}
