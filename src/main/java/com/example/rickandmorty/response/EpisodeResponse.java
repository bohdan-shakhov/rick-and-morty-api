package com.example.rickandmorty.response;

import com.example.rickandmorty.entity.Characters;

import java.util.ArrayList;
import java.util.List;

public class EpisodeResponse {
    private Long id;
    private String air_date;

    private List<String> characters;

    private String created;
    private String episode;
    private String name;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Characters> characters) {
        ArrayList arrayList = new ArrayList();
        characters.forEach(character -> arrayList.add(character.getName()));
        this.characters = arrayList;
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "EpisodeResponse{" +
                "id=" + id +
                ", air_date=" + air_date +
                ", characters=" + characters +
                ", created=" + created +
                ", episode='" + episode + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
