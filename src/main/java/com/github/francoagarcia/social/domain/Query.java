package com.github.francoagarcia.social.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

public interface Query<T> {
    Page<T> findAll(Pageable pageable);
    Iterable<T> findAll();
    Iterable<T> findAll(Sort sort);
    Optional<T> findOne();
    long count();
}
