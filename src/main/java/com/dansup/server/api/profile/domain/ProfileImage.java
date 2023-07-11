package com.dansup.server.api.profile.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProfileImage {

    @Id
    @Column(name = "pi_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;
}
