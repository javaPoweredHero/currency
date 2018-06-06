package com.test.task.service.impl;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.test.task.common.exceptions.currency.CurrencyDataReceivingException;
import com.test.task.domain.currency.CurrencyRecord;
import com.test.task.domain.repository.CurrencyRepository;
import com.test.task.service.api.currency.CurrencyService;
import com.test.task.service.impl.mapper.CurrencyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyScheduledService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyService currencyService;
    private final CurrencyMapper mapper;

    @Scheduled(cron = "0 0 10 * * MON-FRI")
    @Transactional
    public void loadCurrenciesDataJob() {
        LocalDate date = LocalDate.now();
        Set<CurrencyRecord> currencyDbList = currencyRepository.findAllByDate(date);
        try {
            Set<CurrencyRecord> currencyBankList = currencyService.releaseCurrency(LocalDate.now()).getCurrencyDtoList()
                    .stream()
                    .map(mapper::fromDto)
                    .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(currencyDbList)) {
                currencyRepository.saveAll(currencyBankList
                        .stream()
                        .filter(item -> !currencyDbList.contains(item))
                        .collect(Collectors.toSet()));
            } else {
                currencyRepository.saveAll(currencyBankList);
            }
        } catch (CurrencyDataReceivingException e) {
            log.error("scheduled service failure");
        }
    }
}
