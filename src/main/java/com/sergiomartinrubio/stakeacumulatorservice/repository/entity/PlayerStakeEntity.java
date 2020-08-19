package com.sergiomartinrubio.stakeacumulatorservice.repository.entity;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class PlayerStakeEntity {
    long accountId;
    BigDecimal stake;
    LocalDateTime creationDateTime;
}
