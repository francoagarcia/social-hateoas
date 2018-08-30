package com.github.francoagarcia.social.querydsl;

import com.github.francoagarcia.social.domain.GetMessageByIdQuery;
import com.github.francoagarcia.social.domain.Message;
import com.github.francoagarcia.social.domain.MessageRepository;
import com.querydsl.core.types.Predicate;

import java.util.UUID;

public class GetMessageByIdQueryPredicate extends QueryPredicate<Message> implements GetMessageByIdQuery {
    private UUID messageId;

    public GetMessageByIdQueryPredicate(MessageRepository repository) {
        super(repository);
    }

    @Override
    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    @Override
    protected Predicate buildPredicate() {
        return MessageQueryFilters.messageId(messageId);
    }
}
