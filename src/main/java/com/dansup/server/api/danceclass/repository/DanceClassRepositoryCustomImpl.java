package com.dansup.server.api.danceclass.repository;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.*;
import com.dansup.server.api.danceclass.dto.request.DanceClassFilterDto;
import com.dansup.server.api.danceclass.dto.request.DayRequestDto;
import com.dansup.server.api.genre.domain.QClassGenre;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class DanceClassRepositoryCustomImpl extends QuerydslRepositorySupport implements DanceClassRepositoryCustom {

    public DanceClassRepositoryCustomImpl() {
        super(DanceClass.class);
    }

    @Override
    public List<DanceClass> findDanceClass(String title) {
        QDanceClass danceClass = QDanceClass.danceClass;

        return from(danceClass)
                .where(
                        danceClass.title.contains(title),
                        danceClass.state.eq(State.Active)
                )
                .fetch();
    }

    @Override
    public List<DanceClass> findDanceClass(DanceClassFilterDto danceClassFilterDto) {
        QDanceClass danceClass = QDanceClass.danceClass;
        QClassGenre classGenre = QClassGenre.classGenre;

        return from(danceClass)
                .leftJoin(danceClass.classGenres, classGenre).on(
                        genreIn(classGenre, joinClassGenres(danceClassFilterDto.getGenres()))
                )
                .where(
                        locationContains(danceClass, danceClassFilterDto.getLocation()),
                        daysEq(danceClass, danceClassFilterDto.getDays()),
                        startTimeEq(danceClass, danceClassFilterDto.getStartTime(), danceClassFilterDto.getTime()),
                        endTimeEq(danceClass, danceClassFilterDto.getEndTime(), danceClassFilterDto.getTime()),
                        methodEq(danceClass, danceClassFilterDto.getMethod()),
                        difficultyEq(danceClass, danceClassFilterDto.getDifficulty()),
                        tuitionBetween(danceClass, danceClassFilterDto.getMinTuition(), danceClassFilterDto.getMaxTuition()),
                        danceClass.state.eq(State.Active)
                )
                .fetch();
    }

    @Override
    public List<DanceClass> findDanceClass(String title, DanceClassFilterDto danceClassFilterDto) {
        QDanceClass danceClass = QDanceClass.danceClass;
        QClassGenre classGenre = QClassGenre.classGenre;

        return from(danceClass)
                .leftJoin(danceClass.classGenres, classGenre).on(
                        genreIn(classGenre, joinClassGenres(danceClassFilterDto.getGenres()))
                )
                .where(
                        danceClass.title.contains(title),
                        locationContains(danceClass, danceClassFilterDto.getLocation()),
                        daysEq(danceClass, danceClassFilterDto.getDays()),
                        startTimeEq(danceClass, danceClassFilterDto.getStartTime(), danceClassFilterDto.getTime()),
                        endTimeEq(danceClass, danceClassFilterDto.getEndTime(), danceClassFilterDto.getTime()),
                        methodEq(danceClass, danceClassFilterDto.getMethod()),
                        difficultyEq(danceClass, danceClassFilterDto.getDifficulty()),
                        tuitionBetween(danceClass, danceClassFilterDto.getMinTuition(), danceClassFilterDto.getMaxTuition()),
                        danceClass.state.eq(State.Active)
                )
                .fetch();
    }

    private BooleanExpression locationContains(QDanceClass danceClass, String location) {
        return (location != null) ? danceClass.location.contains(location) : null;
    }

    private BooleanExpression daysEq(QDanceClass danceClass, DayRequestDto days) {
        BooleanExpression booleanExpression = danceClass.id.isNotNull();

        if(days.isMon()) {
            booleanExpression.and(danceClass.mon.eq(true));
        }
        if(days.isTue()) {
            booleanExpression.and(danceClass.tue.eq(true));
        }
        if(days.isWed()) {
            booleanExpression.and(danceClass.wed.eq(true));
        }
        if(days.isThu()) {
            booleanExpression.and(danceClass.thu.eq(true));
        }
        if(days.isFri()) {
            booleanExpression.and(danceClass.fri.eq(true));
        }
        if(days.isSat()) {
            booleanExpression.and(danceClass.sat.eq(true));
        }
        if(days.isSun()) {
            booleanExpression.and(danceClass.sun.eq(true));
        }

        return booleanExpression;

    }
    private BooleanExpression startTimeEq(QDanceClass danceClass, Integer startTime, String time) {
        if(startTime != null && time == null) {
            return danceClass.startTime.eq(startTime);
        }

        if (startTime == null && time != null) {
            switch (time) {
                case "새벽":
                    return danceClass.startTime.between(0, 5);
                case "오전":
                    return danceClass.startTime.between(6, 11);
                case "오후":
                    return danceClass.startTime.between(12, 17);
                case "저녁":
                    return danceClass.startTime.between(18, 23);
                case "새벽-오전":
                    return danceClass.startTime.between(0, 11);
                case "오전-오후":
                    return danceClass.startTime.between(6, 17);
                case "오후-저녁":
                    return danceClass.startTime.between(12, 23);
                default:
                    return danceClass.startTime.between(18, 29);
            }
        }
        return null;
    }

    private BooleanExpression endTimeEq(QDanceClass danceClass, Integer endTime, String time) {
        if(endTime != null && time == null) {
            return danceClass.endTime.eq(endTime);
        }

        if (endTime == null && time != null) {
            switch (time) {
                case "새벽":
                    return danceClass.endTime.between(1, 6);
                case "오전":
                    return danceClass.endTime.between(7, 12);
                case "오후":
                    return danceClass.endTime.between(13, 18);
                case "저녁":
                    return danceClass.endTime.between(19, 24);
                case "새벽-오전":
                    return danceClass.endTime.between(1, 12);
                case "오전-오후":
                    return danceClass.endTime.between(7, 18);
                case "오후-저녁":
                    return danceClass.endTime.between(13, 24);
                default:
                    return danceClass.endTime.between(19, 30);
            }
        }
        return null;
    }

    private BooleanExpression methodEq(QDanceClass danceClass, Method method) {
        return (method != null) ? danceClass.method.eq(method) : null;
    }

    private BooleanExpression difficultyEq(QDanceClass danceClass, Difficulty difficulty) {
        return (difficulty != null) ? danceClass.difficulty.eq(difficulty) : null;
    }

    private BooleanExpression tuitionBetween(QDanceClass danceClass, Integer minTuition, Integer maxTuition) {
        return (minTuition != null && maxTuition != null) ? danceClass.tuition.between(minTuition, maxTuition) : null;
    }

    private BooleanExpression genreIn(QClassGenre classGenre, List<String> genres) {
        log.info("[genres is Empty]: {}", genres.isEmpty());
        return (!genres.isEmpty()) ? classGenre.genre.name.in(genres) : classGenre.genre.id.isNotNull();
    }

    private List<String> joinClassGenres(List<GenreRequestDto> genres) {
        List<String> genreList = new ArrayList<>();
        log.info("[genres]: {}", genres);

        if(genres != null) {
            for(GenreRequestDto genre : genres) {
                genreList.add(genre.getGenre());
            }
        }

        log.info("[genreList]: {}", genreList);
        return genreList;
    }
}
