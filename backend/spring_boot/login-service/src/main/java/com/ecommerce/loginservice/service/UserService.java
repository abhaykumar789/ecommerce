package com.ecommerce.loginservice.service;

import com.ecommerce.loginservice.exceptions.UserAlreadyExistException;
import com.ecommerce.loginservice.exceptions.UserNotFoundException;
import com.ecommerce.loginservice.model.User;

public interface UserService {
    User getUserByUserEmail(String userEmail) throws UserNotFoundException;

    User registerNewUser(User user) throws UserAlreadyExistException;

    void initRoleAndUser();
}
