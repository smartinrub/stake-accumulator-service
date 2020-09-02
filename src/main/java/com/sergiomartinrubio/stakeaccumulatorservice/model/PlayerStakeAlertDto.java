package com.sergiomartinrubio.stakeaccumulatorservice.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder
public class PlayerStakeAlertDto {
    UUID id;
    Long accountId;
    BigDecimal cumulatedAmount;
    LocalDateTime creationDateTime;
}
