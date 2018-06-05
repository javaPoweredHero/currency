package com.test.task.web.security;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.test.task.common.exceptions.type.TypedErrorImpl;
import com.test.task.common.exceptions.type.TypedException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExceptionWriter {

    private final JsonResponseWriter writer;

    public void writeTypedException(TypedException typedException, HttpStatus httpStatus, HttpServletResponse response)
            throws IOException {

        writer.writeJson(typedException, httpStatus, response);
    }

    public void writeException(Exception exception, HttpStatus httpStatus, HttpServletResponse response)
            throws IOException {

        writer.writeJson(new TypedErrorImpl(exception), httpStatus, response);
    }
}
