package com.test.task.common.systemDictionaries.web;

public interface Urls {
    String ROOT = "/api/v1/";

    interface Currencies {
        String PART = "currencies";
        String FULL = ROOT + PART;

        interface Dynamic {
            String PART = "dynamic";
            String FULL = Currencies.FULL + "/" + PART;
        }

        interface Custom {
            String PART = "custom";
            String FULL = Currencies.FULL + "/" + PART;
        }
    }
}
