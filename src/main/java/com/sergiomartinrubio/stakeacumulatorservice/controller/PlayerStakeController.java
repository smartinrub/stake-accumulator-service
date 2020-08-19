package com.sergiomartinrubio.stakeacumulatorservice.controller;

import com.sergiomartinrubio.stakeacumulatorservice.model.PlayerStake;
import com.sergiomartinrubio.stakeacumulatorservice.service.PlayerStakeService;
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
    public void savePlayerStake(@RequestBody @Valid PlayerStake playerStake) {
        playerStakeService.save(playerStake);

    }
}
