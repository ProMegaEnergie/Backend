package com.youcode.ProMegaEnergie.controllers;

import com.youcode.ProMegaEnergie.models.Dtos.AgentDto.AgentDto;
import com.youcode.ProMegaEnergie.services.interfaces.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("api/Agent")
public class AgentController {
    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }
    @PostMapping("/signUp")
    public Boolean signUp(@RequestBody AgentDto agentDto){
        return agentService.signUp(agentDto);
    }
}
