package com.dansup.server.api.profile.service;

import com.dansup.server.api.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.api.profile.domain.Portfolio;
import com.dansup.server.api.profile.domain.Profile;
import com.dansup.server.api.profile.dto.response.GetPortfolioDto;
import com.dansup.server.api.profile.repository.PortfolioRepository;
import com.dansup.server.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public void createPortfolio(Profile profile, List<GetPortfolioDto> portfolios){

        portfolios.forEach(pf -> {

            Portfolio portfolio = Portfolio.builder()
                    .profile(profile)
                    .date(pf.getDate())
                    .detail(pf.getDetail())
                    .build();

            portfolioRepository.save(portfolio);

            log.info("[porfolio 생성 완료]: {}", portfolio.getId());
        });

    }
}
