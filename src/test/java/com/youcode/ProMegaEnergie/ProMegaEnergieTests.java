package com.youcode.ProMegaEnergie;

import com.youcode.ProMegaEnergie.models.Dtos.ClientDto.ClientDto;
import com.youcode.ProMegaEnergie.models.Dtos.SocieteDto.SocieteDto;
import com.youcode.ProMegaEnergie.models.Dtos.UserDto.UserDto;
import com.youcode.ProMegaEnergie.models.Entities.Admin;
import com.youcode.ProMegaEnergie.models.Entities.Client;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import com.youcode.ProMegaEnergie.repositories.AdminRepository;
import com.youcode.ProMegaEnergie.repositories.AgentRepository;
import com.youcode.ProMegaEnergie.repositories.ClientRepository;
import com.youcode.ProMegaEnergie.repositories.SocieteRepository;
import com.youcode.ProMegaEnergie.services.interfaces.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ProMegaEnergieTests {
    @Autowired
    private UserService userService; // Assuming userService is the class containing the Login method

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private AgentRepository agentRepository;

    @MockBean
    private SocieteRepository societeRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void testLogin_validAdminCredentials() {
        String email = "uanemaro216@gmail.com";
        String password = "Marouane216@";

        // Mock adminRepository behavior
        Admin admin = new Admin(); // Create a dummy Admin object
        admin.setEmail(email);
        Mockito.when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));
        Mockito.when(passwordEncoder.matches(password, admin.getPassword())).thenReturn(true);

        // Call the login method
        Optional<SocieteDto> loginResult = userService.Login(new UserDto(email, password));

        // Assertions
        assertTrue(loginResult.isPresent());
        // You can further assert the content of the returned SocieteDto object if necessary
    }

    @Test
    public void testLogin_validClientCredentials() {
        String email = "client@gmail.com";
        String password = "Marouane216@";

        // Mock clientRepository behavior (similar to adminRepository)
        Client client = new Client();
        client.setEmail(email);
        Mockito.when(clientRepository.findByEmail(email)).thenReturn(Optional.of(client));
        Mockito.when(passwordEncoder.matches(password, client.getPassword())).thenReturn(true);

        // Call the login method
        Optional<ClientDto> loginResult = userService.Login(new UserDto(email, password));

        // Assertions (similar to admin test)
        assertTrue(loginResult.isPresent());
        // Assert content of ClientDto if needed
    }

    // Similar test cases for Agent and Societe with valid credentials

    @Test
    public void testLogin_invalidEmail() {
        String email = "nuanemaro216zzzz@gmail.com";
        String password = "Marouane216@";

        // Mock behavior to return null for all repositories
        Mockito.when(adminRepository.findByEmail(email)).thenReturn(null);
        Mockito.when(clientRepository.findByEmail(email)).thenReturn(null);
        Mockito.when(agentRepository.findByEmail(email)).thenReturn(null);
        Mockito.when(societeRepository.findByEmail(email)).thenReturn(null);

        // Call the login method
        Optional<Object> loginResult = userService.Login(new UserDto(email, password));

        // Assertion
        assertFalse(loginResult.isPresent());
    }

    @Test
    public void testLogin_invalidPassword() {
        String email = "uanemaro216@gmail.com";
        String password = "Marouane216@zzzz";

        // Mock adminRepository behavior (similar to valid case, but password doesn't match)
        Admin admin = new Admin();
        admin.setEmail(email);
        Mockito.when(adminRepository.findByEmail(email)).thenReturn(Optional.of(admin));
        Mockito.when(passwordEncoder.matches(password, admin.getPassword())).thenReturn(false);

        // Call the login method
        Optional<Object> loginResult = userService.Login(new UserDto(email, password));

        // Assertion
        assertFalse(loginResult.isPresent());
    }

}
