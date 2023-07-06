package com.dansup.server.genre.respository;

import com.dansup.server.genre.domain.ProfileGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileGenreRepository extends JpaRepository<ProfileGenre, Long> {

}
