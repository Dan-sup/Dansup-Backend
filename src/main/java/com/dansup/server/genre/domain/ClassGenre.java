package com.dansup.server.genre.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ClassGenre {

    @Id
    @Column(name = "class_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

}
