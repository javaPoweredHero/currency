package com.test.task.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.task.common.domain.systemDictionaries.currency.DatasourceDateFormat;
import com.test.task.common.domain.systemDictionaries.currency.DatasourceUrl;
import com.test.task.common.exceptions.currency.CurrencyMappingException;
import com.test.task.service.api.currency.CurrencyService;
import com.test.task.service.api.dto.ValCurs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    @Override
    public ValCurs releaseCurrentCurrency() {
        XmlMapper xmlMapper = new XmlMapper();
        String requestUrl = String.format(DatasourceUrl.Currencies.FULL, LocalDate.now()
                .format(DateTimeFormatter.ofPattern(DatasourceDateFormat.CURRENCY_DATE_FORMAT)));
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(requestUrl).openStream(),
                    "windows-1251"));
            return xmlMapper.readValue(br, ValCurs.class);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            throw new CurrencyMappingException();
        }
    }
}
