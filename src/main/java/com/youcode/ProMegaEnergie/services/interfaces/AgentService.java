package com.youcode.ProMegaEnergie.services.interfaces;

import com.youcode.ProMegaEnergie.models.Dtos.AgentDto.AgentDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieDto;
import com.youcode.ProMegaEnergie.models.Dtos.BatterieDto.BatterieResponseDto;
import com.youcode.ProMegaEnergie.models.Entities.Batterie;

import java.util.List;
import java.util.Optional;

public interface AgentService {
    Boolean signUp(AgentDto agentDto);
}
