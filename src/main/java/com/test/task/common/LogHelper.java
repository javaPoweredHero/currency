package com.test.task.common;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.MDC;

public class LogHelper {

    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder();
    private static final int UUID_SIZE_BYTES = 16;


    private LogHelper() {
    }

    @Getter
    @AllArgsConstructor
    public enum ContextType {
        REQUEST("request");

        private final String name;
    }

    private static final String CONTEXT_TYPE_KEY = "contextType";
    private static final String CONTEXT_ID = "contextId";
    private static final String HTTP_METHOD = "http_method";


    public static String setRequestContextWithNewId(String httpMethod) {
        String id = generateContextId();
        setRequestContext(id, httpMethod);
        return id;
    }

    public static String setContextWithNewId(ContextType type) {
        String id = generateContextId();
        setContext(type, id);
        return id;
    }

    private static void setContext(ContextType type, String id) {
        MDC.put(CONTEXT_TYPE_KEY, type.getName());
        MDC.put(CONTEXT_ID, id);
    }

    private static void setRequestContext(String id, String httpMethod) {
        setContext(ContextType.REQUEST, id);
        MDC.put(HTTP_METHOD, httpMethod);
    }

    private static String generateContextId() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer uuidBytes = ByteBuffer.wrap(new byte[UUID_SIZE_BYTES]);
        uuidBytes.putLong(uuid.getMostSignificantBits());
        uuidBytes.putLong(uuid.getLeastSignificantBits());
        return ENCODER.encodeToString(uuidBytes.array()).replaceAll("==$", "");
    }

    public static void clearContext() {
        MDC.clear();
    }
}
