package com.example.rickandmorty.response;

import com.example.rickandmorty.entity.Episode;
import com.example.rickandmorty.entity.Location;
import com.example.rickandmorty.enums.Status;

import java.util.ArrayList;
import java.util.List;

import static com.example.rickandmorty.constant.ProgrammConstant.*;

public class CharacterResponse {
    private Long id;

    private String created;
    private List<String> episode;
    private String gender;
    private String image;
    private LocationResponse location;
    private String name;
    private LocationResponse origin;
    private String species;
    private String status;
    private String type;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public List<String> getEpisode() {
        return episode;
    }

    public void setEpisode(List<Episode> episodes) {
        ArrayList<String> episodeStrings = new ArrayList<>();
        episodes.forEach(singleEpisode -> {
            String episodeUrl = MY_EPISODE_URL + SLASH + singleEpisode.getId().toString();
            episodeStrings.add(episodeUrl);
        });

        this.episode = episodeStrings;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocationResponse getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location == null) {
            this.location = null;
        } else {
            LocationResponse locationResponse = new LocationResponse();
            locationResponse.setName(location.getName());
            locationResponse.setUrl(MY_LOCATION_URL + SLASH + location.getId());
            locationResponse.setCreated(location.getCreated().toString());
            locationResponse.setDimension(location.getDimension());
            locationResponse.setType(location.getType());
            this.location = locationResponse;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocationResponse getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        if (origin == null) {
            this.origin = null;
        } else {
            LocationResponse locationResponse = new LocationResponse();
            locationResponse.setName(origin.getName());
            locationResponse.setUrl(MY_LOCATION_URL + SLASH + origin.getId());
            locationResponse.setCreated(origin.getCreated().toString());
            locationResponse.setDimension(origin.getDimension());
            locationResponse.setType(origin.getType());
            this.origin = locationResponse;
        }

    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return CHARACTER_URL + SLASH + id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CharacterResponse{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", episode=" + episode +
                ", gender='" + gender + '\'' +
                ", image='" + image + '\'' +
                ", location=" + location +
                ", name='" + name + '\'' +
                ", origin=" + origin +
                ", species='" + species + '\'' +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
