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
    void test() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/episode/1"))
                .andExpect(jsonPath("$.name").value("Pilot"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }
}
