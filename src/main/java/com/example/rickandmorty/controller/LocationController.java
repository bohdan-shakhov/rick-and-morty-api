package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.LocationResponse;
import com.example.rickandmorty.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/location")
@AllArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping()
    public List<LocationResponse> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{ids}")
    public List<LocationResponse> getLocationsByArrayOfIds(@PathVariable List<String> ids) {
        return locationService.getLocationsByIds(ids);
    }

}
