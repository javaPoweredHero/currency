package com.test.task.service.api.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CurrencyDynamicDto {

    @JacksonXmlProperty(localName = "Date", isAttribute = true)
    private String date;
    @JacksonXmlProperty(localName = "Id", isAttribute = true)
    private String currencyId;
    @JacksonXmlProperty(localName = "Nominal")
    private int nominal;
    @JacksonXmlProperty(localName = "Value")
    private Double value;

    @JsonSetter("Value")
    public void setCommaValue(String value) {
        this.value = Double.parseDouble(value.replace(",", "."));
    }
}
