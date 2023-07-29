package com.dansup.server.api.danceclass.domain;

import com.dansup.server.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassVideo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cv_id")
    private Long id;

    @Column(nullable = false)
    private String videoUrl;

//    @Column(nullable = false)
//    private String thumbnailUrl;
}
