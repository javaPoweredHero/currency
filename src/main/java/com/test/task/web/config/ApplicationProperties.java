package com.test.task.web.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(prefix = "test-app")
@Data
@Slf4j
public class ApplicationProperties {

    List<String> currencySortOrder;
}
