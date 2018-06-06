package com.test.task.web.controller;

import com.test.task.common.systemDictionaries.web.Urls;
import com.test.task.service.api.currency.CurrencyService;
import com.test.task.service.api.dto.CurrencyBundleDto;
import com.test.task.service.api.dto.CurrencyDynamicBundleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Api(tags = "Currency", description = "Api provides operations with currencies")
@RequestMapping(path = Urls.Currencies.FULL)
@RestController
public class CurrenciesController {

    @Autowired
    CurrencyService currencyService;

    @GetMapping()
    @ApiOperation(value = "Current currencies api", authorizations = {@Authorization(value = "basicAuth")})
    public CurrencyBundleDto getCurrentCurrencies() {
        return currencyService.releaseCurrencyBundle(LocalDate.now());
    }

    @GetMapping(Urls.Currencies.Dynamic.PART)
    @ApiOperation(value = "Currency dynamic api", authorizations = {@Authorization(value = "basicAuth")})
    public CurrencyDynamicBundleDto getCurrenciesDynamics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String currencyId) {
        return currencyService.releaseCurrencyDynamics(startDate, endDate, currencyId);
    }

    @PreAuthorize("hasRole(T(UserRoles).ADMIN)")
    @GetMapping(Urls.Currencies.Custom.PART)
    @ApiOperation(value = "Custom currency request", authorizations = {@Authorization(value = "basicAuth")})
    public CurrencyBundleDto getCurrenciesList(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam List<String> currenciesList) {
        return currencyService.releaseCurrencyRequiredList(currenciesList, date);
    }
}
