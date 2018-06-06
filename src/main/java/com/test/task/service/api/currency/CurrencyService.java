package com.test.task.service.api.currency;

import java.time.LocalDate;

import com.test.task.service.api.dto.CurrencyBundleDto;
import com.test.task.service.api.dto.CurrencyDynamicBundleDto;

public interface CurrencyService {

    CurrencyBundleDto releaseCurrentCurrency();

    CurrencyDynamicBundleDto releaseCurrencyDynamic(LocalDate startDate, LocalDate endDate, String currencyId);
}
