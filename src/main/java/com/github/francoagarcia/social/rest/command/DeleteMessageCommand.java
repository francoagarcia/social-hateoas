package com.github.francoagarcia.social.rest.command;

import com.github.francoagarcia.social.domain.Message;
import com.github.francoagarcia.social.domain.MessageRepository;
import com.github.francoagarcia.social.domain.User;
import com.github.francoagarcia.social.domain.UserRepository;
import com.github.francoagarcia.social.rest.exceptions.MessageNotFoundException;
import com.github.francoagarcia.social.rest.exceptions.UserNotFoundException;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeleteMessageCommand {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @NotNull
    private UUID userId;

    @NotNull
    private UUID messageId;

    public DeleteMessageCommand(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public void execute() throws UserNotFoundException, MessageNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);

        user.unpublish(message);
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }
}
