package com.dansup.server.api.danceclass.repository;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.danceclass.domain.*;
import com.dansup.server.api.danceclass.dto.request.DanceClassFilterDto;
import com.dansup.server.api.danceclass.dto.request.DayRequestDto;
import com.dansup.server.api.genre.domain.QClassGenre;
import com.querydsl.core.types.Predicate;
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
    public List<DanceClass> findDanceClass(DanceClassFilterDto danceClassFilterDto) {
        QDanceClass danceClass = QDanceClass.danceClass;
        QClassGenre classGenre = QClassGenre.classGenre;

        return from(danceClass)
                .leftJoin(danceClass.classGenres, classGenre)
                .where(
                        locationContains(danceClass, danceClassFilterDto.getLocation()),
                        monEq(danceClass, danceClassFilterDto.getDays()),
                        tueEq(danceClass, danceClassFilterDto.getDays()),
                        wedEq(danceClass, danceClassFilterDto.getDays()),
                        thuEq(danceClass, danceClassFilterDto.getDays()),
                        friEq(danceClass, danceClassFilterDto.getDays()),
                        satEq(danceClass, danceClassFilterDto.getDays()),
                        sunEq(danceClass, danceClassFilterDto.getDays()),
                        startTimeEq(danceClass, danceClassFilterDto.getStartTime(), danceClassFilterDto.getTime()),
                        endTimeEq(danceClass, danceClassFilterDto.getEndTime(), danceClassFilterDto.getTime()),
                        methodEq(danceClass, danceClassFilterDto.getMethod()),
                        difficultyEq(danceClass, danceClassFilterDto.getDifficulty()),
                        tuitionBetween(danceClass, danceClassFilterDto.getMinTuition(), danceClassFilterDto.getMaxTuition()),
                        danceClass.state.eq(State.Active),
                        genreIn(classGenre, getClassGenres(danceClassFilterDto.getGenres()))
                )
                .distinct()
                .fetch();
    }

    @Override
    public List<DanceClass> findDanceClass(String title, DanceClassFilterDto danceClassFilterDto) {
        QDanceClass danceClass = QDanceClass.danceClass;
        QClassGenre classGenre = QClassGenre.classGenre;

        return from(danceClass)
                .leftJoin(danceClass.classGenres, classGenre)
                .where(
                        danceClass.title.contains(title),
                        locationContains(danceClass, danceClassFilterDto.getLocation()),
                        monEq(danceClass, danceClassFilterDto.getDays()),
                        tueEq(danceClass, danceClassFilterDto.getDays()),
                        wedEq(danceClass, danceClassFilterDto.getDays()),
                        thuEq(danceClass, danceClassFilterDto.getDays()),
                        friEq(danceClass, danceClassFilterDto.getDays()),
                        satEq(danceClass, danceClassFilterDto.getDays()),
                        sunEq(danceClass, danceClassFilterDto.getDays()),
                        startTimeEq(danceClass, danceClassFilterDto.getStartTime(), danceClassFilterDto.getTime()),
                        endTimeEq(danceClass, danceClassFilterDto.getEndTime(), danceClassFilterDto.getTime()),
                        methodEq(danceClass, danceClassFilterDto.getMethod()),
                        difficultyEq(danceClass, danceClassFilterDto.getDifficulty()),
                        tuitionBetween(danceClass, danceClassFilterDto.getMinTuition(), danceClassFilterDto.getMaxTuition()),
                        danceClass.state.eq(State.Active),
                        genreIn(classGenre, getClassGenres(danceClassFilterDto.getGenres()))
                )
                .distinct()
                .fetch();
    }

    private BooleanExpression locationContains(QDanceClass danceClass, String location) {
        return (location != null) ? danceClass.location.contains(location) : null;
    }


    private BooleanExpression monEq(QDanceClass danceClass, DayRequestDto days) {
        log.info("[days null]: {}", days == null);
        return (days != null && days.isMon()) ? danceClass.mon.isTrue() : null;
    }

    private BooleanExpression tueEq(QDanceClass danceClass, DayRequestDto days) {
        return (days != null && days.isTue()) ? danceClass.tue.isTrue() : null;
    }

    private BooleanExpression wedEq(QDanceClass danceClass, DayRequestDto days) {
        return (days != null && days.isWed()) ? danceClass.wed.isTrue() : null;
    }

    private BooleanExpression thuEq(QDanceClass danceClass, DayRequestDto days) {
        return (days != null && days.isThu()) ? danceClass.thu.isTrue() : null;
    }

    private BooleanExpression friEq(QDanceClass danceClass, DayRequestDto days) {
        return (days != null && days.isFri()) ? danceClass.fri.isTrue() : null;
    }

    private BooleanExpression satEq(QDanceClass danceClass, DayRequestDto days) {
        return (days != null && days.isSat()) ? danceClass.sat.isTrue() : null;
    }

    private BooleanExpression sunEq(QDanceClass danceClass, DayRequestDto days) {
        return (days != null && days.isSun()) ? danceClass.sun.isTrue() : null;
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
        return (!genres.isEmpty()) ? classGenre.genre.name.in(genres) : null;
    }

    private List<String> getClassGenres(List<GenreRequestDto> genres) {
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
