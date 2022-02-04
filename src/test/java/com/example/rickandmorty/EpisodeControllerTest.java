package com.example.rickandmorty;

import com.example.rickandmorty.controller.EpisodeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EpisodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EpisodeController episodeController;

    @Test
    void episodeControllerLoadsTest() {
        assertThat(episodeController).isNotNull();
    }

    @Test
    void getAllEpisodesReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/episode"))
                .andExpect(status().isOk());
    }

    @Test
    void getSingleEpisodeReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/episode/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getEpisodesByIdsReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/episode/1,2"))
                .andExpect(status().isOk());
    }

    @Test
    void verifyNumberOfEpisodesInResponseBodyWhenGetOneEpisode() throws Exception {
        this.mockMvc.perform(get("/episode/51"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andReturn();
    }

    @Test
    void verifyNumberOfEpisodesInResponseBodyWhenGetMultipleEpisodes() throws Exception {
        this.mockMvc.perform(get("/episode/1,2,3,4,5,6,7,8,9,10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(10))
                .andReturn();
    }

    @Test
    void verifyNumberOfEpisodesInResponseBodyWhenGetAllEpisodes() throws Exception {
        this.mockMvc.perform(get("/episode"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(51))
                .andReturn();
    }

    @Test
    void verifyContentOfResponseBodyWhenGetSingleEpisode() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/episode/1"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].air_date").value("2013-12-02 00:00:00.0"))
                .andExpect(jsonPath("$[0].created").value("2017-11-10T12:56:33.798"))
                .andExpect(jsonPath("$[0].episode").value("S01E01"))
                .andExpect(jsonPath("$[0].name").value("Pilot"))
                .andExpect(jsonPath("$[0].url").value("https://rickandmortyapi.com/api/episode/1"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void verifyContentOfResponseBodyWhenGetMultipleEpisodes() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/episode/1,2"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].air_date").value("2013-12-09 00:00:00.0"))
                .andExpect(jsonPath("$[1].created").value("2017-11-10T12:56:33.916"))
                .andExpect(jsonPath("$[1].episode").value("S01E02"))
                .andExpect(jsonPath("$[1].name").value("Lawnmower Dog"))
                .andExpect(jsonPath("$[1].url").value("https://rickandmortyapi.com/api/episode/2"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void verifyContentOfResponseBodyWhenGetAllEpisodes() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/episode"))
                .andExpect(jsonPath("$[50].id").value(51))
                .andExpect(jsonPath("$[50].air_date").value("2021-09-05 00:00:00.0"))
                .andExpect(jsonPath("$[50].created").value("2021-10-15T17:00:24.105"))
                .andExpect(jsonPath("$[50].episode").value("S05E10"))
                .andExpect(jsonPath("$[50].name").value("Rickmurai Jack"))
                .andExpect(jsonPath("$[50].url").value("https://rickandmortyapi.com/api/episode/51"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}
