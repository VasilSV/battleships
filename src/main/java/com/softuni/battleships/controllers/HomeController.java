package com.softuni.battleships.controllers;

import com.softuni.battleships.models.dtos.ShipDTO;
import com.softuni.battleships.models.dtos.StartBattleDTO;
import com.softuni.battleships.services.AuthService;
import com.softuni.battleships.services.ShipService;
import com.softuni.battleships.session.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class HomeController {

    private final ShipService shipService;
    private final LoggedUser loggedUser;
    private final AuthService authService;

    @ModelAttribute("startBattleDTO")
    public StartBattleDTO initBattleForm(){
        return new StartBattleDTO();
    }

    @Autowired
    public HomeController(ShipService shipService, LoggedUser loggedUser, AuthService authService) {
        this.shipService = shipService;
        this.loggedUser = loggedUser;
        this.authService = authService;
    }

    @GetMapping("/")
    public String loggedOutIndex() {
        if (this.authService.isLoggedIn()){
            return "redirect:/home";
        }
        return "redirect:/index";
    }

    @GetMapping("/home")
    public String loggedInIndex (Model model) {
        long loggedUserId = this.loggedUser.getId();
        if (loggedUserId==0){
            return "redirect:/";
        }

        List<ShipDTO> ownShips = this.shipService.getShipsOwnedById(loggedUserId);
        List<ShipDTO> enemyShips = this.shipService.getShipsNotOwnedById(loggedUserId);
        List<ShipDTO> sortedShips = this.shipService.getAllSorted();

        model.addAttribute("ownShips", ownShips);
        model.addAttribute("enemyShips", enemyShips);
        model.addAttribute("sortedShips", sortedShips);

        return "redirect:/home";
    }
}