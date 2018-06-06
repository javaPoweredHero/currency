package com.test.task.common.systemDictionaries.web;

public interface Urls {
    String ROOT = "api/v1/";

    interface Currencies {
        String PART = "currencies";
        String FULL = ROOT + PART;

        interface Dynamic {
            String PART = "dynamic";
            String FULL = Currencies.FULL + "/" + PART;
            String START_DATE_PARAM = "startDate";
            String END_DATE_PARAM = "endDate";
            String CURRENCY_ID_PARAM = "currencyId";
        }

        interface Custom {
            String PART = "custom";;
        }
    }
}
