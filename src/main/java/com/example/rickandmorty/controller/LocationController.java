package com.example.rickandmorty.controller;

import com.example.rickandmorty.exception_handling.IncorrectData;
import com.example.rickandmorty.exception_handling.NoSuchDataException;
import com.example.rickandmorty.response.LocationResponse;
import com.example.rickandmorty.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping()
    public List<LocationResponse> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{ids}")
    public List<LocationResponse> getLocationsByArrayOfIds(@PathVariable List<String> ids) {
        return locationService.getLocationsByIds(ids);
    }

    @ExceptionHandler
    public ResponseEntity<IncorrectData> handleException(NoSuchDataException exception) {
        IncorrectData incorrectData = new IncorrectData();
        incorrectData.setInfo(exception.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

}
