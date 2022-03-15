package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntgTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movieinfos = List.of(
                new MovieInfo(null,
                        "Batman Begins",
                        2005,
                        List.of("Christian Bale", "Michael Cane"),
                        LocalDate.parse("2005-06-15")),
                new MovieInfo(null,
                        "The Dark Knight",
                        2008,
                        List.of("Christian Bale", "HeathLedger"),
                        LocalDate.parse("2008-07-18")),
                new MovieInfo("abc",
                        "Dark Knight Rises",
                        2012,
                        List.of("Christian Bale", "Tom Hardy"),
                        LocalDate.parse("2012-07-20")));

        movieInfoRepository.saveAll(movieinfos)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void findAll() {
        //given
        //when
        var movieInfoFlux = movieInfoRepository.findAll().log();
        //then
        StepVerifier.create(movieInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        //given
        //when
        var movieInfoMono = movieInfoRepository.findById("abc").log();
        //then
        StepVerifier.create(movieInfoMono)
//                .expectNextCount(1)
                .assertNext(movieInfo -> assertEquals("Dark Knight Rises", movieInfo.getName()))
                .verifyComplete();
    }

    @Test
    void saveMovieInfo() {
        //given
        MovieInfo movieInfo = new MovieInfo(
                null,
                "Dr.Strange",
                2016,
                List.of("Benedict Cumberbatch", "Rachel McAdams"),
                LocalDate.parse("2016-10-26"));
        //when
        var movieInfoMono = movieInfoRepository.save(movieInfo).log();
        //then
        StepVerifier.create(movieInfoMono)
//                .expectNextCount(1)
                .assertNext(data -> {
                    assertNotNull(data.getMovieInfoId());
                    assertEquals("Dr.Strange", data.getName());
                })
                .verifyComplete();
    }

    @Test
    void updateMovieInfo() {
        //given
        var movieInfo = movieInfoRepository.findById("abc").block();
        Objects.requireNonNull(movieInfo).setYear(2021);
        //when
        var movieInfoMono = movieInfoRepository.save(movieInfo).log();
        //then
        StepVerifier.create(movieInfoMono)
                .expectNextCount(1)
                .assertNext(data -> assertEquals(2021, data.getYear()))
                .verifyComplete();
    }

    @Test
    void deleteMovieInfo() {
        //given
        //when
        movieInfoRepository.deleteById("abc").block();
        var movieInfoFlux = movieInfoRepository.findAll();
        //then
        StepVerifier.create(movieInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}