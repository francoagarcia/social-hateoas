package com.github.francoagarcia.social.domain;

import java.util.UUID;

public interface InboxQuery extends Query<Message> {
    void setUserId(UUID userId);
}
