package com.test.task.common.domain.systemDictionaries.currency;

public interface DatasourceUrl {

    String ROOT = "http://www.cbr.ru/scripts/";

    interface Currencies {
        String PART = "XML_daily.asp?date_req=%s";
        String FULL = ROOT + PART;
    }
}
