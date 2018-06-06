package com.test.task.service.api.currency;

import java.time.LocalDate;
import java.util.List;

import com.test.task.service.api.dto.CurrencyBundleDto;
import com.test.task.service.api.dto.CurrencyDynamicBundleDto;

public interface CurrencyService {

    CurrencyBundleDto releaseCurrency(LocalDate date);

    CurrencyDynamicBundleDto releaseCurrencyDynamic(LocalDate startDate, LocalDate endDate, String currencyId);

    CurrencyBundleDto releaseCurrencyRequiredList(List<String> requiredList, LocalDate date);
}
