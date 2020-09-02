package com.sergiomartinrubio.stakeaccumulatorservice.controller;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeAlertDto;
import com.sergiomartinrubio.stakeaccumulatorservice.service.PlayerStakeAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alert")
public class PlayerStakeAlertController {

    private final PlayerStakeAlertService playerStakeAlertService;

    @GetMapping("/{accountId}")
    public Set<PlayerStakeAlertDto> processPlayerStake(@PathVariable("accountId") Long accountId) {
        return playerStakeAlertService.findAllByAccountId(accountId);
    }

}
