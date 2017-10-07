package com.github.francoagarcia.social.rest.command;

import com.github.francoagarcia.social.domain.User;
import com.github.francoagarcia.social.domain.UserRepository;
import com.github.francoagarcia.social.rest.exceptions.UserNotFoundException;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class FollowUserCommand {
    private final UserRepository userRepository;

    @NotNull
    private UUID followerId;

    @NotNull
    private UUID targetId;

    public FollowUserCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute() throws UserNotFoundException {
        User follower = userRepository.findById(followerId).orElseThrow(UserNotFoundException::new);
        User target = userRepository.findById(targetId).orElseThrow(UserNotFoundException::new);

        follower.follow(target);
    }

    public void setFollowerId(UUID followerId) {
        this.followerId = followerId;
    }

    public void setTargetId(UUID followerId) {
        this.targetId = followerId;
    }
}
