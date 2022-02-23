package com.example.rickandmorty.response;


public class LocationResponse {
    public static final String MY_LOCATION_URL = "http://localhost:8080/location";
    public static final String SLASH = "/";

    private Long id;

    private String created;

    private String dimension;
    private String name;
    private String type;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.url = MY_LOCATION_URL + SLASH + id;
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "LocationResponse{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", dimension='" + dimension + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
