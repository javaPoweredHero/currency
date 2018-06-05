package com.test.task.common.exceptions.type;

/**
 * Base type for all errors going outside.
 */
public interface TypedError {

    /**
     * @return Error type. Must be unique across application.
     */
    String getType();

    /**
     * @return Error description for developers.
     */
    String getMessage();
}
