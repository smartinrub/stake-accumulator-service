package com.sergiomartinrubio.stakeacumulatorservice.service.utils.impl;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.repository.entity.PlayerStakeEntity;

public interface PlayerStakeTransformer {
    PlayerStakeEntity transformPlayerStake(PlayerStake playerStake);
}
