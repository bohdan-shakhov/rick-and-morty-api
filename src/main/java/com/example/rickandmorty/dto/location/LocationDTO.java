
package com.example.rickandmorty.dto.location;

import com.example.rickandmorty.datework.TimeDateWork;

import javax.annotation.Generated;
import java.time.LocalDateTime;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LocationDTO {

    private LocalDateTime created;
    private String dimension;
    private String name;
    private String type;
    private String url;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = TimeDateWork.parseDateTime(created);
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

}
