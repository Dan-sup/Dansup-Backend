package com.dansup.server.genre.respository;

import com.dansup.server.genre.domain.ClassGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassGenreRepository extends JpaRepository<ClassGenre, Long> {

}
