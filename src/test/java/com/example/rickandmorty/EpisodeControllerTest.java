package com.example.rickandmorty;

import com.example.rickandmorty.controller.EpisodeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
}
