package com.test.task.web.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "test-app")
@Data
@Slf4j
@Validated
public class ApplicationProperties {

    @NotNull
    List<String> currencySortOrder;
}
