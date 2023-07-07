package com.dansup.server.danceclass.service;

import com.dansup.server.danceclass.domain.DanceClass;
import com.dansup.server.danceclass.repository.DanceClassRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DanceClassService {

    private final DanceClassRepository danceClassRepository;

    public Optional<DanceClass> getClass(Long danceClassId){
        return danceClassRepository.findById(danceClassId);
    }

}
