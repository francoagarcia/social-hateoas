package com.github.francoagarcia.social.domain;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.ComparablePath;
import com.querydsl.core.types.dsl.EntityPathBase;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QPersistentObject is a Querydsl query type for PersistentObject
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QPersistentObject extends EntityPathBase<PersistentObject> {

    private static final long serialVersionUID = -589908255L;

    public static final QPersistentObject persistentObject = new QPersistentObject("persistentObject");

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public QPersistentObject(String variable) {
        super(PersistentObject.class, forVariable(variable));
    }

    public QPersistentObject(Path<? extends PersistentObject> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersistentObject(PathMetadata metadata) {
        super(PersistentObject.class, metadata);
    }

}

