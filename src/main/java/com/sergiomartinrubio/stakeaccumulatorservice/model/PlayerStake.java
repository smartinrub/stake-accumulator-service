package com.sergiomartinrubio.stakeaccumulatorservice.model;

import lombok.Value;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Value
public class PlayerStake {
    @NotNull
    @Min(value = 1, message = "Account ID starts from 1")
    long accountId;

    // Assuming the stake amount cannot be zero
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount cannot be zero or less than zero")
    BigDecimal stake;
}
