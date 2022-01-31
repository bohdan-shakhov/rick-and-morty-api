package com.example.rickandmorty.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
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
