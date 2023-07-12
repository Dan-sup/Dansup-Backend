package com.dansup.server.profile.repository;

import com.dansup.server.profile.domain.PortfolioVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioVideoRepository extends JpaRepository<PortfolioVideo, Long> {

}
