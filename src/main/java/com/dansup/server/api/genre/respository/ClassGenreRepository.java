package com.dansup.server.api.genre.respository;

import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.genre.domain.ClassGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassGenreRepository extends JpaRepository<ClassGenre, Long> {
    List<ClassGenre> findAllByDanceClass(DanceClass danceClass);
}
