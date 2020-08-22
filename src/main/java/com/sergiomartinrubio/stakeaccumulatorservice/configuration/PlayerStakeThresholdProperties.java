package com.sergiomartinrubio.stakeaccumulatorservice.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.math.BigDecimal;

@Data
@ConstructorBinding
@ConfigurationProperties("stake.threshold")
public class PlayerStakeThresholdProperties {

    private BigDecimal amount;

    private Integer hours;
}
