package com.example.rickandmorty.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String dimension;

    @OneToMany(  mappedBy = "location", cascade = CascadeType.ALL)
    private List<Characters> residents;

    private LocalDateTime created;
}
