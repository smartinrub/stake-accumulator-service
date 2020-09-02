package com.sergiomartinrubio.stakeaccumulatorservice.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PlayerStake {

    @Id
    private UUID id;

    private Long accountId;

    private BigDecimal stake;

    private LocalDateTime creationDateTime;
}
