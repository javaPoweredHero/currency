package com.test.task.web.config;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(prefix = "test-app")
@Data
@Slf4j
@Validated
public class ApplicationProperties {

    @NotNull
    List<String> currencySortOrder;
}
