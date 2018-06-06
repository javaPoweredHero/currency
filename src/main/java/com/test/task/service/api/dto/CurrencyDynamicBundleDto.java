package com.test.task.service.api.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@JacksonXmlRootElement(localName = "ValCurs")
public class CurrencyDynamicBundleDto {

    @JacksonXmlProperty(localName = "ID", isAttribute = true)
    private String currencyId;
    @JacksonXmlProperty(localName = "DateRange1", isAttribute = true)
    private String startDate;
    @JacksonXmlProperty(localName = "DateRange2", isAttribute = true)
    private String endDate;
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    String name;
    @JacksonXmlProperty(localName = "Record")
    @JacksonXmlElementWrapper(useWrapping = false)
    List<CurrencyDynamicDto> currencyDynamicDtoList;
}
