package ru.otus.springframework.hw05.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@ConfigurationProperties("application")
@Data
public class AppSettings {
    private String dataFilePath;
    private boolean showDetailedResult;
    private boolean showWrongAnswers;
    private Locale locale;
}
