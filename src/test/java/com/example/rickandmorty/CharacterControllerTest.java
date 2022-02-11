package com.example.rickandmorty;

import com.example.rickandmorty.controller.CharacterController;
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
    void getCountOfFilteredCharactersReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/character/filtered_by_species_status_gender"))
                .andExpect(status().isOk());
    }

    @Test
    void getMostPopularPlanetReturned2xxStatus() throws Exception {
        this.mockMvc.perform(get("/character/planet"))
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

    @Test
    void verifyContentOfResponseBodyWhenGetSingleCharacter() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/character/1"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].created").value("2017-11-04T18:48:46.250"))
                .andExpect(jsonPath("$[0].gender").value("MALE"))
                .andExpect(jsonPath("$[0].image").value("https://rickandmortyapi.com/api/character/avatar/1.jpeg"))
                .andExpect(jsonPath("$[0].name").value("Rick Sanchez"))
                .andExpect(jsonPath("$[0].species").value("Human"))
                .andExpect(jsonPath("$[0].status").value("ALIVE"))
                .andExpect(jsonPath("$[0].type").value(""))
                .andExpect(jsonPath("$[0].url").value("https://rickandmortyapi.com/api/character/1"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void verifyContentOfResponseBodyWhenGetMultipleCharacters() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/character/1,2"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].created").value("2017-11-04T18:50:21.651"))
                .andExpect(jsonPath("$[1].gender").value("MALE"))
                .andExpect(jsonPath("$[1].image").value("https://rickandmortyapi.com/api/character/avatar/2.jpeg"))
                .andExpect(jsonPath("$[1].name").value("Morty Smith"))
                .andExpect(jsonPath("$[1].species").value("Human"))
                .andExpect(jsonPath("$[1].status").value("ALIVE"))
                .andExpect(jsonPath("$[1].type").value(""))
                .andExpect(jsonPath("$[1].url").value("https://rickandmortyapi.com/api/character/2"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void verifyContentOfResponseBodyWhenGetAllCharacters() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/character"))
                .andExpect(jsonPath("$[825].id").value(826))
                .andExpect(jsonPath("$[825].created").value("2021-11-02T17:24:37.458"))
                .andExpect(jsonPath("$[825].gender").value("GENDERLESS"))
                .andExpect(jsonPath("$[825].image").value("https://rickandmortyapi.com/api/character/avatar/826.jpeg"))
                .andExpect(jsonPath("$[825].name").value("Butter Robot"))
                .andExpect(jsonPath("$[825].species").value("Robot"))
                .andExpect(jsonPath("$[825].status").value("ALIVE"))
                .andExpect(jsonPath("$[825].type").value("Passing Butter Robot"))
                .andExpect(jsonPath("$[825].url").value("https://rickandmortyapi.com/api/character/826"))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void verifyContentOfResponseBodyWhenGetCountOfFilteredCharacters() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/character/filtered_by_species_status_gender"))
                .andExpect(jsonPath("$").value(91))
                .andReturn();

        assertEquals("application/json", mvcResult.getResponse().getContentType());
    }

    @Test
    void verifyContentOfResponseBodyWhenGetMostPopularPlanet() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(get("/character/planet"))
                .andExpect(jsonPath("$").value("Earth (Replacement Dimension)"))
                .andReturn();

        assertEquals("text/plain;charset=UTF-8", mvcResult.getResponse().getContentType());
    }
}
