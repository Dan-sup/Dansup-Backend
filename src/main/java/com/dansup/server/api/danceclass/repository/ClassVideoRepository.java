package com.dansup.server.api.danceclass.repository;

import com.dansup.server.api.danceclass.domain.ClassVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassVideoRepository extends JpaRepository<ClassVideo, Long> {
}
