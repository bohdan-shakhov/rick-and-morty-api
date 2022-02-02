package com.example.rickandmorty;

import com.example.rickandmorty.controller.CharacterController;
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
class CharacterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CharacterController characterController;

    @Test
    void episodeControllerLoadsTest() {
        assertThat(characterController).isNotNull();
    }

    @Test
    void getAllEpisodesReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/character"))
                .andExpect(status().isOk());
    }

    @Test
    void getSingleEpisodeReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/character/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getEpisodesByIdsReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/character/1,2"))
                .andExpect(status().isOk());
    }

}
