package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertDto;
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

    public Set<PlayerStakeAlertDto> findAllByAccountId(Long accountId) {
        Set<PlayerStakeAlert> playerStakeAlerts = playerStakeAlertRepository.findAllByAccountIdEquals(accountId);
        return playerStakeAlerts.stream()
                .map(this::transformPlayerStakeAlert)
                .collect(Collectors.toSet());
    }

    private PlayerStakeAlertDto transformPlayerStakeAlert(PlayerStakeAlert playerStakeAlert) {
        return PlayerStakeAlertDto.builder()
                .id(playerStakeAlert.getId())
                .accountId(playerStakeAlert.getAccountId())
                .cumulatedAmount(playerStakeAlert.getCumulatedAmount())
                .creationDateTime(playerStakeAlert.getCreationDateTime())
                .build();
    }
}
