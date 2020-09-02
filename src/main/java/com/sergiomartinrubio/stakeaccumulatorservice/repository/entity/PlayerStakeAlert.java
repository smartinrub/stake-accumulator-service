package com.sergiomartinrubio.stakeaccumulatorservice.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class PlayerStakeAlert {
    @Id
    private UUID id;

    private Long accountId;

    private BigDecimal cumulatedAmount;

    private LocalDateTime creationDateTime;
}
