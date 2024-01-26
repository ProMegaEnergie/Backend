package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.exceptions.ApiRequestException;
import com.youcode.ProMegaEnergie.models.Dtos.AdminDto.AdminDto;
import com.youcode.ProMegaEnergie.models.Dtos.AgentDto.AgentDto;
import com.youcode.ProMegaEnergie.models.Dtos.ClientDto.ClientDto;
import com.youcode.ProMegaEnergie.models.Dtos.SocieteDto.SocieteDto;
import com.youcode.ProMegaEnergie.models.Dtos.UserDto.UserDto;
import com.youcode.ProMegaEnergie.models.Dtos.ValidationDto.ValidationDto;
import com.youcode.ProMegaEnergie.models.Entities.*;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import com.youcode.ProMegaEnergie.repositories.*;
import com.youcode.ProMegaEnergie.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {
    private final AdminRepository adminRepository;
    private final ClientRepository clientRepository;
    private final AgentRepository agentRepository;
    private final EmailService emailService;
    private final SocieteRepository societeRepository;
    private final ValidationRepository validationRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(AdminRepository adminRepository,ValidationRepository validationRepository, EmailService emailService,SocieteRepository societeRepository,AgentRepository agentRepository, ModelMapper modelMapper, ClientRepository clientRepository) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.emailService = emailService;
        this.agentRepository = agentRepository;
        this.validationRepository = validationRepository;
        this.societeRepository = societeRepository;
    }

    @Override
    public Optional Login(UserDto userDto) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();
        if(!adminRepository.existsByEmailAndPassword(email, password)){
            if(!clientRepository.existsByEmailAndPassword(email, password)){
                if(!agentRepository.existsByEmailAndPassword(email, password)){
                    if(!societeRepository.existsByEmailAndPassword(email, password)){
                        return Optional.empty();
                    } else {
                        Societe societe = societeRepository.findByEmailAndPassword(email, password).orElse(null);
                        return Optional.ofNullable(modelMapper.map(societe, SocieteDto.class));
                    }
                } else {
                    Agent agent = agentRepository.findByEmailAndPassword(email, password).orElse(null);
                    return Optional.ofNullable(modelMapper.map(agent, AgentDto.class));
                }
            } else {
                Client client = clientRepository.findByEmailAndPassword(email, password).orElse(null);
                return Optional.ofNullable(modelMapper.map(client, ClientDto.class));
            }
        } else {
            Admin admin = adminRepository.findByEmailAndPassword(email, password).orElse(null);
            return Optional.ofNullable(modelMapper.map(admin, AdminDto.class));
        }
    }

    @Override
    public Boolean activateAccount(ValidationDto validationDto) {
        String email = validationDto.getEmail();
        RoleUser roleUser = validationDto.getRoleUser();
        long code = validationDto.getCode();
        if(validationRepository.existsByEmailAndCodeAndRoleUser(email, code, roleUser)){
            if (roleUser == RoleUser.Admin) {
                Admin admin = adminRepository.findByEmail(email).orElse(null);
                if (admin == null) throw new AssertionError();
                adminRepository.save(admin);
                return true;
            } else if (roleUser == RoleUser.Client) {
                Client client = clientRepository.findByEmail(email).orElse(null);
                if (client == null) throw new AssertionError();
                client.setIsVerifie(true);
                clientRepository.save(client);
                return true;
            } else if (roleUser == RoleUser.Agent) {
                Agent agent = agentRepository.findByEmail(email).orElse(null);
                if (agent == null) throw new AssertionError();
                agent.setIsVerifie(true);
                agentRepository.save(agent);
                return true;
            } else if (roleUser == RoleUser.Societe) {
                Societe societe = societeRepository.findByEmail(email).orElse(null);
                if (societe == null) throw new AssertionError();
                societe.setIsVerifie(true);
                societeRepository.save(societe);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean sendCodeForgetPassword(UserDto userDto) {
        if (userDto.getRoleUser() == RoleUser.Admin) {
            Admin admin = adminRepository.findByEmail(userDto.getEmail()).orElse(null);
            if (admin == null) {
                throw new ApiRequestException("Admin not found");
            }
            return sendCodeVerifyAccount(userDto,"reset your password");
        } else if (userDto.getRoleUser() == RoleUser.Client) {
            Client client = clientRepository.findByEmail(userDto.getEmail()).orElse(null);
            if (client == null) {
                throw new ApiRequestException("Client not found");
            }
            return sendCodeVerifyAccount(userDto,"reset your password");
        } else if (userDto.getRoleUser() == RoleUser.Agent) {
            Agent agent = agentRepository.findByEmail(userDto.getEmail()).orElse(null);
            if (agent == null) {
                throw new ApiRequestException("Agent not found");
            }
            return sendCodeVerifyAccount(userDto,"reset your password");
        } else if (userDto.getRoleUser() == RoleUser.Societe) {
            Societe societe = societeRepository.findByEmail(userDto.getEmail()).orElse(null);
            if (societe == null) {
                throw new ApiRequestException("Societe not found");
            }
            return sendCodeVerifyAccount(userDto,"reset your password");
        }
        return false;
    }

    @Override
    public Boolean updatePassword(ValidationDto validationDto, String newPassword) {
        if (validationRepository.existsByEmailAndCodeAndRoleUser(validationDto.getEmail(), validationDto.getCode(), validationDto.getRoleUser())) {
            if (validationDto.getRoleUser() == RoleUser.Admin) {
                Admin admin = adminRepository.findByEmail(validationDto.getEmail()).orElse(null);
                if (admin == null) throw new AssertionError();
                admin.setPassword(newPassword);
                adminRepository.save(admin);
                return true;
            } else if (validationDto.getRoleUser() == RoleUser.Client) {
                Client client = clientRepository.findByEmail(validationDto.getEmail()).orElse(null);
                if (client == null) throw new AssertionError();
                client.setPassword(newPassword);
                clientRepository.save(client);
                return true;
            } else if (validationDto.getRoleUser() == RoleUser.Agent) {
                Agent agent = agentRepository.findByEmail(validationDto.getEmail()).orElse(null);
                if (agent == null) throw new AssertionError();
                agent.setPassword(newPassword);
                agentRepository.save(agent);
                return true;
            } else if (validationDto.getRoleUser() == RoleUser.Societe) {
                Societe societe = societeRepository.findByEmail(validationDto.getEmail()).orElse(null);
                if (societe == null) throw new AssertionError();
                societe.setPassword(newPassword);
                societeRepository.save(societe);
                return true;
            }
        }
        return false;
    }

    public Boolean sendCodeVerifyAccount(UserDto userDto,String subject) {
        String email = userDto.getEmail();
        RoleUser roleUser = userDto.getRoleUser();
        long code = generateCode();
        emailService.sendEmail(email,
                subject,
                subject+"'s code is: " + code);
        Validation validation = new Validation();
        validation.setCode(code);
        validation.setEmail(email);
        validation.setRoleUser(roleUser);
        validationRepository.save(validation);
        return true;
    }

    public long generateCode() {
        return ThreadLocalRandom.current().nextLong(100000, 999999);
    }
}
