package com.youcode.ProMegaEnergie.controllers;

import com.youcode.ProMegaEnergie.models.Dtos.AchatBatterieDto.AchatBatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieResponseDto;
import com.youcode.ProMegaEnergie.models.Entities.AchatBatterie;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import com.youcode.ProMegaEnergie.services.interfaces.BatterieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("api/Batterie")
public class BatterieController {
    private final BatterieService batterieService;

    public BatterieController(BatterieService batterieService) {
        this.batterieService = batterieService;
    }
    @GetMapping("/ReadBatteries")
    public List<Batterie> getBatteries(){
        return batterieService.getBatteries();
    }
    @GetMapping("/ReadBatteries/{AchatStatus}")
    public List<Batterie> getBatteriesByAchatStatus(@PathVariable String AchatStatus){
        return batterieService.getBatteriesByAchatStatus(AchatStatus);
    }
    @GetMapping("/cherecherBatteries/{column}/{cherecher}")
    public List<Batterie> getBatteriesByCherecher(@PathVariable String column ,@PathVariable String cherecher){
        return batterieService.getBatteriesByCherecher(column,cherecher);
    }
    @GetMapping("/ReadBattery/{idAgent}")
    public List<Batterie> ReadBattery(@PathVariable Long idAgent){
        return batterieService.ReadBatterie(idAgent);
    }
    @GetMapping("/ReadBattery/{id}/{idAgent}")
    public Optional<Batterie> ReadBattery(@PathVariable Long id, @PathVariable Long idAgent){
        return batterieService.ReadBatterieById(id,idAgent);
    }
    @PostMapping("/CreateBattery")
    public BatterieResponseDto CreateBattery(@RequestBody BatterieDto batterieDto){
        return batterieService.CreateBatterie(batterieDto);
    }
    @PutMapping("/UpdateBattery")
    public BatterieResponseDto UpdateBattery(@RequestBody BatterieDto batterieDto){
        return batterieService.UpdateBatterie(batterieDto);
    }
    @DeleteMapping("/DeleteBattery/{id}")
    public Boolean DeleteBattery(@PathVariable Long id){
        return batterieService.DeleteBatterie(id);
    }
    @PostMapping("/AcheterBatterie")
    public Boolean acheterBatterie(@RequestBody AchatBatterieDto achatBatterieDto){
        return batterieService.acheterBatterie(achatBatterieDto);
    }
    @GetMapping("/getBatteriesAchetes/{idSociete}")
    public List<AchatBatterie> getBatteriesAchetes(@PathVariable Long idSociete){
        return batterieService.getBatteriesAchetes(idSociete);
    }
}
