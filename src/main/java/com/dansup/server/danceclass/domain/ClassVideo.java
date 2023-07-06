package com.dansup.server.danceclass.domain;

import com.dansup.server.profile.domain.Profile;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

public class ClassVideo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dance_class_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DanceClass danceClass;

    @Column(nullable = false)
    private String url;

}
