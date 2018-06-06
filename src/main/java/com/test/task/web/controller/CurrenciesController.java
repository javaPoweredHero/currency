package com.test.task.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.task.common.domain.systemDictionaries.web.Urls;
import com.test.task.service.api.currency.CurrencyService;
import com.test.task.service.api.dto.CurrencyBundleDto;
import com.test.task.service.api.dto.CurrencyDynamicBundleDto;
import com.test.task.service.api.dto.CurrencyDynamicDto;

import io.swagger.annotations.ApiOperation;

@RequestMapping(path = Urls.Currencies.FULL)
@RestController
public class CurrenciesController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("get current currencies")
    public CurrencyBundleDto getCurrencies() {
        return currencyService.releaseCurrentCurrency();
    }

    @GetMapping(Urls.Currencies.Dynamic.PART)
    @ApiOperation("get currency dynamic")
    public CurrencyDynamicBundleDto getCurrenciesDynamic(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String currencyId) {
        return currencyService.releaseCurrencyDynamic(startDate, endDate,  currencyId);
    }
}
