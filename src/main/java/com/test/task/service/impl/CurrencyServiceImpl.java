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
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.task.common.exceptions.currency.CurrencyDataReceivingException;
import com.test.task.common.exceptions.currency.CurrencyDynamicMappingException;
import com.test.task.common.exceptions.currency.CurrencyMappingException;
import com.test.task.common.systemDictionaries.currency.DatasourceFormat;
import com.test.task.common.systemDictionaries.currency.DatasourceUrl;
import com.test.task.dao.domain.CurrencyRecord;
import com.test.task.dao.repository.CurrencyRepository;
import com.test.task.service.api.currency.CurrencyService;
import com.test.task.service.api.dto.CurrencyBundleDto;
import com.test.task.service.api.dto.CurrencyDynamicBundleDto;
import com.test.task.service.impl.mapper.CurrencyMapper;
import com.test.task.web.config.ApplicationProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

    private final ApplicationProperties properties;
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;
    private XmlMapper xmlMapper = new XmlMapper();

    @Override
    public CurrencyBundleDto releaseCurrencyBundle(LocalDate date) {
        String requestUrl = String.format(DatasourceUrl.Currencies.FULL, date
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

    @Override
    public CurrencyDynamicBundleDto releaseCurrencyDynamics(LocalDate startDate, LocalDate endDate, String currencyId) {
        String requestUrl = String.format(DatasourceUrl.CurrencyDynamic.FULL,
                startDate.format(DateTimeFormatter.ofPattern(DatasourceFormat.CURRENCY_DATE_FORMAT)),
                endDate.format(DateTimeFormatter.ofPattern(DatasourceFormat.CURRENCY_DATE_FORMAT)),
                currencyId);
        Charset charset = resolveXmlEncoding(requestUrl);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new URL(requestUrl).openStream(), charset))) {
            return xmlMapper.readValue(br, CurrencyDynamicBundleDto.class);
        } catch (IOException ex) {
            log.error(ex.getLocalizedMessage());
            throw new CurrencyDynamicMappingException();
        }
    }

    @Override
    @Transactional
    public CurrencyBundleDto releaseCurrencyRequiredList(List<String> requiredList, LocalDate date) {
        Set<CurrencyRecord> dbRecords = currencyRepository.findAllByDate(date);
        if (CollectionUtils.isEmpty(dbRecords)) {
            CurrencyBundleDto currencyBundleDto = releaseCurrencyBundle(date);
            currencyRepository.saveAll(currencyBundleDto.getCurrencyDtoList()
                    .stream()
                    .map(item -> currencyMapper.fromDto(item).setDate(date))
                    .collect(Collectors.toList()));
            currencyBundleDto.getCurrencyDtoList().removeIf(dto -> !requiredList.contains(dto.getCharCode()));
            return currencyBundleDto;
        } else {
            Set<CurrencyRecord> relevantDbRecords = dbRecords.stream()
                    .filter(item -> requiredList.contains(item.getCharCode()))
                    .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(relevantDbRecords) && relevantDbRecords.size() == requiredList.size()) {
                return new CurrencyBundleDto()
                        .setDate(date.format(DateTimeFormatter.ofPattern(DatasourceFormat.CURRENCY_DATE_FORMAT)))
                        .setName(DatasourceFormat.CURRENCY_MARKET)
                        .setCurrencyDtoList(dbRecords.stream().map(currencyMapper::toDto)
                                .collect(Collectors.toList()));
            } else { // not all present
                CurrencyBundleDto currencyBundleDto = releaseCurrencyBundle(date);
                currencyRepository.saveAll(currencyBundleDto
                        .getCurrencyDtoList().stream()
                        .map(item -> currencyMapper.fromDto(item).setDate(date))
                        .filter(item -> !dbRecords.contains(item))
                        .collect(Collectors.toList()));
                currencyBundleDto.getCurrencyDtoList().removeIf(dto -> !requiredList.contains(dto.getCharCode()));
                return currencyBundleDto;
            }
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

    private CurrencyBundleDto sortCurrencies(CurrencyBundleDto dtoBundle) {
        if (Objects.isNull(dtoBundle) || CollectionUtils.isEmpty(dtoBundle.getCurrencyDtoList())) {
            throw new CurrencyDataReceivingException();
        }

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
