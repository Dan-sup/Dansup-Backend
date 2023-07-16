package com.dansup.server.api.profile.domain;

import com.dansup.server.common.BaseEntity;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
public class PortfolioVideo extends BaseEntity {

    @Id
    @Column(name = "pfv_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Profile profile;

    @Column(nullable = false)
    private String url;

}
