package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.List;

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

    @Test
    void namesMono_map_filter() {
        //given
        int len = 3;
        //when
        var namesMonoVar = fluxAndMonoGeneratorService.namesMono_map_filter(len);
        //then
        StepVerifier.create(namesMonoVar)
                .expectNext("HYEBEEN")
                .verifyComplete();
    }

    @Test
    void namesMono_flatmap() {
        //given
        int len = 3;
        //when
        var namesMonoVar = fluxAndMonoGeneratorService.namesMono_flatmap(len);
        //then
        StepVerifier.create(namesMonoVar)
                .expectNext(List.of("H", "Y", "E", "B", "E", "E", "N"))
                .verifyComplete();
    }

    @Test
    void namesMono_flatmapMany() {
        //given
        int len = 3;
        //when
        var namesMonoVar = fluxAndMonoGeneratorService.namesMono_flatmapMany(len);
        //then
        StepVerifier.create(namesMonoVar)
                .expectNext("H", "Y", "E", "B", "E", "E", "N")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform() {
        //given
        int len = 2;
        //when
        var namesMonoVar = fluxAndMonoGeneratorService.namesFlux_transform(len);
        //then
        StepVerifier.create(namesMonoVar)
                .expectNext("H", "Y", "E", "B", "E", "E", "N")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_empty() {
        //given
        int len = 10;
        //when
        var namesMonoVar = fluxAndMonoGeneratorService.namesFlux_transform_defaultifempty(len);
        //then
        StepVerifier.create(namesMonoVar)
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void namesFlux_transform_switchifempty() {
        //given
        int len = 5;
        //when
        var namesMonoVar = fluxAndMonoGeneratorService.namesFlux_transform_switchifempty(len);
        //then
        StepVerifier.create(namesMonoVar)
                .expectNext("D","E","F","A","U","L","T")
                .verifyComplete();
    }

    @Test
    void explore_concat() {
        //given
        //when
        var concatFlux = fluxAndMonoGeneratorService.explore_concat();
        //then
        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_concat_delay() {
        //given
        //when
        var concatFlux = fluxAndMonoGeneratorService.explore_concat_delay();
        //then
        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_concatwith() {
        //given
        //when
        var concatFlux = fluxAndMonoGeneratorService.explore_concatwith();
        //then
        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void explore_concatwith_mono() {
        //given
        //when
        var concatFlux = fluxAndMonoGeneratorService.explore_concatwith_mono();
        //then
        StepVerifier.create(concatFlux)
                .expectNext("ABC", "DEF")
                .verifyComplete();
    }

    @Test
    void explore_merge() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_merge();
        //then
        StepVerifier.create(mergeFlux)
                .expectNext("A","D","B","E","C","F")//publishers are subscribed at the same time
                .verifyComplete();
    }

    @Test
    void explore_mergewith() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_mergewith();
        //then
        StepVerifier.create(mergeFlux)
                .expectNext("A","D","B","E","C","F")//publishers are subscribed at the same time
                .verifyComplete();
    }

    @Test
    void explore_mergewith_mono() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_mergewith_mono();
        //then
        StepVerifier.create(mergeFlux)
                .expectNext("ABC", "DEF")
                .verifyComplete();
    }

    @Test
    void explore_mergeSequential() {
        //given

        //when
        var mergeFlux = fluxAndMonoGeneratorService.explore_mergeSequential();
        //then
        StepVerifier.create(mergeFlux)
                .expectNext("A", "B", "C", "D", "E", "F") // ordering result
                .verifyComplete();
    }

    @Test
    void explore_zip() {
        //given
        //when
        var value = fluxAndMonoGeneratorService.explore_zip();
        //then
        StepVerifier.create(value)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void explore_zip_1() {
        //given
        //when
        var value = fluxAndMonoGeneratorService.explore_zip_1();
        //then
        StepVerifier.create(value)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void explore_zipwith() {
        //given
        //when
        var value = fluxAndMonoGeneratorService.explore_zipwith();
        //then
        StepVerifier.create(value)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void explore_zipwith_mono() {
        //given
        //when
        var value = fluxAndMonoGeneratorService.explore_zipwith_mono();
        //then
        StepVerifier.create(value)
                .expectNext("ABCDEF")
                .verifyComplete();
    }
}