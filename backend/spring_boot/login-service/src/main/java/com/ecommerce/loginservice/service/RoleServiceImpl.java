package com.ecommerce.loginservice.service;

import com.ecommerce.loginservice.model.Role;
import com.ecommerce.loginservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role createNewRole(Role role) throws IOException {
        return roleRepository.save(role);
    }
}
