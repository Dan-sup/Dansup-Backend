package com.dansup.server.api.genre.respository;

import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.genre.domain.ClassGenre;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassGenreRepository extends JpaRepository<ClassGenre, Long> {
    @Query("select d from ClassGenre d where d.danceClass.id = :danceClassId")
    List<ClassGenre> findByDanceClassId(@Param("danceClassId")Long classId);
}
