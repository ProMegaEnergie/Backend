package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.models.Dtos.AchatBatterieDto.AchatBatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieResponseDto;
import com.youcode.ProMegaEnergie.models.Entities.AchatBatterie;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.repositories.AchatBatterieRepository;
import com.youcode.ProMegaEnergie.repositories.BatteryRepository;
import com.youcode.ProMegaEnergie.services.interfaces.BatterieService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BatterieServiceImpl implements BatterieService {
    private final BatteryRepository batteryRepository;
    private final AchatBatterieRepository achatBatterieRepository;
    private final ModelMapper modelMapper;

    public BatterieServiceImpl(BatteryRepository batteryRepository, AchatBatterieRepository achatBatterieRepository, ModelMapper modelMapper) {
        this.batteryRepository = batteryRepository;
        this.achatBatterieRepository = achatBatterieRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Batterie> getBatteries() {
        return batteryRepository.findAll();
    }

    @Override
    public Boolean acheterBatterie(AchatBatterieDto achatBatterieDto) {
        AchatBatterie achatBatterie = modelMapper.map(achatBatterieDto, AchatBatterie.class);
        achatBatterieRepository.save(achatBatterie);
        return true;
    }

    @Override
    public List<AchatBatterie> getBatteriesAchetes(Long idSociete) {
        return achatBatterieRepository.findAllBySocieteId(idSociete);
    }


    @Override
    public BatterieResponseDto CreateBatterie(BatterieDto batterieDto) {
        Batterie batterie = modelMapper.map(batterieDto, Batterie.class);
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
}
