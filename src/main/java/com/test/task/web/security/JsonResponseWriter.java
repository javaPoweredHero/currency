package com.test.task.web.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonResponseWriter {

    private final ObjectMapper jacksonMapper;

    public void writeJson(Object body,
                          HttpStatus status,
                          HttpServletResponse response) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        String out = jacksonMapper.writeValueAsString(body);
        response.getWriter().print(out);
        response.flushBuffer();
    }
}
