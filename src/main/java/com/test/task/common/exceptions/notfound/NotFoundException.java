package com.test.task.common.exceptions.notfound;

import com.google.common.collect.Sets;
import com.test.task.common.exceptions.type.TypedException;

import lombok.Getter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Getter
public class NotFoundException extends TypedException {

    private static final long serialVersionUID = -5495936701721518055L;
    private final String entity;
    private final String field;
    private final Collection<? extends Serializable> values;

    public <T extends Serializable> NotFoundException(String entity, T idValue) {
        this(entity, "id", idValue);
    }

    public <T extends Serializable> NotFoundException(String entity, Collection<T> idValues) {
        this(entity, "id", idValues);
    }

    public <T extends Serializable> NotFoundException(String entity, Set<T> foundIds, Set<T> expectedIds) {
        this(entity, Sets.difference(expectedIds, foundIds));
    }

    public <T extends Serializable> NotFoundException(String entity, String field, T value) {
        this(entity, field, Collections.singletonList(value));
    }

    public NotFoundException(String message) {
        super(message);
        this.entity = null;
        this.field = null;
        this.values = null;
    }

    private NotFoundException(String entity, String field, Collection<? extends Serializable> values) {
        super("Not found " + entity + " with " + field + " = " + values);
        this.entity = entity;
        this.field = field;
        this.values = values;
    }
}
