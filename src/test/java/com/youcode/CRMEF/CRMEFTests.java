package com.youcode.CRMEF;

import com.youcode.CRMEF.models.Dtos.FormateurDto.FormateurDto;
import com.youcode.CRMEF.models.Dtos.ChefFiliereDto.ChefFiliereDto;
import com.youcode.CRMEF.models.Dtos.UserDto.UserDto;
import com.youcode.CRMEF.models.Entities.Admin;
import com.youcode.CRMEF.models.Entities.Formateur;
import com.youcode.CRMEF.repositories.AdminRepository;
import com.youcode.CRMEF.repositories.EnseignantRepository;
import com.youcode.CRMEF.repositories.ChefFiliereRepository;
import com.youcode.CRMEF.repositories.FormateurRepository;
import com.youcode.CRMEF.services.interfaces.UserService;
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
class CRMEFTests {
    @Autowired
    private UserService userService; // Assuming userService is the class containing the Login method

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private FormateurRepository formateurRepository;

    @MockBean
    private EnseignantRepository enseignantRepository;

    @MockBean
    private ChefFiliereRepository chefFiliereRepository;

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
        Optional<ChefFiliereDto> loginResult = userService.Login(new UserDto(email, password));

        // Assertions
        assertTrue(loginResult.isPresent());
        // You can further assert the content of the returned ChefFiliereDto object if necessary
    }

    @Test
    public void testLogin_validClientCredentials() {
        String email = "formateur@gmail.com";
        String password = "Marouane216@";

        // Mock clientRepository behavior (similar to adminRepository)
        Formateur formateur = new Formateur();
        formateur.setEmail(email);
        Mockito.when(formateurRepository.findByEmail(email)).thenReturn(Optional.of(formateur));
        Mockito.when(passwordEncoder.matches(password, formateur.getPassword())).thenReturn(true);

        // Call the login method
        Optional<FormateurDto> loginResult = userService.Login(new UserDto(email, password));

        // Assertions (similar to admin test)
        assertTrue(loginResult.isPresent());
        // Assert content of FormateurDto if needed
    }

    // Similar test cases for Enseignant and ChefFiliere with valid credentials

    @Test
    public void testLogin_invalidEmail() {
        String email = "nuanemaro216zzzz@gmail.com";
        String password = "Marouane216@";

        // Mock behavior to return null for all repositories
        Mockito.when(adminRepository.findByEmail(email)).thenReturn(null);
        Mockito.when(formateurRepository.findByEmail(email)).thenReturn(null);
        Mockito.when(enseignantRepository.findByEmail(email)).thenReturn(null);
        Mockito.when(chefFiliereRepository.findByEmail(email)).thenReturn(null);

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
