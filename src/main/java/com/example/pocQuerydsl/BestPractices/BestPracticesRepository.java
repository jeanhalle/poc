package com.example.pocQuerydsl.BestPractices;

import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;

public interface BestPracticesRepository
        extends CrudRepository<BestPracticesDocument, String>, QuerydslPredicateExecutor<BestPracticesDocument>, QuerydslBinderCustomizer<com.example.pocQuerydsl.BestPractices.QBestPracticesDocument> {

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
