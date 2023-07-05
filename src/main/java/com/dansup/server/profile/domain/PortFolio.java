package com.dansup.server.profile.domain;

import lombok.Getter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
public class PortFolio {

    @Id
    @Column(name = "portfolio_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PortFolio portFolio;

    @Column
    private String date;

    @Column
    private String detail;

}
