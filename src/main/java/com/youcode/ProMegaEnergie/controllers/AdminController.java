package com.youcode.ProMegaEnergie.controllers;

import com.youcode.ProMegaEnergie.models.Enums.RoleUser;
import com.youcode.ProMegaEnergie.services.interfaces.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@CrossOrigin("*")
@RequestMapping("api/Admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/all/{RoleUser}")
    public List getAll(@PathVariable RoleUser RoleUser){
        return adminService.getAll(RoleUser);
    }
    @GetMapping("/user/{RoleUser}/{id}")
    public Object getById(@PathVariable RoleUser RoleUser,@PathVariable Long id){
        return adminService.getById(RoleUser,id);
    }
    @DeleteMapping("/delete/{RoleUser}/{id}")
    public void deleteById(@PathVariable RoleUser RoleUser,@PathVariable Long id){
        adminService.deleteById(RoleUser,id);
    }
    @PostMapping("/create/{RoleUser}")
    public Object create(@PathVariable RoleUser RoleUser, @RequestBody Object object){
        return adminService.create(RoleUser,object);
    }
    @PutMapping("/update/{RoleUser}")
    public Object update(@PathVariable RoleUser RoleUser, @RequestBody Object object){
        return adminService.update(RoleUser,object);
    }
}
