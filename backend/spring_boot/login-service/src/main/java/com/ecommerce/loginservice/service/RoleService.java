package com.ecommerce.loginservice.service;

import com.ecommerce.loginservice.model.Role;

import java.io.IOException;

public interface RoleService {
    Role createNewRole(Role role) throws IOException;
}
