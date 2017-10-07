package com.github.francoagarcia.social.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ extends PersistentObject_{

	public static volatile SingularAttribute<Message, LocalDateTime> publishTime;
	public static volatile ListAttribute<Message, Message> replies;
	public static volatile SingularAttribute<Message, User> author;
	public static volatile SingularAttribute<Message, String> body;

}

