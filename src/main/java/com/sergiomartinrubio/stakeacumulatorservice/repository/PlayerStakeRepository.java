package com.sergiomartinrubio.stakeacumulatorservice.repository;

import com.sergiomartinrubio.stakeacumulatorservice.repository.entity.PlayerStakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStakeRepository extends JpaRepository<PlayerStakeEntity, Long> {
}
