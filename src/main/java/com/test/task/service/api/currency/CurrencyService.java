package com.test.task.service.api.currency;

import java.time.LocalDate;
import java.util.List;

import com.test.task.service.api.dto.CurrencyBundleDto;
import com.test.task.service.api.dto.CurrencyDynamicBundleDto;

public interface CurrencyService {

    CurrencyBundleDto releaseCurrencyBundle(LocalDate date);

    CurrencyDynamicBundleDto releaseCurrencyDynamics(LocalDate startDate, LocalDate endDate, String currencyId);

    CurrencyBundleDto releaseCurrencyRequiredList(List<String> requiredList, LocalDate date);
}
