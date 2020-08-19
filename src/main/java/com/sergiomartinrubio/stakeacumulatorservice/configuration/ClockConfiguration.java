package com.sergiomartinrubio.stakeacumulatorservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ClockConfiguration {
    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of("UTC"));
    }
}
