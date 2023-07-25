package com.dansup.server.api.danceclass.repository;

import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.dto.request.DanceClassFilterDto;

import java.util.List;

public interface DanceClassRepositoryCustom {

    List<DanceClass> findDanceClass(String title);

    List<DanceClass> findDanceClass(DanceClassFilterDto danceClassFilterDto);

    List<DanceClass> findDanceClass(String title, DanceClassFilterDto danceClassFilterDto);
}
