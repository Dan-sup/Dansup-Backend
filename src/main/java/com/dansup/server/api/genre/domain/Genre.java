package com.dansup.server.api.genre.domain;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Genre {

    @Id
    @Column(name = "genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

}
