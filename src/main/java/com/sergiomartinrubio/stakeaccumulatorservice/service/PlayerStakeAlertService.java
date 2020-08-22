package com.sergiomartinrubio.stakeaccumulatorservice.service;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlert;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PlayerStakeAlertService {

    public Set<PlayerStakeAlert> findAllByAccountId(Long accountId) {
        return null;
    }
}
