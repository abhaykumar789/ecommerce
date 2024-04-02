package com.ecommerce.loginservice.controller;

import com.ecommerce.loginservice.model.Role;
import com.ecommerce.loginservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/check")
    public String check(){
        return "Role Working..!";
    }

    @PostMapping("/registerNewRole")
    public ResponseEntity<Role> createNewRole(@RequestBody Role role) throws IOException {
        try {
            return new ResponseEntity<Role>(roleService.createNewRole(role), HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity("IO exception", HttpStatus.BAD_REQUEST);
        }
    }
}
