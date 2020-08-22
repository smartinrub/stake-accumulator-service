package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeaccumulatorservice.repository.entity.PlayerStakeEntity;
import com.sergiomartinrubio.stakeaccumulatorservice.service.utils.PlayerStakeTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerStakeService {

    private final PlayerStakeTransformer playerStakeTransformer;
    private final PlayerStakeRepository playerStakeRepository;
    private final PlayerStakeNotificationService playerStakeNotificationService;

    public void process(PlayerStake playerStake) {
        PlayerStakeEntity playerStakeEntity = playerStakeTransformer.transformPlayerStake(playerStake);
        playerStakeRepository.save(playerStakeEntity);
        playerStakeNotificationService.evaluate(playerStake.getAccountId());
    }
}
