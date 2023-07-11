package com.dansup.server.api.user.domain;

import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

}
