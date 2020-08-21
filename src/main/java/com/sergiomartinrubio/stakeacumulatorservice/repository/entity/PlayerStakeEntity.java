package com.sergiomartinrubio.stakeacumulatorservice.repository.entity;

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
@Table(name = "player_stake")
public class PlayerStakeEntity {

    @Id
    private UUID id;

    @Column(name = "account_id")
    private Long accountId;

    private BigDecimal stake;

    @Column(name = "creation_date_time")
    private LocalDateTime creationDateTime;
}
