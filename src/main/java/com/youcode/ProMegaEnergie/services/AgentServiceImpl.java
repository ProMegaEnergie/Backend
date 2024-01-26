package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.models.Dtos.AgentDto.AgentDto;
import com.youcode.ProMegaEnergie.models.Dtos.UserDto.UserDto;
import com.youcode.ProMegaEnergie.models.Entities.Agent;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import com.youcode.ProMegaEnergie.repositories.AgentRepository;
import com.youcode.ProMegaEnergie.services.interfaces.AgentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImpl implements AgentService {
    private AgentRepository agentRepository;
    private UserServiceImpl userService;
    private final ModelMapper modelMapper;

    public AgentServiceImpl(AgentRepository agentRepository, ModelMapper modelMapper, UserServiceImpl userService) {
        this.agentRepository = agentRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean signUp(AgentDto agentDto) {
        if (agentRepository.existsByEmail(agentDto.getEmail())) {
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
