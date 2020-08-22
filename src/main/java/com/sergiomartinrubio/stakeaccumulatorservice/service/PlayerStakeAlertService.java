package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlert;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeAlertRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeAlertEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerStakeAlertService {

    private final PlayerStakeAlertRepository playerStakeAlertRepository;

    public Set<PlayerStakeAlert> findAllByAccountId(Long accountId) {
        Set<PlayerStakeAlertEntity> playerStakeAlerts = playerStakeAlertRepository.findAllByAccountIdEquals(accountId);
        return playerStakeAlerts.stream()
                .map(this::transformPlayerStakeAlert)
                .collect(Collectors.toSet());
    }

    private PlayerStakeAlert transformPlayerStakeAlert(PlayerStakeAlertEntity playerStakeAlert) {
        return PlayerStakeAlert.builder()
                .id(playerStakeAlert.getId())
                .accountId(playerStakeAlert.getAccountId())
                .cumulatedAmount(playerStakeAlert.getCumulatedAmount())
                .creationDateTime(playerStakeAlert.getCreationDateTime())
                .build();
    }
}
