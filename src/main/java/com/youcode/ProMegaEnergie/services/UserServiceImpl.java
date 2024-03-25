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

    public RoleUser sendCodeForgetPassword(String email) {

        if (adminRepository.existsByEmail(email)) {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setRoleUser(RoleUser.Admin);
            this.sendCodeVerifyAccount(userDto,"reset your password");
            return RoleUser.Admin;
        } else if (clientRepository.existsByEmail(email)) {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setRoleUser(RoleUser.Client);
            this.sendCodeVerifyAccount(userDto,"reset your password");
            return RoleUser.Client;
        } else if (agentRepository.existsByEmail(email)) {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setRoleUser(RoleUser.Agent);
            this.sendCodeVerifyAccount(userDto,"reset your password");
            return RoleUser.Agent;
        } else if (societeRepository.existsByEmail(email)) {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setRoleUser(RoleUser.Societe);
            this.sendCodeVerifyAccount(userDto,"reset your password");
            return RoleUser.Societe;
        }
        return null;
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

    @Override
    public Boolean signUp(Object userObject) {
        String userObjectString = userObject.toString();
        String[] userObjectArray = userObjectString.split("roleUser=");
        RoleUser roleUser = RoleUser.valueOf(userObjectArray[1].substring(0, userObjectArray[1].length() - 1));

        if (roleUser == RoleUser.Admin) {
            Admin admin = modelMapper.map(userObject, Admin.class);
            adminRepository.save(admin);
            UserDto userDto = new UserDto();
            userDto.setEmail(admin.getEmail());
            userDto.setRoleUser(admin.getRoleUser());
            this.sendCodeVerifyAccount(userDto,"activate your account");
            return true;
        } else if (roleUser == RoleUser.Client) {
            Client client = modelMapper.map(userObject, Client.class);
            clientRepository.save(client);
            UserDto userDto = new UserDto();
            userDto.setEmail(client.getEmail());
            userDto.setRoleUser(client.getRoleUser());
            this.sendCodeVerifyAccount(userDto,"activate your account");
            return true;
        } else if (roleUser == RoleUser.Agent) {
            Agent agent = modelMapper.map(userObject, Agent.class);
            agentRepository.save(agent);
            UserDto userDto = new UserDto();
            userDto.setEmail(agent.getEmail());
            userDto.setRoleUser(agent.getRoleUser());
            this.sendCodeVerifyAccount(userDto,"activate your account");
            return true;
        } else if (roleUser == RoleUser.Societe) {
            Societe societe = modelMapper.map(userObject, Societe.class);
            societeRepository.save(societe);
            UserDto userDto = new UserDto();
            userDto.setEmail(societe.getEmail());
            userDto.setRoleUser(societe.getRoleUser());
            this.sendCodeVerifyAccount(userDto,"activate your account");
            return true;
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

    @Override
    public List getAll(RoleUser roleUser) {
        if (roleUser == RoleUser.Admin){
            return adminRepository.findAll();
        } else if (roleUser == RoleUser.Agent){
            return agentRepository.findAll();
        } else if (roleUser == RoleUser.Client){
            return clientRepository.findAll();
        } else if (roleUser == RoleUser.Societe){
            return societeRepository.findAll();
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(RoleUser roleUser, Long id) {
        if (roleUser == RoleUser.Admin){
            adminRepository.deleteById(id);
        } else if (roleUser == RoleUser.Agent){
            agentRepository.deleteById(id);
        } else if (roleUser == RoleUser.Client){
            clientRepository.deleteById(id);
        } else if (roleUser == RoleUser.Societe){
            societeRepository.deleteById(id);
        }
    }

    @Override
    public Object getById(RoleUser roleUser, Long id) {
        if (roleUser == RoleUser.Admin){
            return adminRepository.findById(id);
        } else if (roleUser == RoleUser.Agent){
            return agentRepository.findById(id);
        } else if (roleUser == RoleUser.Client){
            return clientRepository.findById(id);
        } else if (roleUser == RoleUser.Societe){
            return societeRepository.findById(id);
        } else {
            return null;
        }
    }

    @Override
    public Object create(RoleUser roleUser, Object object) {
        String objectString = object.toString();
        String[] objectStringArray = objectString.split(", ");
        String objectStringEmail = objectStringArray[0];
        String objectStringPassword = objectStringArray[1];
        String email = objectStringEmail.split("email=")[1];
        String password = objectStringPassword.split("password=")[1];
        if (adminRepository.existsByEmail(email) || agentRepository.existsByEmail(email) || clientRepository.existsByEmail(email) || societeRepository.existsByEmail(email)){
            return null;
        }
        if (roleUser == RoleUser.Admin){
            Admin admin = getAdminInCreate(email, password);
            return adminRepository.save(admin);
        } else if (roleUser == RoleUser.Agent){
            Agent agent = getAgentInCreate(objectStringArray, email, password);
            return agentRepository.save(agent);
        } else if (roleUser == RoleUser.Client){
            Client client = getClientInCreate(objectStringArray, email, password);
            client.setIsVerifie(true);
            return clientRepository.save(client);
        } else if (roleUser == RoleUser.Societe){
            Societe societe = getSocieteInCreate(objectStringArray, email, password);
            societe.setIsVerifie(true);
            return societeRepository.save(societe);
        } else {
            return null;
        }
    }

    @Override
    public Object update(RoleUser roleUser, Object object) {
        String objectString = object.toString();
        String[] objectStringArray = objectString.split(", ");
        String objectStringId = objectStringArray[0];
        String objectStringEmail = objectStringArray[1];
        String objectStringPassword = objectStringArray[2];
        Long id = Long.parseLong(objectStringId.split("id=")[1]);
        String email = objectStringEmail.split("email=")[1];
        String password = objectStringPassword.split("password=")[1];
        if (roleUser == RoleUser.Admin) {
            Admin admin = getAdminInUpdate(id, email, password);
            return adminRepository.save(admin);
        } else if (roleUser == RoleUser.Agent) {
            Agent agent = getAgentInUpdate(objectStringArray, id, email, password);
            agent.setRoleUser(RoleUser.Agent);
            agent.setIsVerifie(true);
            return agentRepository.save(agent);
        } else if (roleUser == RoleUser.Client) {
            Client client = getClientInUpdate(objectStringArray, id, email, password);
            client.setRoleUser(RoleUser.Client);
            client.setIsVerifie(true);
            return clientRepository.save(client);
        } else if (roleUser == RoleUser.Societe) {
            Societe societe = getSocieteInUpdate(objectStringArray, id, email, password);
            societe.setRoleUser(RoleUser.Societe);
            societe.setIsVerifie(true);
            return societeRepository.save(societe);
        } else {
            return null;
        }
    }

    private Societe getSocieteInCreate(String[] objectStringArray, String email, String password) {
        String objectStringImage = objectStringArray[2];
        String objectStringNom = objectStringArray[3];
        String image = objectStringImage.split("image=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Societe societe = new Societe();
        societe.setEmail(email);
        societe.setPassword(password);
        societe.setNom(nom);
        societe.setImage(image);
        societe.setRoleUser(RoleUser.Societe);
        societe.setIsVerifie(true);
        return societe;
    }

    private Client getClientInCreate(String[] objectStringArray, String email, String password) {
        String objectStringPrenom = objectStringArray[2];
        String objectStringNom = objectStringArray[3];
        String prenom = objectStringPrenom.split("prenom=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Client client = new Client();
        client.setEmail(email);
        client.setPassword(password);
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setRoleUser(RoleUser.Client);
        client.setIsVerifie(true);
        return client;
    }

    private Admin getAdminInCreate(String email, String password) {
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRoleUser(RoleUser.Admin);
        return admin;
    }

    private static Agent getAgentInCreate(String[] objectStringArray, String email, String password) {
        String objectStringPrenom = objectStringArray[2];
        String objectStringNom = objectStringArray[3];
        String prenom = objectStringPrenom.split("prenom=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Agent agent = new Agent();
        agent.setEmail(email);
        agent.setPassword(password);
        agent.setNom(nom);
        agent.setPrenom(prenom);
        agent.setRoleUser(RoleUser.Agent);
        agent.setIsVerifie(true);
        return agent;
    }
    private Societe getSocieteInUpdate(String[] objectStringArray,Long id, String email, String password) {
        String objectStringImage = objectStringArray[3];
        String objectStringNom = objectStringArray[4];
        String image = objectStringImage.split("image=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Societe societe = new Societe();
        societe.setId(id);
        societe.setEmail(email);
        societe.setPassword(password);
        societe.setNom(nom);
        societe.setImage(image);
        societe.setRoleUser(RoleUser.Societe);
        societe.setIsVerifie(true);
        return societe;
    }

    private Client getClientInUpdate(String[] objectStringArray,Long id, String email, String password) {
        String objectStringPrenom = objectStringArray[3];
        String objectStringNom = objectStringArray[4];
        String prenom = objectStringPrenom.split("prenom=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Client client = new Client();
        client.setId(id);
        client.setEmail(email);
        client.setPassword(password);
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setRoleUser(RoleUser.Client);
        client.setIsVerifie(true);
        return client;
    }

    private Admin getAdminInUpdate(Long id, String email, String password) {
        Admin admin = new Admin();
        admin.setId(id);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRoleUser(RoleUser.Admin);
        return admin;
    }

    private static Agent getAgentInUpdate(String[] objectStringArray,Long id, String email, String password) {
        String objectStringPrenom = objectStringArray[3];
        String objectStringNom = objectStringArray[4];
        String prenom = objectStringPrenom.split("prenom=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Agent agent = new Agent();
        agent.setId(id);
        agent.setEmail(email);
        agent.setPassword(password);
        agent.setNom(nom);
        agent.setPrenom(prenom);
        agent.setRoleUser(RoleUser.Agent);
        agent.setIsVerifie(true);
        return agent;
    }

    public long generateCode() {
        return ThreadLocalRandom.current().nextLong(100000, 999999);
    }
}
