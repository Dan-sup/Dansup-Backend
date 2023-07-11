package com.dansup.server.api.genre.domain;

import com.dansup.server.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ClassGenre extends BaseEntity {

    @Id
    @Column(name = "class_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

}
