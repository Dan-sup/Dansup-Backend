package com.dansup.server.api.genre.domain;

import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProfileGenre extends BaseEntity {

    @Id
    @Column(name = "profile_genre_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

}
