package com.github.francoagarcia.social.rest;

import com.github.francoagarcia.social.domain.InboxQuery;
import com.github.francoagarcia.social.domain.Message;
import com.github.francoagarcia.social.domain.User;
import com.github.francoagarcia.social.rest.command.CommandValidator;
import com.github.francoagarcia.social.rest.command.CreateUserCommand;
import com.github.francoagarcia.social.rest.command.FollowUserCommand;
import com.github.francoagarcia.social.rest.command.PublishMessageCommand;
import com.github.francoagarcia.social.rest.exceptions.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(path = ApiEndpoints.V1 + "/users")
public class UserEndpoints {
    
    @Transactional
    @RequestMapping(method = POST)
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody CreateUserCommand createUserCommand)
            throws URISyntaxException {
        User newUser = createUserCommand.execute();
        UserResource resource = UserResource.builder().toResource(newUser);

        return created(new URI(resource.getId().getHref())).body(resource);
    }

    @Transactional
    @RequestMapping(method = POST, path = "/{userId}/messages")
    public ResponseEntity<MessageResource> publishMessage(
            @PathVariable("userId") UUID userId,
            @RequestBody PublishMessageCommand command,
            CommandValidator validator)
            throws URISyntaxException, UserNotFoundException {

        command.setUserId(userId);
        validator.validateOrFail(command);

        Message message = command.execute();
        MessageResource resource = MessageResource.builder().toResource(message);

        return created(new URI(resource.getId().getHref())).body(resource);
    }

    @Transactional(readOnly = true)
    @RequestMapping(method = GET, path = "/{userId}/inbox")
    public ResponseEntity<PageResource<MessageResource>> inbox(
            @ModelAttribute InboxQuery query,
            Pageable maybePageable,
            HttpServletRequest request) {
        Pageable pageable = Optional.ofNullable(maybePageable).orElse(new PageRequest(0, 20));

        Page<Message> page = query.findAll(pageable);

        return ok(MessageResource.builder().toResource(page, request, pageable));
    }

    @Transactional
    @RequestMapping(method = POST, path = "/{userId}/followers")
    public ResponseEntity<Void> follow(
            @PathVariable("userId") UUID userId,
            @RequestBody FollowUserCommand command,
            CommandValidator validator) throws UserNotFoundException {

        command.setTargetId(userId);
        validator.validateOrFail(command);

        command.execute();

        return noContent().build();
    }
}
