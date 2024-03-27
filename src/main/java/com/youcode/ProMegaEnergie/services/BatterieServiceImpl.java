package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.models.Dtos.AchatBatterieDto.AchatBatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieResponseDto;
import com.youcode.ProMegaEnergie.models.Entities.AchatBatterie;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Entities.Societe;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import com.youcode.ProMegaEnergie.models.Enums.StatusBattery;
import com.youcode.ProMegaEnergie.repositories.AchatBatterieRepository;
import com.youcode.ProMegaEnergie.repositories.BatteryRepository;
import com.youcode.ProMegaEnergie.repositories.SocieteRepository;
import com.youcode.ProMegaEnergie.services.interfaces.BatterieService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatterieServiceImpl implements BatterieService {
    private final BatteryRepository batteryRepository;
    private final SocieteRepository societeRepository;
    private final AchatBatterieRepository achatBatterieRepository;
    private final ModelMapper modelMapper;

    public BatterieServiceImpl(BatteryRepository batteryRepository, SocieteRepository societeRepository, AchatBatterieRepository achatBatterieRepository, ModelMapper modelMapper) {
        this.batteryRepository = batteryRepository;
        this.societeRepository = societeRepository;
        this.achatBatterieRepository = achatBatterieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Batterie> getBatteries() {
        return batteryRepository.findAll();
    }

    @Override
    public Boolean acheterBatterie(AchatBatterieDto achatBatterieDto) {
        Long idSociete = achatBatterieDto.getSociete().getId();
        Optional<Societe> societe = societeRepository.findById(idSociete);

        Long idBatterie = achatBatterieDto.getBatterie().getId();
        Optional<Batterie> batterie = batteryRepository.findById(idBatterie);
        if (batterie.isEmpty() || societe.isEmpty() || batterie.get().getAchatStatus() == AchatStatus.Payed){
            return false;
        } else {
            batterie.get().setAchatStatus(AchatStatus.Payed);
            batteryRepository.save(batterie.get());
            AchatBatterie achatBatterie = modelMapper.map(achatBatterieDto, AchatBatterie.class);
            achatBatterie.setStatusBattery(StatusBattery.Inactive);
            achatBatterieRepository.save(achatBatterie);
            return true;
        }
    }

    @Override
    public List<AchatBatterie> getBatteriesAchetes(Long idSociete) {
        return achatBatterieRepository.findAllBySocieteId(idSociete);
    }


    @Override
    public BatterieResponseDto CreateBatterie(BatterieDto batterieDto) {
        Batterie batterie = modelMapper.map(batterieDto, Batterie.class);
        batterie.setAchatStatus(AchatStatus.NotPayed);
        batterie = batteryRepository.save(batterie);
        return modelMapper.map(batterie, BatterieResponseDto.class);
    }

    @Override
    public List<Batterie> ReadBatterie(Long idAgent) {
        return batteryRepository.findAllByAgent_Id(idAgent);
    }

    @Override
    public Optional<Batterie> ReadBatterieById(Long id,Long idAgent) {
        return batteryRepository.findByIdAndAgent_Id(id,idAgent);
    }

    @Override
    public BatterieResponseDto UpdateBatterie(BatterieDto batterieDto) {
        return batterieDto.getId() != null ? CreateBatterie(batterieDto) : null;
    }

    @Override
    public Boolean DeleteBatterie(Long id) {
        if (batteryRepository.existsById(id)){
            batteryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Batterie> getBatteriesByAchatStatus(String achatStatus) {
        return batteryRepository.findAllByAchatStatus(AchatStatus.valueOf(achatStatus));
    }

    @Override
    public List<Batterie> getBatteriesByCherecher(String column, String cherecher) {
        cherecher = cherecher.replace('¤',' ');
        switch (column) {
            case "prix" -> {
                return batteryRepository.findByPrixAndAchatStatus(Float.parseFloat(cherecher), AchatStatus.NotPayed);
            }
            case "nom" -> {
                return batteryRepository.findByNomContainingIgnoreCaseAndAchatStatus(cherecher, AchatStatus.NotPayed);
            }
            case "prix_nom" -> {
                String[] cherecherArray = cherecher.split(",");
                float prix = Float.parseFloat(cherecherArray[0]);
                String nom = cherecherArray[1];
                return batteryRepository.findByPrixAndNomContainingIgnoreCaseAndAchatStatus(prix, nom, AchatStatus.NotPayed);
            }
        }
        return null;
    }
}
