package com.learnreactiveprogramming.service;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class FluxAndMonoGeneratorService {

    /**
     * Flux
     */
    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("so", "hye", "been")); //db or a remote service call
    }

    public Flux<String> namesFlux_map() {
        return Flux.fromIterable(List.of("so", "hye", "been"))
//                .map(s -> s.toUpperCase());
                .map(String::toUpperCase) // immutable : always return new Flux
                .log();
    }

    public Flux<String> namesFlux_filter(int len) {
        return Flux.fromIterable(List.of("so", "hye", "been"))
                .filter(s -> s.length() > len)
                .log();
    }

    public Flux<String> namesFlux_filter_map(int len) {
        return Flux.fromIterable(List.of("so", "hye", "been"))
                .filter(s -> s.length() > len)
                .map(s -> s.length() + "-" + s)
                .log();
    }

    public Flux<String> namesFlux_flatmap(int len) {
        return Flux.fromIterable(List.of("so", "hye", "been"))
                .filter(s -> s.length() > len)
                .flatMap(s -> Flux.fromArray(s.split("")))
                .log();
    }

    public Flux<String> namesFlux_flatmap_map(int len) {
        return Flux.fromIterable(List.of("so", "hye", "been"))
                .filter(s -> s.length() > len)
                .map(s -> s.split(""))
                .flatMap(s -> Flux.fromArray(s))
                .log();
    }

    public Flux<String> namesFlux_flatmap_async(int len) {
        return Flux.fromIterable(List.of("so", "hye", "been"))
                .filter(s -> s.length() > len)
                .flatMap(s -> splitString_withDelay(s))
                .log();
    }

    public Flux<String> namesFlux_concatmap_async(int len) {
        return Flux.fromIterable(List.of("so", "hye", "been"))
                .filter(s -> s.length() > len)
                .concatMap(s -> splitString_withDelay(s))
                .log();
    }

    private Flux<String> splitString_withDelay(String str) {
        String[] arr = str.split("");
//        int delay = new Random().nextInt(3000);
        int delay = 1000;
        return Flux.fromArray(arr)
                .delayElements(Duration.ofMillis(delay)); //have a delay before emitting each and every element from this flux
    }

    public Flux<String> logFlux() {
        return Flux.fromIterable(List.of("so", "hye", "been")).log();
    }

    /***
     * Mono
     */
    public Mono<String> nameMono() {
        return Mono.just("hyebeen");
    }

    public Mono<String> logMono() {
        return Mono.just("hyebeen").log();
    }

    /**
     * Main
     */
    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
//        the only way to access these values is by subscribing to that flux/Mono
        fluxAndMonoGeneratorService.namesFlux().subscribe(name -> System.out.println("Name is " + name)); // way1. subscribe(consumer)
        fluxAndMonoGeneratorService.nameMono().subscribe(name -> System.out.println("Mono Name is " + name)); // way1. subscribe(consumer)
        fluxAndMonoGeneratorService.logFlux().subscribe(name -> System.out.println("value : " + name));
        fluxAndMonoGeneratorService.logMono().subscribe(name -> System.out.println("value : " + name));


    }
}
