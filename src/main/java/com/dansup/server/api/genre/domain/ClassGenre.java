package com.dansup.server.api.genre.domain;

import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassGenre extends BaseEntity {

    @Id
    @Column(name = "class_genre_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dance_class_id")
    private DanceClass danceClass;

}
