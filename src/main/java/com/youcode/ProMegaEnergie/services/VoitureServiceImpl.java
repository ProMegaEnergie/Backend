package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.models.Dtos.VoitureDto.VoitureDto;
import com.youcode.ProMegaEnergie.models.Entities.Voiture;
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

    public VoitureServiceImpl(ModelMapper modelMapper, VoitureRepository voitureRepository) {
        this.modelMapper = modelMapper;
        this.voitureRepository = voitureRepository;
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
        return null;
    }

    @Override
    public Boolean createVoiture(VoitureDto voitureDto) {
        Voiture voiture = modelMapper.map(voitureDto, Voiture.class);
        voitureRepository.save(voiture);
        return true;
    }

    @Override
    public Boolean updateVoiture(VoitureDto voitureDto) {
        Voiture voiture = modelMapper.map(voitureDto, Voiture.class);
        if (voitureRepository.existsById(voiture.getId())){
            voitureRepository.save(voiture);
            return true;
        }
        return false;
    }
}
