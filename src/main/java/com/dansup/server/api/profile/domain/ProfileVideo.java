package com.dansup.server.api.profile.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProfileVideo {

    @Id
    @Column(name = "pv_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

}
