package com.sergiomartinrubio.stakeacumulatorservice.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PlayerStakeMessage {
    private long accountId;
    private BigDecimal stake;
}
