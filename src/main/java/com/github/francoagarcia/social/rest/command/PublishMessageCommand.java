package com.github.francoagarcia.social.rest.command;

import com.github.francoagarcia.social.domain.Message;
import com.github.francoagarcia.social.domain.User;
import com.github.francoagarcia.social.domain.UserRepository;
import com.github.francoagarcia.social.rest.exceptions.UserNotFoundException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class PublishMessageCommand {
    private final UserRepository userRepository;

    @NotNull(message = "content.empty.message")
    @Size(min = 1, max = 140, message = "content.size.invalid.message")
    private String message;

    @NotNull(message = "publish.user.null.message")
    private UUID userId;

    public PublishMessageCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Message execute() throws UserNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return user.publish(message);
    }
}
