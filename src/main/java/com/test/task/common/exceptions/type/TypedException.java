package com.test.task.common.exceptions.type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Base exception class to throw outside of service layer.
 * Contains <code>type</code> property to uniquely identify error.
 */
@JsonIgnoreProperties({"localizedMessage", "cause", "stackTrace", "suppressed"})
public abstract class TypedException extends RuntimeException implements TypedError {

    private static final long serialVersionUID = -6177042571577063818L;
    private final TypedErrorImpl typedErrorImpl;


    protected TypedException(String message) {
        super(message);
        this.typedErrorImpl = new TypedErrorImpl(this.getClass(), message);
    }

    protected TypedException(String type, String message) {
        super(message);
        this.typedErrorImpl = new TypedErrorImpl(type, message);
    }

    protected TypedException(String message, Throwable cause) {
        super(message, cause);
        this.typedErrorImpl = new TypedErrorImpl(this.getClass(), message);
    }

    protected TypedException(String type, String message, Throwable cause) {
        super(message, cause);
        this.typedErrorImpl = new TypedErrorImpl(type, message);
    }

    @Override
    public String getType() {
        return typedErrorImpl.getType();
    }
}