package com.dansup.server.api.profile.domain;

import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.genre.domain.ProfileGenre;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Profile extends BaseEntity {

    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, length = 14)
    private String nickname;

    @Column(length = 50)
    private String intro;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE)
    private List<ProfileGenre> profileGenres = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pv_id")
    private ProfileVideo profileVideo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "pi_id")
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE)
    private List<Portfolio> portfolios = new ArrayList<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE)
    private List<PortfolioVideo> portfolioVideos = new ArrayList<>();

    @Column(length = 5)
    private String hashtag1;

    @Column(length = 5)
    private String hashtag2;

    @Column(length = 5)
    private String hashtag3;

    @Builder
    public Profile(User user, SignUpDto signUpDto, ProfileImage profileImage, ProfileVideo profileVideo) {
        this.user = user;
        this.username = signUpDto.getUsername();
        this.nickname = signUpDto.getNickname();
        this.intro = signUpDto.getIntro();
        this.profileImage = profileImage;
        this.profileVideo = profileVideo;
        this.hashtag1 = signUpDto.getHashtag1();
        this.hashtag2 = signUpDto.getHashtag2();
        this.hashtag3 = signUpDto.getHashtag3();
    }

    @Builder
    public Profile(User user, SignUpDto signUpDto, ProfileImage profileImage) {
        this.user = user;
        this.username = signUpDto.getUsername();
        this.nickname = signUpDto.getNickname();
        this.intro = signUpDto.getIntro();
        this.profileImage = profileImage;
        this.profileVideo = null;
        this.hashtag1 = signUpDto.getHashtag1();
        this.hashtag2 = signUpDto.getHashtag2();
        this.hashtag3 = signUpDto.getHashtag3();
    }

    @Builder
    public Profile(User user, SignUpDto signUpDto, ProfileVideo profileVideo) {
        this.user = user;
        this.username = signUpDto.getUsername();
        this.nickname = signUpDto.getNickname();
        this.intro = signUpDto.getIntro();
        this.profileImage = null;
        this.profileVideo = profileVideo;
        this.hashtag1 = signUpDto.getHashtag1();
        this.hashtag2 = signUpDto.getHashtag2();
        this.hashtag3 = signUpDto.getHashtag3();
    }

}
