package com.youcode.CRMEF.services;

import com.youcode.CRMEF.models.Dtos.AdminDto.AdminDto;
import com.youcode.CRMEF.models.Dtos.ChefFiliereDto.ChefFiliereDto;
import com.youcode.CRMEF.models.Dtos.EnseignantDto.EnseignantsDto;
import com.youcode.CRMEF.models.Dtos.FormateurDto.FormateurDto;
import com.youcode.CRMEF.models.Dtos.UserDto.UserDto;
import com.youcode.CRMEF.models.Dtos.ValidationDto.ValidationDto;
import com.youcode.CRMEF.models.Entities.*;
import com.youcode.CRMEF.models.Enums.RoleUser;
import com.youcode.CRMEF.repositories.*;
import com.youcode.CRMEF.services.interfaces.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final AdminRepository adminRepository;
    private final FormateurRepository formateurRepository;
    private final EnseignantRepository enseignantRepository;
    private final EmailService emailService;
    private final ChefFiliereRepository chefFiliereRepository;
    private final ValidationRepository validationRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(AdminRepository adminRepository, ValidationRepository validationRepository, EmailService emailService, ChefFiliereRepository chefFiliereRepository, EnseignantRepository enseignantRepository, ModelMapper modelMapper, FormateurRepository formateurRepository) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.formateurRepository = formateurRepository;
        this.emailService = emailService;
        this.enseignantRepository = enseignantRepository;
        this.validationRepository = validationRepository;
        this.chefFiliereRepository = chefFiliereRepository;
    }

    @Override
    public Optional Login(UserDto userDto) {
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        if (!adminRepository.existsByEmail(email)){
            if (!formateurRepository.existsByEmail(email)){
                if (!enseignantRepository.existsByEmail(email)){
                    if (!chefFiliereRepository.existsByEmail(email)){
                        return Optional.empty();
                    }
                    else {
                        ChefFiliere chefFiliere = chefFiliereRepository.findByEmail(email).orElse(null);
                        if (chefFiliere == null) throw new AssertionError();
                        String societePassword = chefFiliere.getPassword();
                        if (passwordEncoder.matches(password, societePassword)) {
                            return Optional.ofNullable(modelMapper.map(chefFiliere, ChefFiliereDto.class));
                        }
                    }
                }
                else {
                    Enseignant enseignant = enseignantRepository.findByEmail(email).orElse(null);
                    if (enseignant == null) throw new AssertionError();
                    String agentPassword = enseignant.getPassword();
                    if (passwordEncoder.matches(password, agentPassword)) {
                        return Optional.ofNullable(modelMapper.map(enseignant, EnseignantsDto.class));
                    }
                }
            } else {
                Formateur formateur = formateurRepository.findByEmail(email).orElse(null);
                if (formateur == null) throw new AssertionError();
                String clientPassword = formateur.getPassword();
                if (passwordEncoder.matches(password, clientPassword)) {
                    return Optional.ofNullable(modelMapper.map(formateur, FormateurDto.class));
                }
            }
        }
        else {
            Admin admin = adminRepository.findByEmail(email).orElse(null);
            if (admin == null) throw new AssertionError();
            String adminPassword = admin.getPassword();
            if (passwordEncoder.matches(password, adminPassword)) {
                return Optional.ofNullable(modelMapper.map(admin, AdminDto.class));
            }
        }

        /*if(!adminRepository.existsByEmailAndPassword(email, password)){
            if(!clientRepository.existsByEmailAndPassword(email, password)){
                if(!agentRepository.existsByEmailAndPassword(email, password)){
                    if(!societeRepository.existsByEmailAndPassword(email, password)){
                        return Optional.empty();
                    } else {
                        ChefFiliere societe = societeRepository.findByEmailAndPassword(email, password).orElse(null);
                        return Optional.ofNullable(modelMapper.map(societe, ChefFiliereDto.class));
                    }
                } else {
                    Enseignant agent = agentRepository.findByEmailAndPassword(email, password).orElse(null);
                    return Optional.ofNullable(modelMapper.map(agent, EnseignantsDto.class));
                }
            } else {
                Formateur client = clientRepository.findByEmailAndPassword(email, password).orElse(null);
                return Optional.ofNullable(modelMapper.map(client, FormateurDto.class));
            }
        }
        else {
            Admin admin = adminRepository.findByEmailAndPassword(email, password).orElse(null);
            return Optional.ofNullable(modelMapper.map(admin, AdminDto.class));
        }*/
        return Optional.empty();
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
                Formateur formateur = formateurRepository.findByEmail(email).orElse(null);
                if (formateur == null) throw new AssertionError();
                formateur.setIsVerifie(true);
                formateurRepository.save(formateur);
                return true;
            } else if (roleUser == RoleUser.Agent) {
                Enseignant enseignant = enseignantRepository.findByEmail(email).orElse(null);
                if (enseignant == null) throw new AssertionError();
                enseignant.setIsVerifie(true);
                enseignantRepository.save(enseignant);
                return true;
            } else if (roleUser == RoleUser.Societe) {
                ChefFiliere chefFiliere = chefFiliereRepository.findByEmail(email).orElse(null);
                if (chefFiliere == null) throw new AssertionError();
                chefFiliere.setIsVerifie(true);
                chefFiliereRepository.save(chefFiliere);
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
        } else if (formateurRepository.existsByEmail(email)) {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setRoleUser(RoleUser.Client);
            this.sendCodeVerifyAccount(userDto,"reset your password");
            return RoleUser.Client;
        } else if (enseignantRepository.existsByEmail(email)) {
            UserDto userDto = new UserDto();
            userDto.setEmail(email);
            userDto.setRoleUser(RoleUser.Agent);
            this.sendCodeVerifyAccount(userDto,"reset your password");
            return RoleUser.Agent;
        } else if (chefFiliereRepository.existsByEmail(email)) {
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
        newPassword = passwordEncoder.encode(newPassword);
        if (validationRepository.existsByEmailAndCodeAndRoleUser(validationDto.getEmail(), validationDto.getCode(), validationDto.getRoleUser())) {
            if (validationDto.getRoleUser() == RoleUser.Admin) {
                Admin admin = adminRepository.findByEmail(validationDto.getEmail()).orElse(null);
                if (admin == null) throw new AssertionError();
                admin.setPassword(newPassword);
                adminRepository.save(admin);
                return true;
            } else if (validationDto.getRoleUser() == RoleUser.Client) {
                Formateur formateur = formateurRepository.findByEmail(validationDto.getEmail()).orElse(null);
                if (formateur == null) throw new AssertionError();
                formateur.setPassword(newPassword);
                formateurRepository.save(formateur);
                return true;
            } else if (validationDto.getRoleUser() == RoleUser.Agent) {
                Enseignant enseignant = enseignantRepository.findByEmail(validationDto.getEmail()).orElse(null);
                if (enseignant == null) throw new AssertionError();
                enseignant.setPassword(newPassword);
                enseignantRepository.save(enseignant);
                return true;
            } else if (validationDto.getRoleUser() == RoleUser.Societe) {
                ChefFiliere chefFiliere = chefFiliereRepository.findByEmail(validationDto.getEmail()).orElse(null);
                if (chefFiliere == null) throw new AssertionError();
                chefFiliere.setPassword(newPassword);
                chefFiliereRepository.save(chefFiliere);
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

            String encodedPassword = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(encodedPassword);

            adminRepository.save(admin);
            UserDto userDto = new UserDto();
            userDto.setEmail(admin.getEmail());
            userDto.setRoleUser(admin.getRoleUser());
            this.sendCodeVerifyAccount(userDto,"activate your account");
            return true;
        } else if (roleUser == RoleUser.Client) {
            Formateur formateur = modelMapper.map(userObject, Formateur.class);

            String encodedPassword = passwordEncoder.encode(formateur.getPassword());
            formateur.setPassword(encodedPassword);

            formateurRepository.save(formateur);
            UserDto userDto = new UserDto();
            userDto.setEmail(formateur.getEmail());
            userDto.setRoleUser(formateur.getRoleUser());
            this.sendCodeVerifyAccount(userDto,"activate your account");
            return true;
        } else if (roleUser == RoleUser.Agent) {
            Enseignant enseignant = modelMapper.map(userObject, Enseignant.class);

            String encodedPassword = passwordEncoder.encode(enseignant.getPassword());
            enseignant.setPassword(encodedPassword);

            enseignantRepository.save(enseignant);
            UserDto userDto = new UserDto();
            userDto.setEmail(enseignant.getEmail());
            userDto.setRoleUser(enseignant.getRoleUser());
            this.sendCodeVerifyAccount(userDto,"activate your account");
            return true;
        } else if (roleUser == RoleUser.Societe) {
            ChefFiliere chefFiliere = modelMapper.map(userObject, ChefFiliere.class);

            String encodedPassword = passwordEncoder.encode(chefFiliere.getPassword());
            chefFiliere.setPassword(encodedPassword);

            chefFiliereRepository.save(chefFiliere);
            UserDto userDto = new UserDto();
            userDto.setEmail(chefFiliere.getEmail());
            userDto.setRoleUser(chefFiliere.getRoleUser());
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
            return enseignantRepository.findAll();
        } else if (roleUser == RoleUser.Client){
            return formateurRepository.findAll();
        } else if (roleUser == RoleUser.Societe){
            return chefFiliereRepository.findAll();
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(RoleUser roleUser, Long id) {
        if (roleUser == RoleUser.Admin){
            adminRepository.deleteById(id);
        } else if (roleUser == RoleUser.Agent){
            enseignantRepository.deleteById(id);
        } else if (roleUser == RoleUser.Client){
            formateurRepository.deleteById(id);
        } else if (roleUser == RoleUser.Societe){
            chefFiliereRepository.deleteById(id);
        }
    }

    @Override
    public Object getById(RoleUser roleUser, Long id) {
        if (roleUser == RoleUser.Admin){
            return adminRepository.findById(id);
        } else if (roleUser == RoleUser.Agent){
            return enseignantRepository.findById(id);
        } else if (roleUser == RoleUser.Client){
            return formateurRepository.findById(id);
        } else if (roleUser == RoleUser.Societe){
            return chefFiliereRepository.findById(id);
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

        password = passwordEncoder.encode(password);

        if (adminRepository.existsByEmail(email) || enseignantRepository.existsByEmail(email) || formateurRepository.existsByEmail(email) || chefFiliereRepository.existsByEmail(email)){
            return null;
        }
        if (roleUser == RoleUser.Admin){
            Admin admin = getAdminInCreate(email, password);
            return adminRepository.save(admin);
        } else if (roleUser == RoleUser.Agent){
            Enseignant enseignant = getAgentInCreate(objectStringArray, email, password);
            return enseignantRepository.save(enseignant);
        } else if (roleUser == RoleUser.Client){
            Formateur formateur = getClientInCreate(objectStringArray, email, password);
            formateur.setIsVerifie(true);
            return formateurRepository.save(formateur);
        } else if (roleUser == RoleUser.Societe){
            ChefFiliere chefFiliere = getSocieteInCreate(objectStringArray, email, password);
            chefFiliere.setIsVerifie(true);
            return chefFiliereRepository.save(chefFiliere);
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
            Enseignant enseignant = getAgentInUpdate(objectStringArray, id, email, password);
            enseignant.setRoleUser(RoleUser.Agent);
            enseignant.setIsVerifie(true);
            return enseignantRepository.save(enseignant);
        } else if (roleUser == RoleUser.Client) {
            Formateur formateur = getClientInUpdate(objectStringArray, id, email, password);
            formateur.setRoleUser(RoleUser.Client);
            formateur.setIsVerifie(true);
            return formateurRepository.save(formateur);
        } else if (roleUser == RoleUser.Societe) {
            ChefFiliere chefFiliere = getSocieteInUpdate(objectStringArray, id, email, password);
            chefFiliere.setRoleUser(RoleUser.Societe);
            chefFiliere.setIsVerifie(true);
            return chefFiliereRepository.save(chefFiliere);
        } else {
            return null;
        }
    }

    private ChefFiliere getSocieteInCreate(String[] objectStringArray, String email, String password) {
        String objectStringImage = objectStringArray[2];
        String objectStringNom = objectStringArray[3];
        String image = objectStringImage.split("image=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        ChefFiliere chefFiliere = new ChefFiliere();
        chefFiliere.setEmail(email);
        chefFiliere.setPassword(password);
        chefFiliere.setNom(nom);
        chefFiliere.setImage(image);
        chefFiliere.setRoleUser(RoleUser.Societe);
        chefFiliere.setIsVerifie(true);
        return chefFiliere;
    }

    private Formateur getClientInCreate(String[] objectStringArray, String email, String password) {
        String objectStringPrenom = objectStringArray[2];
        String objectStringNom = objectStringArray[3];
        String prenom = objectStringPrenom.split("prenom=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Formateur formateur = new Formateur();
        formateur.setEmail(email);
        formateur.setPassword(password);
        formateur.setNom(nom);
        formateur.setPrenom(prenom);
        formateur.setRoleUser(RoleUser.Client);
        formateur.setIsVerifie(true);
        return formateur;
    }

    private Admin getAdminInCreate(String email, String password) {
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRoleUser(RoleUser.Admin);
        return admin;
    }

    private static Enseignant getAgentInCreate(String[] objectStringArray, String email, String password) {
        String objectStringPrenom = objectStringArray[2];
        String objectStringNom = objectStringArray[3];
        String prenom = objectStringPrenom.split("prenom=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Enseignant enseignant = new Enseignant();
        enseignant.setEmail(email);
        enseignant.setPassword(password);
        enseignant.setNom(nom);
        enseignant.setPrenom(prenom);
        enseignant.setRoleUser(RoleUser.Agent);
        enseignant.setIsVerifie(true);
        return enseignant;
    }
    private ChefFiliere getSocieteInUpdate(String[] objectStringArray, Long id, String email, String password) {
        String objectStringImage = objectStringArray[3];
        String objectStringNom = objectStringArray[4];
        String image = objectStringImage.split("image=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        ChefFiliere chefFiliere = new ChefFiliere();
        chefFiliere.setId(id);
        chefFiliere.setEmail(email);
        chefFiliere.setPassword(password);
        chefFiliere.setNom(nom);
        chefFiliere.setImage(image);
        chefFiliere.setRoleUser(RoleUser.Societe);
        chefFiliere.setIsVerifie(true);
        return chefFiliere;
    }

    private Formateur getClientInUpdate(String[] objectStringArray, Long id, String email, String password) {
        String objectStringPrenom = objectStringArray[3];
        String objectStringNom = objectStringArray[4];
        String prenom = objectStringPrenom.split("prenom=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Formateur formateur = new Formateur();
        formateur.setId(id);
        formateur.setEmail(email);
        formateur.setPassword(password);
        formateur.setNom(nom);
        formateur.setPrenom(prenom);
        formateur.setRoleUser(RoleUser.Client);
        formateur.setIsVerifie(true);
        return formateur;
    }

    private Admin getAdminInUpdate(Long id, String email, String password) {
        Admin admin = new Admin();
        admin.setId(id);
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRoleUser(RoleUser.Admin);
        return admin;
    }

    private static Enseignant getAgentInUpdate(String[] objectStringArray, Long id, String email, String password) {
        String objectStringPrenom = objectStringArray[3];
        String objectStringNom = objectStringArray[4];
        String prenom = objectStringPrenom.split("prenom=")[1];
        String nom = objectStringNom.split("nom=")[1];
        nom = nom.substring(0, nom.length() - 1);
        Enseignant enseignant = new Enseignant();
        enseignant.setId(id);
        enseignant.setEmail(email);
        enseignant.setPassword(password);
        enseignant.setNom(nom);
        enseignant.setPrenom(prenom);
        enseignant.setRoleUser(RoleUser.Agent);
        enseignant.setIsVerifie(true);
        return enseignant;
    }

    public long generateCode() {
        return ThreadLocalRandom.current().nextLong(100000, 999999);
    }
}
