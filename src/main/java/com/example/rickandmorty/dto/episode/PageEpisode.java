
package com.example.rickandmorty.dto.episode;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PageEpisode {

    private Info info;
    private List<EpisodeDTO> results;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<EpisodeDTO> getResults() {
        return results;
    }

    public void setResults(List<EpisodeDTO> results) {
        this.results = results;
    }
}
