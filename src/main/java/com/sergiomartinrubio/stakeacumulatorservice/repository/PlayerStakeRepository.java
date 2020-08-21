package com.sergiomartinrubio.stakeacumulatorservice.repository;

import com.sergiomartinrubio.stakeacumulatorservice.repository.entity.PlayerStakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PlayerStakeRepository extends JpaRepository<PlayerStakeEntity, UUID> {

    @Query(value = "SELECT * FROM player_stake " +
            "WHERE account_id = :accountId " +
            "AND NOW() < DATEADD(hour, :hours, creation_date_time)", nativeQuery = true)
    Set<PlayerStakeEntity> findAllByAccountAndHoursThreshold(@Param("accountId") Long accountId,
                                                             @Param("hours") int hours);
}
