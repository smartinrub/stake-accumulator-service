package com.sergiomartinrubio.stakeaccumulatorservice.repository;

import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface PlayerStakeAlertRepository extends JpaRepository<PlayerStakeAlertEntity, UUID> {

    Set<PlayerStakeAlertEntity> findAllByAccountIdEquals(Long accountId);
}
