package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFluxTest1() {
        //given
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux();
        //then
        StepVerifier.create(namesFluxVar)
                .expectNext("so", "hye", "been")
                .verifyComplete();
    }

    @Test
    void namesFluxTest2() {
        //given
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux();
        //then
        StepVerifier.create(namesFluxVar)
                .expectNext("hye", "so", "been") // "so", "hye", "been"
                .verifyComplete(); //err
    }

    @Test
    void namesFluxTest3() {
        //given
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux();
        //then
        StepVerifier.create(namesFluxVar)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void namesFluxTest4() {
        //given
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux();
        //then
        StepVerifier.create(namesFluxVar)
                .expectNext("so")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFlux_map() {
        //given
        //when
        var nameFluxVar = fluxAndMonoGeneratorService.namesFlux_map();
        //then
        StepVerifier.create(nameFluxVar)
                .expectNext("SO", "HYE", "BEEN")
                .verifyComplete();
    }

    @Test
    void namesFlux_filter() {
        //given
        int len = 3;
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux_filter(len);
        //then
        StepVerifier.create(namesFluxVar)
                .expectNext("been")
                .verifyComplete();
    }

    @Test
    void namesFlux_filter_map() {
        //given
        int len = 3;
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux_filter_map(len);
        //then
        StepVerifier.create(namesFluxVar)
                .expectNext("4-been")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap() {
        //given
        int len = 2;
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux_flatmap(len);
        //then
        StepVerifier.create(namesFluxVar)
                .expectNext("h","y","e","b","e","e","n")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_map() {
        //given
        int len = 2;
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux_flatmap_map(len);
        //then
        StepVerifier.create(namesFluxVar)
                .expectNext("h","y","e","b","e","e","n")
                .verifyComplete();
    }

    @Test
    void namesFlux_flatmap_async() {
        //given
        int len = 2;
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux_flatmap_async(len);
        //then
        StepVerifier.create(namesFluxVar)
//                .expectNext("h","y","e","b","e","e","n")
                .expectNextCount("hyebeen".length())
                .verifyComplete();
    }

    @Test
    void namesFlux_concatmap_async() {
        //given
        int len = 2;
        //when
        var namesFluxVar = fluxAndMonoGeneratorService.namesFlux_concatmap_async(len);
        //then
        StepVerifier.create(namesFluxVar)
                .expectNext("h","y","e","b","e","e","n")
//                .expectNextCount("hyebeen".length())
                .verifyComplete();
    }
}