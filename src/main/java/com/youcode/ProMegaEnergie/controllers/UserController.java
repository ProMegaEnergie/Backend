package com.youcode.ProMegaEnergie.controllers;

import com.youcode.ProMegaEnergie.exceptions.ApiRequestException;
import com.youcode.ProMegaEnergie.models.Dtos.UserDto.UserDto;
import com.youcode.ProMegaEnergie.models.Dtos.ValidationDto.ValidationDto;
import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
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
    @PostMapping("signUp")
    public Boolean signUp(@RequestBody Object userObject) {
        return userService.signUp(userObject);
    }
    @PutMapping("activateAccount")
    public Boolean deactivateAccount(@RequestBody ValidationDto validationDto) {
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


    @GetMapping("/all/{RoleUser}")
    public List getAll(@PathVariable RoleUser RoleUser){
        return userService.getAll(RoleUser);
    }
    @GetMapping("/user/{RoleUser}/{id}")
    public Object getById(@PathVariable RoleUser RoleUser,@PathVariable Long id){
        return userService.getById(RoleUser,id);
    }
    @DeleteMapping("/delete/{RoleUser}/{id}")
    public void deleteById(@PathVariable RoleUser RoleUser,@PathVariable Long id){
        userService.deleteById(RoleUser,id);
    }
    @PostMapping("/create/{RoleUser}")
    public Object create(@PathVariable RoleUser RoleUser, @RequestBody Object object){
        return userService.create(RoleUser,object);
    }
    @PutMapping("/update/{RoleUser}")
    public Object update(@PathVariable RoleUser RoleUser, @RequestBody Object object){
        return userService.update(RoleUser,object);
    }
}
