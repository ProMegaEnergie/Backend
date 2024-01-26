package com.youcode.ProMegaEnergie.controllers;

import com.youcode.ProMegaEnergie.exceptions.ApiRequestException;
import com.youcode.ProMegaEnergie.models.Dtos.UserDto.UserDto;
import com.youcode.ProMegaEnergie.models.Dtos.ValidationDto.ValidationDto;
import com.youcode.ProMegaEnergie.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("api/User")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("login")
    public Optional Login(@RequestBody UserDto userDto) {
        if(Optional.ofNullable(userService.Login(userDto)).isEmpty())
            throw new ApiRequestException("User not found");
        return userService.Login(userDto);
    }
    @PutMapping("activateAccount")
    public Boolean activateAccount(@RequestBody ValidationDto validationDto) {
        if(Optional.ofNullable(userService.activateAccount(validationDto)).isEmpty())
            throw new ApiRequestException("Code not found");
        return userService.activateAccount(validationDto);
    }
    @PostMapping("forgetPassword")
    public Boolean forgetPassword(@RequestBody UserDto userDto) {
        if(Optional.ofNullable(userService.sendCodeForgetPassword(userDto)).isEmpty())
            throw new ApiRequestException("User not found");
        return userService.sendCodeForgetPassword(userDto);
    }
    @PutMapping("forgetPassword/{newPassword}")
    public Boolean forgetPassword(@RequestBody ValidationDto validationDto , @PathVariable String newPassword) {
        if(Optional.ofNullable(userService.updatePassword(validationDto,newPassword)).isEmpty())
            throw new ApiRequestException("Code not found");
        return userService.updatePassword(validationDto,newPassword);
    }

}
