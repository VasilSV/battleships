package com.softuni.battleships.controllers;

import com.softuni.battleships.models.dtos.StartBattleDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class BattleController {
    @PostMapping("/battle")
    public String battle(@Valid StartBattleDTO startBattleDTO){
        System.out.println(startBattleDTO.getAttackerId() +"+"+ startBattleDTO.getDefenderId());

        return "redirect: /home";

    }

}
