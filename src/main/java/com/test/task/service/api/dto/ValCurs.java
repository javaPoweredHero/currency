package com.test.task.service.api.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
@JacksonXmlRootElement(localName = "ValCurs")
public class ValCurs {
    @JacksonXmlProperty(localName = "Date", isAttribute = true)
    String date;
    @JacksonXmlProperty(localName = "name", isAttribute = true)
    String name;
    @JacksonXmlProperty(localName = "Valute")
    @JacksonXmlElementWrapper(useWrapping=false)
    List<Valute> currencyDtoList;
}
