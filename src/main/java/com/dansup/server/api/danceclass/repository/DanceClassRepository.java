package com.dansup.server.api.danceclass.repository;

import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.domain.State;
import com.dansup.server.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Long> {
    Optional<DanceClass> findById(Long dance_class_id);
    List<DanceClass> findByState(State state);
}
