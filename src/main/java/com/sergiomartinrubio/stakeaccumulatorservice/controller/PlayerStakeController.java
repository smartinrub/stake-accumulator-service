package com.sergiomartinrubio.stakeaccumulatorservice.controller;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeaccumulatorservice.service.PlayerStakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player-stake")
public class PlayerStakeController {

    private final PlayerStakeService playerStakeService;

    @ResponseStatus(CREATED)
    @PostMapping
    public void processPlayerStake(@RequestBody @Valid PlayerStake playerStake) {
        playerStakeService.process(playerStake);
    }
}
