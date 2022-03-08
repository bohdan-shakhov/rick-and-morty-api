package com.example.rickandmorty.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter @Setter @ToString
@NoArgsConstructor @AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -1138446817700416884L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String username;

    @JsonProperty
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonProperty
    private Collection<Role> roles = new ArrayList<>();

}
