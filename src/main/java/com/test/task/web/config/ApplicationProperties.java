package com.test.task.web.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@ConfigurationProperties(prefix = "test-app")
@Data
@Slf4j
@Validated
public class ApplicationProperties {

    @NotEmpty
    List<String> currencySortOrder;
}
