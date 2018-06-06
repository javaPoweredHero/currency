package com.test.task.service.impl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.test.task.domain.currency.CurrencyRecord;
import com.test.task.service.api.dto.CurrencyDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CurrencyMapper {

    @Mapping(target = "id", ignore = true)
    CurrencyRecord fromDto(CurrencyDto dto);

    CurrencyDto toDto(CurrencyRecord entity);
}
