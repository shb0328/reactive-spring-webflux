package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

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

    public Flux<String> namesFlux_transform(int len) {

        Function<Flux<String>, Flux<String>> filtermap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > len);

        return Flux.fromIterable(List.of("so", "hye", "been"))
                .transform(filtermap)
                .flatMap(s -> splitStringFlux(s))
                .log();
    }

    public Flux<String> namesFlux_transform_defaultifempty(int len) {

        Function<Flux<String>, Flux<String>> filtermap = name -> name.map(String::toUpperCase)
                .filter(s -> s.length() > len);

        return Flux.fromIterable(List.of("so", "hye", "been"))
                .transform(filtermap)
                .flatMap(s -> splitStringFlux(s))
                .defaultIfEmpty("default") // accepts actual type
                .log();
    }

    public Flux<String> namesFlux_transform_switchifempty(int len) {

        Function<Flux<String>, Flux<String>> filtermap = name ->
                name.map(String::toUpperCase)
                .filter(s -> s.length() > len)
                .flatMap(s -> splitStringFlux(s));

        Flux<String> defaultFlux = Flux.just("default")
                .transform(filtermap);

        return Flux.fromIterable(List.of("so", "hye", "been"))
                .transform(filtermap)
                .switchIfEmpty(defaultFlux) // accepts a publisher (Mono or Flux)
                .log();
    }

    private Flux<String> splitStringFlux(String str) {
        String[] arr = str.split("");
        return Flux.fromArray(arr);
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
    public Mono<String> namesMono() {
        return Mono.just("hyebeen");
    }

    public Mono<String> namesMono_map_filter(int len) {
        return Mono.just("hyebeen")
                .map(String::toUpperCase)
                .filter(s->s.length() > len);
    }

    public Mono<List<String>> namesMono_flatmap(int len) {
        return Mono.just("hyebeen")
                .map(String::toUpperCase)
                .filter(s-> s.length() > len)
                .flatMap(this::splitStringMono)
                .log(); //Mono<List of H Y E B E E N>
    }

    public Flux<String> namesMono_flatmapMany(int len) {
        return Mono.just("hyebeen")
                .map(String::toUpperCase)
                .filter(s-> s.length() > len)
                .flatMapMany(this::splitStringFlux)
                .log(); //
    }


    private Mono<List<String>> splitStringMono(String s) {
        String[] arr = s.split("");
        List<String> list = List.of(arr);
        return Mono.just(list);
    }

    public Mono<String> logMono() {
        return Mono.just("hyebeen").log();
    }

    /**
     * concat() & concatWith()
     * used to combine two reactive streams in to one
     * concatenation of reactive streams happens in a sequence
     * - first one is subscribed first and completes
     * - second one is subscribed after that and then completes
     * concat() : static method in Flux
     * concatWith() : instance method in Flux and Mono
     */

    public Flux<String> explore_concat() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> explore_concat_delay() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));
        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> explore_concatwith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return abcFlux.concatWith(defFlux).log();
    }

    public Flux<String> explore_concatwith_mono() { // return Flux !!
        var abcMono = Mono.just("ABC");
        var defMono = Mono.just("DEF");
        return abcMono.concatWith(defMono).log();
    }

    /**
     * merge() & mergeWith()
     * both the publishers are subscribed at the same time
     * - publishers are subscribed eagerly and the merge happens in an interleaved fashion
     * - concat() subscribes to the publishers in a sequence
     * merge() : static method in Flux
     * mergeWith() : instance method in Flux and Mono
     */

    public Flux<String> explore_merge() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(1000));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(1300));
        return Flux.merge(abcFlux, defFlux).log();
    }

    public Flux<String> explore_mergewith() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));
        return abcFlux.mergeWith(defFlux).log();
    }

    public Flux<String> explore_mergewith_mono() { // return Flux !!
        var abcMono = Mono.just("ABC");
        var defMono = Mono.just("DEF");
        return abcMono.mergeWith(defMono).log();
    }

    /**
     * mergeSequential()
     * the publishers are subscribed eagerly the merge happens in a sequence
     */

    public Flux<String> explore_mergeSequential() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(1000));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(2000));
        return Flux.mergeSequential(abcFlux, defFlux).log();
    }

    /**
     * zip() & zipWith()
     * publishers are subscribed eagerly
     * waits for all the publishers involved in the transformation to emit one element
     * continues until one publisher sends an onComplete event
     * zip()
     * - static method that's part of the flux
     * - can be used to merge up-to 2 to 8 publishers (flux or mono) in to one
     * zipWith()
     * - this is an instance method that's part of the flux and mono
     * - used to merge two publishers in to one
     */

    public Flux<String> explore_zip() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return Flux.zip(abcFlux, defFlux, (first, second) -> first + second)
                .log();
    }

    public Flux<String> explore_zip_1() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        var _123Flux = Flux.just("1", "2", "3");
        var _456Flux = Flux.just("4", "5", "6");
        return Flux.zip(abcFlux, defFlux, _123Flux, _456Flux) // Flux<Tuple4<T1, T2, T3, T4>>
                .map(t4 -> t4.getT1() + t4.getT2() + t4.getT3() + t4.getT4())
                .log();
    }

    public Flux<String> explore_zipwith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        return abcFlux.zipWith(defFlux, (first, second) -> first + second)
                .log();
    }

    public Mono<String> explore_zipwith_mono() {
        var abcMono = Mono.just("ABC");
        var defMono = Mono.just("DEF");
        return abcMono.zipWith(defMono) // Mono<Tuple2<T1, T2>>
                .map(t2 -> t2.getT1() + t2.getT2())
                .log();
    }

    /**
     * Main
     */
    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();
//        the only way to access these values is by subscribing to that flux/Mono
        fluxAndMonoGeneratorService.namesFlux().subscribe(name -> System.out.println("Name is " + name)); // way1. subscribe(consumer)
        fluxAndMonoGeneratorService.namesMono().subscribe(name -> System.out.println("Mono Name is " + name)); // way1. subscribe(consumer)
        fluxAndMonoGeneratorService.logFlux().subscribe(name -> System.out.println("value : " + name));
        fluxAndMonoGeneratorService.logMono().subscribe(name -> System.out.println("value : " + name));


    }
}
