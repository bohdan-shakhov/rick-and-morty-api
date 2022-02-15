package com.example.rickandmorty.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;
    private Date air_date;
    private LocalDateTime created;


    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER,mappedBy = "episode" )
    private List<Characters> characters;

    private String episode;
    private String url;
}
