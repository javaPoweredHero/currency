package com.test.task.common.exceptions.currency;

import com.test.task.common.exceptions.type.TypedException;

public class CurrencyMappingException extends TypedException {

    public CurrencyMappingException () {
        super("Currency mapping failure");
    }
}
