package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.models.Dtos.SocieteDto.SocieteDto;
import com.youcode.ProMegaEnergie.models.Dtos.UserDto.UserDto;
import com.youcode.ProMegaEnergie.models.Entities.Societe;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import com.youcode.ProMegaEnergie.repositories.*;
import com.youcode.ProMegaEnergie.services.interfaces.SocieteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class SocieteServiceImpl implements SocieteService {
    private final AgentRepository agentRepository;
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final SocieteRepository societeRepository;
    private final UserServiceImpl userService;
    private final ModelMapper modelMapper;

    public SocieteServiceImpl(AgentRepository agentRepository, AdminRepository adminRepository, ClientRepository clientRepository, SocieteRepository societeRepository,UserServiceImpl userService, ModelMapper modelMapper) {
        this.agentRepository = agentRepository;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.societeRepository = societeRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean signUp(SocieteDto societeDto) {
        if (agentRepository.existsByEmail(societeDto.getEmail()) || adminRepository.existsByEmail(societeDto.getEmail()) || clientRepository.existsByEmail(societeDto.getEmail()) || societeRepository.existsByEmail(societeDto.getEmail())){
            return false;
        }
        Societe societe = modelMapper.map(societeDto, Societe.class);
        societe.setIsVerifie(false);
        societe.setRoleUser(RoleUser.Societe);
        societeRepository.save(societe);
        UserDto userDto = new UserDto();
        userDto.setEmail(societe.getEmail());
        userDto.setRoleUser(societe.getRoleUser());
        userService.sendCodeVerifyAccount(userDto,"activate your account");
        return true;
    }
}
