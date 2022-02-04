package com.example.rickandmorty;

import com.example.rickandmorty.controller.CharacterController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    void getAllCharactersReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/character"))
                .andExpect(status().isOk());
    }

    @Test
    void getSingleCharacterReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/character/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getCharactersByIdsReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/character/1,2"))
                .andExpect(status().isOk());
    }

    @Test
    void verifyNumberOfCharactersInResponseBodyWhenGetOneCharacter() throws Exception {
        this.mockMvc.perform(get("/character/21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andReturn();
    }

    @Test
    void verifyNumberOfCharactersInResponseBodyWhenGetTenCharacters() throws Exception {
        this.mockMvc.perform(get("/character/1,2,3,4,5,6,7,8,9,10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(10))
                .andReturn();
    }

    @Test
    void verifyNumberOfCharactersInResponseBodyWhenGetAllCharacters() throws Exception {
        this.mockMvc.perform(get("/character"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(826))
                .andReturn();
    }
}
