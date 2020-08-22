package com.sergiomartinrubio.stakeacumulatorservice.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlayerStakeAlertMessage {
    private Long accountId;
    private BigDecimal cumulatedAmount;
}
