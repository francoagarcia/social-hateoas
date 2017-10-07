package com.github.francoagarcia.social.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.francoagarcia.social.domain.*;
import com.github.francoagarcia.social.querydsl.FindMessageQueryDslPredicate;
import com.github.francoagarcia.social.querydsl.GetMessageByIdQueryDslPredicate;
import com.github.francoagarcia.social.querydsl.InboxQueryDslPredicate;
import com.github.francoagarcia.social.rest.command.*;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import javax.validation.Validator;
import java.io.IOException;
import java.lang.reflect.Type;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class RestConfiguration {
    @Bean @Scope(SCOPE_PROTOTYPE)
    public CreateUserCommand createUserCommand(UserRepository repository) {
        return new CreateUserCommand(repository);
    }

    @Bean @Scope(SCOPE_PROTOTYPE)
    public PublishMessageCommand publishMessageCommand(UserRepository repository) {
        return new PublishMessageCommand(repository);
    }

    @Bean @Scope(SCOPE_PROTOTYPE)
    public GetMessageByIdQuery getMessageByIdQuery(MessageRepository repository) {
        return new GetMessageByIdQueryDslPredicate(repository);
    }

    @Bean @Scope(SCOPE_PROTOTYPE)
    public FindMessageQuery findMessageQuery(MessageRepository repository) {
        return new FindMessageQueryDslPredicate(repository);
    }

    @Bean @Scope(SCOPE_PROTOTYPE)
    public InboxQuery inboxQuery(MessageRepository repository) {
        return new InboxQueryDslPredicate(repository);
    }

    @Bean @Scope(SCOPE_PROTOTYPE)
    public ReplyMessageCommand replyMessageCommand(UserRepository userRepository, MessageRepository messageRepository) {
        return new ReplyMessageCommand(userRepository, messageRepository);
    }

    @Bean @Scope(SCOPE_PROTOTYPE)
    public FollowUserCommand followUserCommand(UserRepository userRepository) {
        return new FollowUserCommand(userRepository);
    }

    @Bean
    public CommandValidator commandValidator(Validator validator) {
        return new CommandValidator(validator);
    }

    @Bean
    public HttpMessageConverters converters(ObjectMapper objectMapper, ApplicationContext applicationContext) {
        return new HttpMessageConverters(
                new MappingJackson2HttpMessageConverter(objectMapper) {
                    @Override
                    public boolean canRead(Class<?> clazz, MediaType mediaType) {
                        return canRead(mediaType) && !applicationContext.getBeansOfType(clazz).isEmpty();
                    }

                    @Override
                    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
                        return canRead((Class) type, mediaType);
                    }

                    @Override
                    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
                        return objectMapper
                                .readerForUpdating(applicationContext.getBean(clazz))
                                .readValue(inputMessage.getBody());
                    }

                    @Override
                    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException {
                        return readInternal((Class) type, inputMessage);
                    }
                }
        );
    }
}
