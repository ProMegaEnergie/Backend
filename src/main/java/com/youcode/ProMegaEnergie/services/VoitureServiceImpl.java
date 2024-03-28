package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.models.Dtos.VoitureDto.VoitureDto;
import com.youcode.ProMegaEnergie.models.Entities.AchatBatterie;
import com.youcode.ProMegaEnergie.models.Entities.AchatVoiture;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Entities.Voiture;
import com.youcode.ProMegaEnergie.models.Enums.AchatStatus;
import com.youcode.ProMegaEnergie.models.Enums.StatusBattery;
import com.youcode.ProMegaEnergie.repositories.AchatBatterieRepository;
import com.youcode.ProMegaEnergie.repositories.AchatVoitureRepository;
import com.youcode.ProMegaEnergie.repositories.VoitureRepository;
import com.youcode.ProMegaEnergie.services.interfaces.VoitureService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoitureServiceImpl implements VoitureService {
    private final ModelMapper modelMapper;
    private final VoitureRepository voitureRepository;
    private final AchatBatterieRepository achatBatterieRepository;
    private final AchatVoitureRepository achatVoitureRepository;

    public VoitureServiceImpl(ModelMapper modelMapper, VoitureRepository voitureRepository, AchatBatterieRepository achatBatterieRepository, AchatVoitureRepository achatVoitureRepository) {
        this.modelMapper = modelMapper;
        this.voitureRepository = voitureRepository;
        this.achatBatterieRepository = achatBatterieRepository;
        this.achatVoitureRepository = achatVoitureRepository;
    }

    @Override
    public List<Voiture> getAllVoiture() {
        return voitureRepository.findAll();
    }

    @Override
    public List<Voiture> getAllVoiture(Long idSociete) {
        return voitureRepository.findAllBySociete_Id(idSociete);
    }

    @Override
    public Optional<Voiture> getVoiture(Long idVoiture) {
        if (voitureRepository.existsById(idVoiture)){
            return voitureRepository.findById(idVoiture);
        }
        return Optional.empty();
    }

    @Override
    public Boolean createVoiture(VoitureDto voitureDto) {
        Voiture voiture = modelMapper.map(voitureDto, Voiture.class);
        Long idSociete = voitureDto.getSociete().getId();
        Long idBatterie = voitureDto.getBatterie().getId();
        if (achatBatterieRepository.existsBySocieteIdAndBatterieId(idSociete, idBatterie)){
            AchatBatterie achatBatterie = achatBatterieRepository.findBySocieteIdAndBatterieId(idSociete, idBatterie);
            StatusBattery statusBattery = achatBatterie.getStatusBattery();
            if (statusBattery == StatusBattery.Inactive){
                achatBatterie.setStatusBattery(StatusBattery.Active);
                achatBatterieRepository.save(achatBatterie);
                voiture.setAchatStatus(AchatStatus.NotPayed);
                voitureRepository.save(voiture);
                return true;
            }
        }
        return  false;
    }

    @Override
    public Boolean updateVoiture(VoitureDto voitureDto) {
        Voiture voiture = modelMapper.map(voitureDto, Voiture.class);
        if (voitureRepository.existsById(voiture.getId()) && achatBatterieRepository.existsBySocieteIdAndBatterieId(voitureDto.getSociete().getId(), voitureDto.getBatterie().getId())){
            Voiture oldVoiture = voitureRepository.findById(voiture.getId()).get();
            voiture.setAchatStatus(oldVoiture.getAchatStatus());
            voitureRepository.save(voiture);
            return true;
        }
        return false;
    }

    @Override
    public List<Voiture> readAllVoitureByAchatStatus(AchatStatus achatStatus) {
        return voitureRepository.findAllByAchatStatus(achatStatus);
    }

    @Override
    public List<Voiture> getVoituresByCherecher(String column, String cherecher) {
        switch (column){
            case "prix" -> {
                return voitureRepository.findAllByPrixAndAchatStatus(Float.parseFloat(cherecher), AchatStatus.NotPayed);
            }
            case "matrecule" -> {
                return voitureRepository.findAllByMatreculeContainingIgnoreCaseAndAchatStatus(cherecher, AchatStatus.NotPayed);
            }
            case "prix_matrecule" -> {
                String[] cherecherArray = cherecher.split(",");
                float prix = Float.parseFloat(cherecherArray[0]);
                String matrecule = cherecherArray[1];
                return voitureRepository.findAllByPrixAndMatreculeContainingIgnoreCaseAndAchatStatus(prix, matrecule, AchatStatus.NotPayed);
            }
        }
        return null;
    }

    @Override
    public Boolean deleteVoiture(Long idVoiture) {
        if (voitureRepository.existsById(idVoiture)){
            voitureRepository.deleteById(idVoiture);
            return true;
        }
        return false;
    }

    @Override
    public Boolean acheterVoiture(AchatVoiture achatVoiture) {
        if (voitureRepository.existsById(achatVoiture.getVoiture().getId())){
            Voiture voiture = voitureRepository.findById(achatVoiture.getVoiture().getId()).get();
            voiture.setAchatStatus(AchatStatus.Payed);
            voitureRepository.save(voiture);
            achatVoitureRepository.save(achatVoiture);
            return true;
        }
        return false;
    }
}
