package com.sergiomartinrubio.stakeaccumulatorservice.model;

import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class PlayerStake {
    @NotNull
    @Min(1)
    long accountId;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal stake;
}
