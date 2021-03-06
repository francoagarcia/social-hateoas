package com.github.francoagarcia.social.domain;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1544439178L;

    public static final QUser user = new QUser("user");

    public final QPersistentObject _super = new QPersistentObject(this);

    public final StringPath email = createString("email");

    public final SetPath<User, QUser> followers = this.<User, QUser>createSet("followers", User.class, QUser.class, PathInits.DIRECT2);

    //inherited
    public final ComparablePath<java.util.UUID> id = _super.id;

    public final SetPath<Message, QMessage> messages = this.<Message, QMessage>createSet("messages", Message.class, QMessage.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

