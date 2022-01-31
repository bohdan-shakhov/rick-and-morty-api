
package com.example.rickandmorty.dto.character;

import javax.annotation.Generated;
import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PageCharacter {

    private Info info;
    private List<CharacterDTO> results;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<CharacterDTO> getResults() {
        return results;
    }

    public void setResults(List<CharacterDTO> results) {
        this.results = results;
    }

}
