package com.example.rickandmorty.controller;

import com.example.rickandmorty.response.LocationResponse;
import com.example.rickandmorty.service.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        return ResponseEntity.ok().body(locationService.getAllLocations());
    }

    @GetMapping("/{ids}")
    public ResponseEntity<List<LocationResponse>> getLocationsByArrayOfIds(@PathVariable List<String> ids) {
        return ResponseEntity.ok().body(locationService.getLocationsByIds(ids));
    }

}
