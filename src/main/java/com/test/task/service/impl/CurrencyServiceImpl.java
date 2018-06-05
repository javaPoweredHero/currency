package com.test.task.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.task.common.domain.systemDictionaries.currency.DatasourceFormat;
import com.test.task.common.domain.systemDictionaries.currency.DatasourceUrl;
import com.test.task.common.exceptions.currency.CurrencyMappingException;
import com.test.task.service.api.currency.CurrencyService;
import com.test.task.service.api.dto.CurrencyBundleDto;
import com.test.task.web.config.ApplicationProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final ApplicationProperties properties;

    @Override
    public CurrencyBundleDto releaseCurrentCurrency() {
        XmlMapper xmlMapper = new XmlMapper();
        String requestUrl = String.format(DatasourceUrl.Currencies.FULL, LocalDate.now()
                .format(DateTimeFormatter.ofPattern(DatasourceFormat.CURRENCY_DATE_FORMAT)));
        Charset charset = resolveXmlEncoding(requestUrl);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new URL(requestUrl).openStream(), charset))) {
            return sortCurrencies(xmlMapper.readValue(br, CurrencyBundleDto.class));
        } catch (IOException ex) {
            log.error(ex.getLocalizedMessage());
            throw new CurrencyMappingException();
        }
    }

    private Charset resolveXmlEncoding(String url) {
        XMLInputFactory f = XMLInputFactory.newFactory();
        try (InputStream inputStream = new URL(url).openStream()) {
            XMLStreamReader sr = f.createXMLStreamReader(inputStream);
            sr.next();
            Charset charset = StringUtils.isNotEmpty(sr.getEncoding()) ? Charset.forName(sr.getEncoding()) :
                    Charset.forName(DatasourceFormat.DEFAULT_CHARSET);
            sr.close();
            return charset;
        } catch (IOException | XMLStreamException | UnsupportedCharsetException e) {
            log.error(e.getLocalizedMessage());
            return Charset.forName(DatasourceFormat.DEFAULT_CHARSET);
        }
    }

    /*private CurrencyBundleDto sortCurrencies(CurrencyBundleDto dto) {
        List<CurrencyDto> relevantCurrencies = dto.getCurrencyDtoList().stream()
                .filter(valute -> properties.getCurrencySortOrder().contains(valute.getCharCode()))
                .sorted(Comparator.comparingInt(left -> properties.getCurrencySortOrder().indexOf(left.getCharCode())))
                .collect(Collectors.toList());
        List<CurrencyDto> otherCurrencies = dto.getCurrencyDtoList().stream()
                .filter(valute -> !properties.getCurrencySortOrder().contains(valute.getCharCode()))
                .sorted(Comparator.comparing(CurrencyDto::getCharCode))
                .collect(Collectors.toList());
        relevantCurrencies.addAll(otherCurrencies);
        return dto.setCurrencyDtoList(relevantCurrencies);
    }*/

    private CurrencyBundleDto sortCurrencies(CurrencyBundleDto dtoBundle) {
        dtoBundle.getCurrencyDtoList().sort((left, right) -> {
            if (properties.getCurrencySortOrder().contains(left.getCharCode()) &&
                    properties.getCurrencySortOrder().contains(right.getCharCode())) {
                return Integer.compare(properties.getCurrencySortOrder().indexOf(left.getCharCode()),
                        properties.getCurrencySortOrder().indexOf(right.getCharCode()));
            }

            if (properties.getCurrencySortOrder().contains(right.getCharCode()) &&
                    !properties.getCurrencySortOrder().contains(left.getCharCode())) {
                return 1;
            }

            if (!properties.getCurrencySortOrder().contains(right.getCharCode()) &&
                    properties.getCurrencySortOrder().contains(left.getCharCode())) {
                return -1;
            } else {
                return left.getCharCode().compareToIgnoreCase(right.getCharCode());
            }
        });
        return dtoBundle;
    }
}
