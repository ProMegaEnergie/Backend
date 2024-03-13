package com.youcode.ProMegaEnergie.controllers;

import com.youcode.ProMegaEnergie.models.Dtos.AchatBatterieDto.AchatBatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.SocieteDto.SocieteDto;
import com.youcode.ProMegaEnergie.models.Dtos.VoitureDto.VoitureDto;
import com.youcode.ProMegaEnergie.models.Entities.AchatBatterie;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Entities.Voiture;
import com.youcode.ProMegaEnergie.services.interfaces.BatterieService;
import com.youcode.ProMegaEnergie.services.interfaces.SocieteService;
import com.youcode.ProMegaEnergie.services.interfaces.VoitureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("api/Societe")
public class SocieteController {
    private final SocieteService societeService;

    public SocieteController(SocieteService societeService) {
        this.societeService = societeService;
    }

    //Societe
    @PostMapping("/signUp")
    public Boolean signIp(@RequestBody SocieteDto societeDto){
        return societeService.signUp(societeDto);
    }
}
