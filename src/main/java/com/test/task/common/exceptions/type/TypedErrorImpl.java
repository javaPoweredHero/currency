package com.test.task.common.exceptions.type;

import java.beans.Introspector;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TypedErrorImpl implements TypedError, Serializable {

    private static final long serialVersionUID = -1364581019837905006L;
    private final String type;
    private final String message;

    public TypedErrorImpl(Throwable throwable) {
        this.type = buildTypeByClass(throwable.getClass());
        this.message = throwable.getMessage();
    }

    public TypedErrorImpl(Class<?> errorClass, String message) {
        this.type = buildTypeByClass(errorClass);
        this.message = message;
    }

    private String buildTypeByClass(Class<?> errorClass) {
        return Introspector.decapitalize(errorClass.getSimpleName());
    }
}
