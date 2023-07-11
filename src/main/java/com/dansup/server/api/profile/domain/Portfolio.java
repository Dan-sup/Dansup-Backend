package com.dansup.server.api.profile.domain;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
public class Portfolio {

    @Id
    @Column(name = "portfolio_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Profile profile;

    @Column
    private String date;

    @Column
    private String detail;

}
