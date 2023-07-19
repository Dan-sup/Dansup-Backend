package com.dansup.server.api.danceclass.domain;

import com.dansup.server.api.user.domain.User;
import com.dansup.server.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DanceClass extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dance_class_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cv_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ClassVideo classVideo;

    @Column(nullable = false)
    @NotBlank(message = "수업 이름은 필수 값입니다.")
    private String title;

    @Column(length = 5)
    private String hashtag1;

    @Column(length = 5)
    private String hashtag2;

    @Column(length = 5)
    private String hashtag3;

    @Column(nullable = false)
    @NotBlank(message = "주소는 필수 값입니다.")
    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(nullable = false)
    @NotBlank(message = "수업 가격은 필수 값입니다.")
    private int tuition;

    private int maxPeople;

    @Column(length = 10)
    private String song;

    private String detail1;
    private String detail2;
    private String detail3;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Method method;

    private boolean mon;
    private boolean tue;
    private boolean wed;
    private boolean thu;
    private boolean fri;
    private boolean sat;
    private boolean sun;

    @Column(nullable = false)
    @NotBlank(message = "수업 시작시간은 필수 값입니다.")
    private int startTime;

    @Column(nullable = false)
    @NotBlank(message = "수업 종료시간은 필수 값입니다.")
    private int endTime;

    private String date;

    private String reserveLink;

    private State state = State.Active;

    public void updateState(State state) {
        this.state = state;
    }
}
