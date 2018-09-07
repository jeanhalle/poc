package com.example.pocQuerydsl.BestPractices;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/bestpractices")
public class BestPracticesController {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final BestPracticesRepository repository;


    /**
     * Querydsl approach:
     * localhost:8090/api/v1/bestpractices/documents/?description=bp
     * localhost:8090/api/v1/bestpractices/documents/?description=bp&phase=6
     * localhost:8090/api/v1/bestpractices/documents/?description=bp&phase=bp&size=3&page=2
     */
    @GetMapping(value = "/documents", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BestPracticesDocument>> getDocument(@QuerydslPredicate(root = BestPracticesDocument.class) Predicate predicate, //
                                                                   @PageableDefault(sort = { "description", "phase" }) Pageable pageable){
        return new ResponseEntity<>(repository.findAll(predicate, pageable).stream().collect(Collectors.toList()), HttpStatus.OK);
    }


    /**
     * MongoTemplate approach:
     *
     * localhost:8090/api/v1/bestpractices/documents/search?description=bp
     * localhost:8090/api/v1/bestpractices/documents/search?description=bp&phase=6
     * localhost:8090/api/v1/bestpractices/documents/search?description=bp&phase=bp&size=3&page=2
     * localhost:8090/api/v1/bestpractices/documents/search?categories=cat0
     *
     */
    @GetMapping(value = "/documents/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BestPracticesDocument>> test(@PageableDefault(sort = { "description", "phase" }) Pageable pageable,
                                                            @RequestParam Map<String, String> parameters){
        final List<String> members = Arrays.stream(BestPracticesDocument.class.getDeclaredFields())
                .map(f -> f.getName())
                .collect(Collectors.toList());
        Query query = new Query().with(pageable);
        BiConsumer<String, String> createCriteria = (key, value) -> {
            if (members.contains(key)) {
                query.addCriteria(Criteria.where(key).regex(value));
            }
        };

        parameters.forEach(createCriteria);
        return new ResponseEntity<>(mongoTemplate.find(query, BestPracticesDocument.class), HttpStatus.OK);
    }



    @GetMapping(value = "/documents/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BestPracticesDocument>> test(@PageableDefault(sort = { "description", "phase" }) Pageable pageable){
        com.example.pocQuerydsl.BestPractices.QBestPracticesDocument bpd = com.example.pocQuerydsl.BestPractices.QBestPracticesDocument.bestPracticesDocument;

        BooleanExpression documentHasCategory0 = bpd.categories.contains("cat0");
        BooleanExpression documentHasCategory1 = bpd.categories.contains("cat1");
        return new ResponseEntity<>(repository.findAll(documentHasCategory0.or(documentHasCategory1), pageable).stream().collect(Collectors.toList()), HttpStatus.OK);
    }


}
