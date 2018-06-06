package com.test.task.web.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.expression.spel.support.StandardTypeLocator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class DefaultSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    @Override
    public StandardEvaluationContext createEvaluationContextInternal(final Authentication auth,
                                                                     final MethodInvocation mi) {
        StandardEvaluationContext standardEvaluationContext = super.createEvaluationContextInternal(auth, mi);
        ((StandardTypeLocator) standardEvaluationContext.getTypeLocator()).
                registerImport("com.test.task.common.domain.systemDictionaries.roles");
        return standardEvaluationContext;
    }
}
