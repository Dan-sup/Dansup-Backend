package com.dansup.server.profile.domain;

import com.dansup.server.genre.domain.ProfileGenre;
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
    private List<ProfileGenre> profileGenres = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id")
    private ProfileVideo profileVideo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pi_id")
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "profile")
    private List<Portfolio> portfolios = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<PortfolioVideo> portfolioVideos = new ArrayList<>();

    @Column(length = 5)
    private String hashtag1;

    @Column(length = 5)
    private String hashtag2;

    @Column(length = 5)
    private String hashtag3;

}