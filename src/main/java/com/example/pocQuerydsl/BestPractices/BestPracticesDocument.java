package com.example.pocQuerydsl.BestPractices;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document
@AllArgsConstructor
public class BestPracticesDocument {

    String id;
    String description;
    String phase;

//    Map<String, String> keyValues;
    List<String> categories;
}
