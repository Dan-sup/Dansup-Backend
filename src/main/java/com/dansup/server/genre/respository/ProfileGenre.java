package com.dansup.server.genre.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileGenre extends JpaRepository<ProfileGenre, Long> {

}
