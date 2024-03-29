package com.youcode.ProMegaEnergie.controllers;

import com.youcode.ProMegaEnergie.models.Dtos.VoitureDto.VoitureDto;
import com.youcode.ProMegaEnergie.models.Entities.AchatVoiture;
import com.youcode.ProMegaEnergie.models.Entities.Voiture;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import com.youcode.ProMegaEnergie.services.interfaces.VoitureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("api/Voiture")
public class VoitureController {
    private final VoitureService voitureService;

    public VoitureController(VoitureService voitureService) {
        this.voitureService = voitureService;
    }

    @GetMapping("/readVoiture")
    public List<Voiture> readAllVoiture(){
        return voitureService.getAllVoiture();
    }
    @GetMapping("/readVoitures/{AchatStatus}")
    public List<Voiture> readAllVoitureByAchatStatus(@PathVariable AchatStatus AchatStatus){
        return voitureService.readAllVoitureByAchatStatus(AchatStatus);
    }
    @GetMapping("/cherecherVoitures/{column}/{cherecher}")
    public List<Voiture> getVoituresByCherecher(@PathVariable String column ,@PathVariable String cherecher){
        return voitureService.getVoituresByCherecher(column, cherecher);
    }
    @GetMapping("/readVoitureByIdSociete/{idSociete}")
    public List<Voiture> readAllVoiture(@PathVariable Long idSociete){
        return voitureService.getAllVoiture(idSociete);
    }
    @GetMapping("/readVoitureByIdVoiture/{idVoiture}")
    public Optional<Voiture> readVoiture(@PathVariable Long idVoiture){
        return voitureService.getVoiture(idVoiture);
    }
    @PostMapping("/createVoiture")
    public Boolean createVoiture(@RequestBody VoitureDto voitureDto){
        return voitureService.createVoiture(voitureDto);
    }
    @PutMapping("/updateVoiture")
    public Boolean updateVoiture(@RequestBody VoitureDto voitureDto){
        return voitureService.updateVoiture(voitureDto);
    }
    @DeleteMapping("/deleteVoiture/{idVoiture}")
    public Boolean deleteVoiture(@PathVariable Long idVoiture){
        return voitureService.deleteVoiture(idVoiture);
    }
    @PostMapping("/acheterVoiture")
    public Boolean acheterVoiture(@RequestBody AchatVoiture achatVoiture){
        return voitureService.acheterVoiture(achatVoiture);
    }
}
