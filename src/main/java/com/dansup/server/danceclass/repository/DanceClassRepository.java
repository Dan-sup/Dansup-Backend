package com.dansup.server.danceclass.repository;

import com.dansup.server.danceclass.domain.DanceClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanceClassRepository extends JpaRepository<DanceClass, Long> {
}