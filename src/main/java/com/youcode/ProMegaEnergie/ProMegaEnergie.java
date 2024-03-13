package com.youcode.ProMegaEnergie;

import com.youcode.ProMegaEnergie.models.Entities.Admin;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import com.youcode.ProMegaEnergie.repositories.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProMegaEnergie {
    public static void main(String[] args) {
        SpringApplication.run(ProMegaEnergie.class, args);
    }
    @Bean
    CommandLineRunner start(AdminRepository adminRepository){
        return args -> {
            Admin admin = new Admin();
            admin.setId(1L);
            admin.setEmail("uanemaro216@gmail.com");
            admin.setPassword("Marouane216@");
            admin.setRoleUser(RoleUser.Admin);
            adminRepository.save(admin);
        };
    }
}