package com.youcode.CRMEF;

import com.youcode.CRMEF.models.Entities.Admin;
import com.youcode.CRMEF.models.Enums.RoleUser;
import com.youcode.CRMEF.repositories.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CRMEF {
    public static void main(String[] args) {
        SpringApplication.run(CRMEF.class, args);
    }
    @Bean
    CommandLineRunner start(AdminRepository adminRepository){
        return args -> {
            Admin admin = new Admin();
            admin.setId(1L);
            admin.setEmail("uanemaro216@gmail.com");
            admin.setPassword("$2a$10$evkiO.g1GLODwm7D19K5uOFz96BAqsJHpfU77Rk7Be8K1KccqtxB6");
            admin.setRoleUser(RoleUser.Admin);
            adminRepository.save(admin);
        };
    }
}