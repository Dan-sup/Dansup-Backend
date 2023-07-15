package com.dansup.server.api.profile.domain;

import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.genre.domain.ProfileGenre;
import com.dansup.server.api.user.domain.User;
import com.dansup.server.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Builder.Default
    @OneToMany(mappedBy = "profile")
    private List<ProfileGenre> profileGenres = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pv_id")
    private ProfileVideo profileVideo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pi_id")
    private ProfileImage profileImage;

    @Builder.Default
    @OneToMany(mappedBy = "profile")
    private List<Portfolio> portfolios = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "profile")
    private List<PortfolioVideo> portfolioVideos = new ArrayList<>();

    @Column(length = 5)
    private String hashtag1;

    @Column(length = 5)
    private String hashtag2;

    @Column(length = 5)
    private String hashtag3;

    public static Profile createProfile(User user, SignUpDto signUpDto) {
        return Profile.builder()
                .user(user)
                .username(signUpDto.getUsername())
                .nickname(signUpDto.getNickname())
                .intro(signUpDto.getIntro())
                .hashtag1(signUpDto.getHashtags().get(0).getHashtag())
                .hashtag2(signUpDto.getHashtags().get(1).getHashtag())
                .hashtag3(signUpDto.getHashtags().get(2).getHashtag())
                .build();
        // TO DO: 파일 업로드 로직 추가
    }
}
