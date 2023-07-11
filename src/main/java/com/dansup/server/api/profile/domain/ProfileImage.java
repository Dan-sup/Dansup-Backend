package com.dansup.server.api.profile.domain;

import com.dansup.server.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProfileImage extends BaseEntity {

    @Id
    @Column(name = "pi_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;
}
