package com.sergiomartinrubio.stakeacumulatorservice.service;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;

public interface PlayerStakeService {
    void process(PlayerStake playerStake);
}
