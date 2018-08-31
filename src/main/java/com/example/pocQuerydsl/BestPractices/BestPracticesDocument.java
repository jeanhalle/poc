package com.example.pocQuerydsl.BestPractices;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class BestPracticesDocument {

    String id;
    String description;
    String phase;
}
