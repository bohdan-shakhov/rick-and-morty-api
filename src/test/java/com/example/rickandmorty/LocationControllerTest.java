package com.example.rickandmorty;

import com.example.rickandmorty.controller.LocationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LocationController locationController;

    @Test
    void locationControllerLoads() {
        assertThat(locationController).isNotNull();
    }

    @Test
    void getAllLocationsReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/location"))
                .andExpect(status().isOk());
    }

    @Test
    void getSingleLocationReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/location/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getLocationsByIdsReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/location/1,2"))
                .andExpect(status().isOk());
    }

}
