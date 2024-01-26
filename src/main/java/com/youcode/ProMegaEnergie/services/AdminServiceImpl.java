package com.youcode.ProMegaEnergie.services;

import com.youcode.ProMegaEnergie.models.Entities.Admin;
import com.youcode.ProMegaEnergie.models.Entities.Agent;
import com.youcode.ProMegaEnergie.models.Entities.Client;
import com.youcode.ProMegaEnergie.models.Entities.Societe;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import com.youcode.ProMegaEnergie.repositories.AdminRepository;
import com.youcode.ProMegaEnergie.repositories.AgentRepository;
import com.youcode.ProMegaEnergie.repositories.ClientRepository;
import com.youcode.ProMegaEnergie.repositories.SocieteRepository;
import com.youcode.ProMegaEnergie.services.interfaces.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final AgentRepository agentRepository;
    private final ClientRepository clientRepository;
    private final SocieteRepository societeRepository;
    private final ModelMapper modelMapper;
    public AdminServiceImpl(AdminRepository adminRepository,ModelMapper modelMapper,AgentRepository agentRepository,ClientRepository clientRepository,SocieteRepository societeRepository) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.agentRepository = agentRepository;
        this.clientRepository = clientRepository;
        this.societeRepository = societeRepository;
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
        if (adminRepository.existsByEmail(email) || agentRepository.existsByEmail(email) || clientRepository.existsByEmail(email) || societeRepository.existsByEmail(email)) {

            if (roleUser == RoleUser.Admin) {
                Admin admin = getAdminInUpdate(id, email, password);
                return adminRepository.save(admin);
            } else if (roleUser == RoleUser.Agent) {
                Agent agent = getAgentInUpdate(objectStringArray, id, email, password);
                return agentRepository.save(agent);
            } else if (roleUser == RoleUser.Client) {
                Client client = getClientInUpdate(objectStringArray, id, email, password);
                client.setIsVerifie(true);
                return clientRepository.save(client);
            } else if (roleUser == RoleUser.Societe) {
                Societe societe = getSocieteInUpdate(objectStringArray, id, email, password);
                societe.setIsVerifie(true);
                return societeRepository.save(societe);
            } else {
                return null;
            }
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
}
