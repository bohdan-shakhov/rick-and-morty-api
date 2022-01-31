package com.example.rickandmorty.entity;

import com.example.rickandmorty.enums.Gender;
import com.example.rickandmorty.enums.Status;
import lombok.Data;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String species;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne()
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "origin_id", referencedColumnName = "id")
    private Location origin;

    @ManyToOne()
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @ManyToMany(fetch = FetchType.EAGER,   cascade = CascadeType.MERGE)
    @JoinTable(
            name = "episode_character",
            joinColumns = @JoinColumn(name = "episode_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private List<Episode> episode;

    private String image;

    private String type;

    private LocalDateTime created;
}
