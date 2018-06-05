package com.test.task.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.task.common.domain.systemDictionaries.web.Urls;
import com.test.task.service.api.currency.CurrencyService;
import com.test.task.service.api.dto.ValCurs;

@RestController
@RequestMapping(path = Urls.Currencies.FULL)
public class CurrenciesController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping
    public ValCurs releaseCurrencies() {
        return currencyService.releaseCurrentCurrency();
    }
}
