package com.example.pocQuerydsl.BestPractices;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.List;

public interface BestPracticesRepository
        extends MongoRepository<BestPracticesDocument, String>, QuerydslPredicateExecutor<BestPracticesDocument>, QuerydslBinderCustomizer<com.example.pocQuerydsl.BestPractices.QBestPracticesDocument> {


    List<BestPracticesDocument> findBestPracticesDocumentByCategoriesContaining(String category);

    /*
     * (non-Javadoc)
     * @see org.springframework.data.querydsl.binding.QuerydslBinderCustomizer#customize(org.springframework.data.querydsl.binding.QuerydslBindings, com.mysema.query.types.EntityPath)
     */
    @Override
    default public void customize(QuerydslBindings bindings, com.example.pocQuerydsl.BestPractices.QBestPracticesDocument doc) {

        bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
        //bindings.excluding(doc.id);
    }
}
