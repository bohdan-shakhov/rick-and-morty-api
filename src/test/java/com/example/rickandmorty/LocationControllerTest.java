package com.example.rickandmorty;

import com.example.rickandmorty.controller.LocationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test

    void verifyNumberOfLocationsInResponseBodyWhenGetOneLocation() throws Exception {
        this.mockMvc.perform(get("/location/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andReturn();
    }

    @Test
    void verifyContentOfResponseBodyWhenGetSingleLocation() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/location/1"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].created").value("2017-11-10T12:42:04.162"))
                .andExpect(jsonPath("$[0].dimension").value("Dimension C-137"))
                .andExpect(jsonPath("$[0].name").value("Earth (C-137)"))
                .andExpect(jsonPath("$[0].type").value("Planet"))
                .andExpect(jsonPath("$[0].url").value("http://localhost:8080/location/1"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void verifyContentOfResponseBodyWhenGetMultipleLocations() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/location/1,2"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].created").value("2017-11-10T13:06:38.182"))
                .andExpect(jsonPath("$[1].dimension").value("unknown"))
                .andExpect(jsonPath("$[1].name").value("Abadango"))
                .andExpect(jsonPath("$[1].type").value("Cluster"))
                .andExpect(jsonPath("$[1].url").value("http://localhost:8080/location/2"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void verifyContentOfResponseBodyWhenGetAllLocations() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/location"))
                .andExpect(jsonPath("$[125].id").value(126))
                .andExpect(jsonPath("$[125].created").value("2021-11-02T15:18:57.987"))
                .andExpect(jsonPath("$[125].dimension").value(""))
                .andExpect(jsonPath("$[125].name").value("Rick's Memories"))
                .andExpect(jsonPath("$[125].type").value("Memory"))
                .andExpect(jsonPath("$[125].url").value("http://localhost:8080/location/126"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}
