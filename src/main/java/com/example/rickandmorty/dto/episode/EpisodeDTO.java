
package com.example.rickandmorty.dto.episode;

import com.example.rickandmorty.datework.TimeDateWork;

import javax.annotation.Generated;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;



@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EpisodeDTO {

    private Date air_date;
    private LocalDateTime created;
    private String episode;
    private String name;
    private String url;

    public Date getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) throws ParseException {
        DateFormat format = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        Date newAirDate = format.parse(air_date);
        this.air_date = newAirDate;
    }


    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = TimeDateWork.parseDateTime(created);
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
}
