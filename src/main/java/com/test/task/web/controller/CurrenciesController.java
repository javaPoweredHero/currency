package com.test.task.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.task.common.systemDictionaries.web.Urls;
import com.test.task.service.api.currency.CurrencyService;
import com.test.task.service.api.dto.CurrencyBundleDto;
import com.test.task.service.api.dto.CurrencyDynamicBundleDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Currency", description = "Api provides operations with currencies. All dates in ISO format")
@RequestMapping(path = Urls.Currencies.FULL)
@RestController
public class CurrenciesController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping()
    @ApiOperation(value = "Current currencies api")
    public CurrencyBundleDto getCurrentCurrencies() {
        return currencyService.releaseCurrencyBundle(LocalDate.now());
    }

    @GetMapping(Urls.Currencies.Dynamic.PART)
    @ApiOperation(value = "Currency dynamics api")
    public CurrencyDynamicBundleDto getCurrenciesDynamics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String currencyId) {
        return currencyService.releaseCurrencyDynamics(startDate, endDate, currencyId);
    }

    @GetMapping(Urls.Currencies.Custom.PART)
    @ApiOperation(value = "Custom domain request based on params")
    public CurrencyBundleDto getCurrenciesList(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam List<String> currenciesList) {
        return currencyService.releaseCurrencyRequiredList(currenciesList, date);
    }
}
