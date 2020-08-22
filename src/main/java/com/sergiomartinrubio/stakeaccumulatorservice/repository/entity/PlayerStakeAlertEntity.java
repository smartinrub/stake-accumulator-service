package com.sergiomartinrubio.stakeaccumulatorservice.repository.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "player_stake_alert")
public class PlayerStakeAlertEntity {
    @Id
    private UUID id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "cumulated_amount")
    private BigDecimal cumulatedAmount;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
}
