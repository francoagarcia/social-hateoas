package com.github.francoagarcia.social.rest.command;

import com.github.francoagarcia.social.domain.User;
import com.github.francoagarcia.social.domain.UserRepository;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserCommand {
    private final UserRepository repository;

    @NotNull(message = "username.empty.message")
    @Size(min = 1, message = "username.empty.message")
    private String username;

    @NotNull(message = "email.empty.message")
    @Size(min = 1, message = "email.empty.message")
    private String email;

    public CreateUserCommand(UserRepository repository) {
        this.repository = repository;
    }

    public User execute() {
        User newUser = new User(username, email);
        return repository.save(newUser);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
