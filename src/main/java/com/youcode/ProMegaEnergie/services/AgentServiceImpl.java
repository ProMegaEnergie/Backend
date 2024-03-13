package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.models.Dtos.AgentDto.AgentDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieResponseDto;
import com.youcode.ProMegaEnergie.models.Dtos.UserDto.UserDto;
import com.youcode.ProMegaEnergie.models.Entities.Agent;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import com.youcode.ProMegaEnergie.repositories.*;
import com.youcode.ProMegaEnergie.services.interfaces.AgentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentServiceImpl implements AgentService {
    private AgentRepository agentRepository;
    private AdminRepository adminRepository;
    private ClientRepository clientRepository;
    private SocieteRepository societeRepository;
    private BatteryRepository batteryRepository;
    private UserServiceImpl userService;
    private final ModelMapper modelMapper;

    public AgentServiceImpl(AgentRepository agentRepository, ModelMapper modelMapper, UserServiceImpl userService, AdminRepository adminRepository, ClientRepository clientRepository, SocieteRepository societeRepository, BatteryRepository batteryRepository) {
        this.agentRepository = agentRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.adminRepository = adminRepository;
        this.clientRepository = clientRepository;
        this.societeRepository = societeRepository;
        this.batteryRepository = batteryRepository;
    }

    @Override
    public Boolean signUp(AgentDto agentDto) {
        if (agentRepository.existsByEmail(agentDto.getEmail()) || adminRepository.existsByEmail(agentDto.getEmail()) || clientRepository.existsByEmail(agentDto.getEmail()) || societeRepository.existsByEmail(agentDto.getEmail())){
            return false;
        }
        Agent agent = modelMapper.map(agentDto, Agent.class);
        agent.setRoleUser(RoleUser.Agent);
        agent.setIsVerifie(false);
        agentRepository.save(agent);
        UserDto userDto = new UserDto();
        userDto.setEmail(agent.getEmail());
        userDto.setRoleUser(agent.getRoleUser());
        userService.sendCodeVerifyAccount(userDto,"activate your account");
        return true;
    }
}
