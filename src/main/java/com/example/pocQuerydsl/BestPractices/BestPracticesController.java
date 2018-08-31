package com.example.pocQuerydsl.BestPractices;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/bestpractices")
public class BestPracticesController {

    private final BestPracticesRepository repository;

    @GetMapping(value = "/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BestPracticesDocument>> getDocument(@QuerydslPredicate(root = BestPracticesDocument.class) Predicate predicate, //
                                                                   @PageableDefault(sort = { "description", "phase" }) Pageable pageable, //
                                                                   @RequestParam MultiValueMap<String, String> parameters){
        return new ResponseEntity<>(repository.findAll(predicate, pageable).stream().collect(Collectors.toList()), HttpStatus.OK);
    }
}
