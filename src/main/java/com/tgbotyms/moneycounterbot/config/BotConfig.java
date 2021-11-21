package com.tgbotyms.moneycounterbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")

public class BotConfig {
    // Registered bot name
    @Value("${bot.name}")
    String botUserName;

    // Bot's registration token
    @Value("${bot.token}")
    String token;
}
