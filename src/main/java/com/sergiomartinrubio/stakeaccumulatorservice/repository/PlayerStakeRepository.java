package com.sergiomartinrubio.stakeaccumulatorservice.repository;

import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeEntity;
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
            "AND current_timestamp < DATEADD(minute, :timeWindowInMinutes, creation_date_time)", nativeQuery = true)
    Set<PlayerStakeEntity> findAllByAccountAndTimeWindowThreshold(@Param("accountId") Long accountId,
                                                                  @Param("timeWindowInMinutes") int timeWindowInMinutes);
}
