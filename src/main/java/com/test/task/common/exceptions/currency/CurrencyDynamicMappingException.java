package com.test.task.common.exceptions.currency;

import com.test.task.common.exceptions.type.TypedException;

public class CurrencyDynamicMappingException extends TypedException {

    public CurrencyDynamicMappingException() {
        super("Currency dynamic mapping failure");
    }
}
