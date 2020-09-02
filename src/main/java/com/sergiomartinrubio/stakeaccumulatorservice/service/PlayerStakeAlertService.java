package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeAlertRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeAlert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerStakeAlertService {

    private final PlayerStakeAlertRepository playerStakeAlertRepository;

    public Set<com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlert> findAllByAccountId(Long accountId) {
        Set<PlayerStakeAlert> playerStakeAlerts = playerStakeAlertRepository.findAllByAccountIdEquals(accountId);
        return playerStakeAlerts.stream()
                .map(this::transformPlayerStakeAlert)
                .collect(Collectors.toSet());
    }

    private com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlert transformPlayerStakeAlert(PlayerStakeAlert playerStakeAlert) {
        return com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlert.builder()
                .id(playerStakeAlert.getId())
                .accountId(playerStakeAlert.getAccountId())
                .cumulatedAmount(playerStakeAlert.getCumulatedAmount())
                .creationDateTime(playerStakeAlert.getCreationDateTime())
                .build();
    }
}
