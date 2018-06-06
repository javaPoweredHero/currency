package com.test.task.common.exceptions.currency;

import com.test.task.common.exceptions.type.TypedException;

public class CurrencyDataReceivingException extends TypedException {

    public CurrencyDataReceivingException() {
        super("Currency data retrieve from bank failure");
    }
}
