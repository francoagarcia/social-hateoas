package com.github.francoagarcia.social.rest.command;

import com.github.francoagarcia.social.domain.Message;
import com.github.francoagarcia.social.domain.MessageRepository;
import com.github.francoagarcia.social.domain.User;
import com.github.francoagarcia.social.domain.UserRepository;
import com.github.francoagarcia.social.rest.exceptions.MessageNotFoundException;
import com.github.francoagarcia.social.rest.exceptions.UserNotFoundException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class ReplyMessageCommand {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @NotNull(message = "reply.user.null.message")
    private UUID authorId;

    @NotNull(message = "reply.original.null.message")
    private UUID messageId;

    @NotNull(message = "content.empty.message")
    @Size(min = 1, max = 140, message = "content.size.invalid.message")
    private String content;


    public ReplyMessageCommand(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public Message execute() throws MessageNotFoundException, UserNotFoundException {
        Message message = messageRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        User user = userRepository.findById(authorId).orElseThrow(UserNotFoundException::new);

        return user.reply(content, message);
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
