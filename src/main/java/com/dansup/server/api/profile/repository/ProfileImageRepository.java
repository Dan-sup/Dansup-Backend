package com.dansup.server.api.profile.repository;

import com.dansup.server.api.profile.domain.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {

}
