package com.github.francoagarcia.social.querydsl;

import com.github.francoagarcia.social.domain.*;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.UUID;

import static com.querydsl.jpa.JPAExpressions.select;
import static java.util.Objects.requireNonNull;

public class InboxQueryPredicate extends QueryPredicate<Message> implements InboxQuery {
    private final QueryDslPredicateExecutor<Message> repository;

    private UUID userId;

    public InboxQueryPredicate(MessageRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public Iterable<Message> findAll() {
        Sort sort = buildSort();
        return this.findAll(sort);
    }

    @Override
    public Page<Message> findAll(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), buildSort());
        return repository.findAll(buildPredicate(), pageRequest);
    }

    private Sort buildSort() {
        return new Sort(Sort.Direction.DESC, QMessage.message.publishTime.getMetadata().getName());
    }

    @Override
    protected Predicate buildPredicate() {
        requireNonNull(userId, "null user is not a valid search criteria");

        return QMessage.message.author.in(
                   select(QUser.user)
                  .from(QUser.user)
                  .where(
                    QUser.user.followers.contains(
                       select(QUser.user)
                      .from(QUser.user)
                      .where(QUser.user.id.eq(userId)))))
                  .or(
                    QMessage.message.author.id.eq(userId)
                  );
    }
}
