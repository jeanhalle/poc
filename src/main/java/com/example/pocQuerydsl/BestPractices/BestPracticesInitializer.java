package com.example.pocQuerydsl.BestPractices;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class BestPracticesInitializer {

    private final BestPracticesRepository repository;

    public void init(){

        IntStream.range(0, 10)
                .mapToObj(i -> new BestPracticesDocument("bp" + i , "bp1 Desc" + i, "bp1 Phase" + i, Arrays.asList("cat" + i)))
                .forEach(i -> repository.save(i));
    }
}
