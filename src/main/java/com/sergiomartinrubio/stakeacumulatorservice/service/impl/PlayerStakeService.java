package com.sergiomartinrubio.stakeacumulatorservice.service.impl;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.repository.PlayerStakeRepository;
import com.sergiomartinrubio.stakeacumulatorservice.repository.entity.PlayerStakeEntity;
import com.sergiomartinrubio.stakeacumulatorservice.service.utils.PlayerStakeTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerStakeService {

    private final PlayerStakeTransformer playerStakeTransformer;
    private final PlayerStakeRepository playerStakeRepository;
    private final PlayerStakeVerificationService playerStakeVerificationService;

    public void process(PlayerStake playerStake) {
        PlayerStakeEntity playerStakeEntity = playerStakeTransformer.transformPlayerStake(playerStake);
        playerStakeRepository.save(playerStakeEntity);
        playerStakeVerificationService.evaluate(playerStake.getAccountId());
    }
}
