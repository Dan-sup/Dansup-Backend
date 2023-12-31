package com.dansup.server.api.genre.respository;

import com.dansup.server.api.genre.domain.ProfileGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileGenreRepository extends JpaRepository<ProfileGenre, Long> {

}
