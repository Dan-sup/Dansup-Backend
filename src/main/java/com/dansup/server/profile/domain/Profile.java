package com.dansup.server.profile.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Profile {

    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 14)
    private String nickname;

    @Column(length = 50)
    private String intro;

    @Column
    @Enumerated(EnumType.STRING)
    private String danceGenre1;

    @Column
    @Enumerated(EnumType.STRING)
    private String danceGenre2;

    @Column
    @Enumerated(EnumType.STRING)
    private String danceGenre3;

    @Column
    private String hashtag1;

    @Column
    private String hashtag2;

    @Column
    private String hashtag3;

}
