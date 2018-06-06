package com.test.task.common.systemDictionaries.currency;

public interface DatasourceUrl {

    String ROOT = "http://www.cbr.ru/scripts/";

    interface Currencies {
        String PART = "XML_daily.asp?date_req=%s";
        String FULL = ROOT + PART;
    }

    interface CurrencyDynamic {
        String PART = "XML_dynamic.asp?date_req1=%s&date_req2=%s&VAL_NM_RQ=%s";
        String FULL = ROOT + PART;
    }
}
