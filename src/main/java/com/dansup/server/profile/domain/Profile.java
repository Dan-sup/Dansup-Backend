package com.dansup.server.profile.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "profile")
    private List<PortFolio> portFolios = new ArrayList<>();

    @Column
    @Enumerated(EnumType.STRING)
    private DanceGenre danceGenre1;

    @Column
    @Enumerated(EnumType.STRING)
    private DanceGenre danceGenre2;

    @Column
    @Enumerated(EnumType.STRING)
    private DanceGenre danceGenre3;

    @Column
    private String hashtag1;

    @Column
    private String hashtag2;

    @Column
    private String hashtag3;

}
