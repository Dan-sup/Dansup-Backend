package com.dansup.server.profile.repository;

import com.dansup.server.profile.domain.ProfileVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileVideoRepository extends JpaRepository<ProfileVideo, Long> {

}
