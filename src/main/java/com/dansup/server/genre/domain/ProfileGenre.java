package com.dansup.server.genre.domain;

import com.dansup.server.profile.domain.Profile;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProfileGenre {

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
