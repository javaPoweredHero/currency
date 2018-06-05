package com.test.task.common.exceptions.conflict;

import com.test.task.common.exceptions.type.TypedException;

public class ConflictException extends TypedException {

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String type, String message) {
        super(type, message);
    }

    public ConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
