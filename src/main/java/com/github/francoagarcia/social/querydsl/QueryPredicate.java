package com.github.francoagarcia.social.querydsl;

import com.github.francoagarcia.social.domain.Query;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Optional;

public abstract class QueryPredicate<T> implements Query<T>{
    private final QueryDslPredicateExecutor<T> executor;

    public QueryPredicate(QueryDslPredicateExecutor<T> repository) {
        this.executor = repository;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return executor.findAll(buildPredicate(), pageable);
    }

    @Override
    public Iterable<T> findAll() {
        return executor.findAll(buildPredicate());
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        return executor.findAll(buildPredicate(), sort);
    }

    @Override
    public Optional<T> findOne() {
        return Optional.ofNullable(executor.findOne(buildPredicate()));
    }

    @Override
    public long count() {
        return executor.count(buildPredicate());
    }

    protected abstract Predicate buildPredicate();
}
