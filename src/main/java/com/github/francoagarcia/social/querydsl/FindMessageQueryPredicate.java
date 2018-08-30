package com.github.francoagarcia.social.querydsl;

import com.github.francoagarcia.social.domain.FindMessageQuery;
import com.github.francoagarcia.social.domain.Message;
import com.github.francoagarcia.social.domain.MessageRepository;
import com.github.francoagarcia.social.domain.User;

import java.time.LocalDateTime;
import java.util.Optional;

public class FindMessageQueryPredicate extends ComposableQueryPredicate<Message> implements FindMessageQuery {
    public FindMessageQueryPredicate(MessageRepository repository) {
        super(repository);
    }

    @Override
    public void setAuthorName(Optional<String> author) {
        super.addFilter(author.map(MessageQueryFilters::authoredBy));
    }

    @Override
    public void setAuthor(Optional<User> author) {
        super.addFilter(author.map(MessageQueryFilters::authoredBy));
    }

    @Override
    public void setPublishedBefore(Optional<LocalDateTime> localDateTime) {
        super.addFilter(localDateTime.map(MessageQueryFilters::publishedBefore));
    }

    @Override
    public void setPublishedAfter(Optional<LocalDateTime> localDateTime) {
        super.addFilter(localDateTime.map(MessageQueryFilters::publishedAfter));
    }

    public void setContains(Optional<String> text) {
        super.addFilter(text.map(MessageQueryFilters::contains));
    }
}
