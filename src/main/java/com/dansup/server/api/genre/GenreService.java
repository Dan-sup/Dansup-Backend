package com.dansup.server.api.genre;

import com.dansup.server.api.auth.dto.request.GenreRequestDto;
import com.dansup.server.api.auth.dto.request.SignUpDto;
import com.dansup.server.api.danceclass.domain.DanceClass;
import com.dansup.server.api.danceclass.dto.request.CreateDanceClassDto;
import com.dansup.server.api.genre.domain.ClassGenre;
import com.dansup.server.api.genre.domain.ProfileGenre;
import com.dansup.server.api.genre.respository.ClassGenreRepository;
import com.dansup.server.api.genre.respository.GenreRepository;
import com.dansup.server.api.genre.respository.ProfileGenreRepository;
import com.dansup.server.api.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;
    private final ProfileGenreRepository profileGenreRepository;
    private final ClassGenreRepository classGenreRepository;

    public void creatProfileGenre(SignUpDto signUpDto, Profile profile){

        List<GenreRequestDto> genres = signUpDto.getGenres();

        for(GenreRequestDto genreRequestDto : genres) {
            if(genreRequestDto.getGenre() != null) {
                ProfileGenre profileGenre = ProfileGenre.builder()
                        .profile(profile)
                        .genre(genreRepository.findByName(genreRequestDto.getGenre())).build();
                profileGenreRepository.save(profileGenre);
            }
            else break;
        }
    }

    public void creatClassGenre(CreateDanceClassDto createDanceClassDto, DanceClass danceClass){

        List<GenreRequestDto> genres = createDanceClassDto.getGenres();

        for(GenreRequestDto genreRequestDto : genres) {
            if(genreRequestDto.getGenre() != null) {
                ClassGenre classGenre = ClassGenre.builder()
                        .danceClass(danceClass)
                        .genre(genreRepository.findByName(genreRequestDto.getGenre())).build();
                classGenreRepository.save(classGenre);
            }
            else break;
        }
    }

}
