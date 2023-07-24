package com.dansup.server.api.profile.repository;

import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUser(User user);
    Optional<Profile> findById(Long profileId);

    List<Profile> findByNickname(String nickname);
}
