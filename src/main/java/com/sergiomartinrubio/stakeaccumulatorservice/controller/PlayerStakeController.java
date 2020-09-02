package com.sergiomartinrubio.stakeaccumulatorservice.controller;

import com.sergiomartinrubio.stakeaccumulatorservice.model.PlayerStakeDto;
import com.sergiomartinrubio.stakeaccumulatorservice.service.PlayerStakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player-stake")
public class PlayerStakeController {

    private final PlayerStakeService playerStakeProcessorService;

    @ResponseStatus(CREATED)
    @PostMapping
    public void processPlayerStake(@RequestBody @Valid PlayerStakeDto playerStake) {
        playerStakeProcessorService.process(playerStake);
    }
}
