package com.ecommerce.loginservice.service;

import com.ecommerce.loginservice.exceptions.UserAlreadyExistException;
import com.ecommerce.loginservice.exceptions.UserNotFoundException;
import com.ecommerce.loginservice.model.Role;
import com.ecommerce.loginservice.model.User;
import com.ecommerce.loginservice.repository.RoleRepository;
import com.ecommerce.loginservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User getUserByUserEmail(String userEmail) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userEmail);

        if (optionalUser.isPresent()){
            return optionalUser.get();
        }else {
            throw new UserNotFoundException();
        }
    }

//    @Override
//    public User registerNewUser(User user) throws UserAlreadyExistException {
//        Optional<User> optionalUser = userRepository.findById(user.getUserEmail());
//
//        if (optionalUser.isPresent()){
//            throw new UserAlreadyExistException();
//        }else {
//            Role role = roleRepository.findById("User").get();
//            Set<Role> userRoles = new HashSet<>();
//            userRoles.add(role);
//            user.setRole(userRoles);
//
//            return userRepository.save(user);
//        }
//    }

    @Override
    public User registerNewUser(User user) throws UserAlreadyExistException {
        Optional<User> optionalUser = userRepository.findById(user.getUserEmail());

        if (optionalUser.isPresent()){
            throw new UserAlreadyExistException();
        } else {
            // Retrieve roleName from the Role object in the JSON request
            String roleName = null;
            if (!user.getRole().isEmpty()) {
                roleName = user.getRole().iterator().next().getRoleName(); // Assuming there's only one role
            }

            // Find the corresponding Role object from the repository based on roleName
            Role role = roleRepository.findByRoleName(roleName).orElse(null);
            if (role == null) {
                throw new IllegalArgumentException("Role not found with roleName: " + roleName);
            }

            // Set the retrieved Role object for the User
            Set<Role> userRoles = new HashSet<>();
            userRoles.add(role);
            user.setRole(userRoles);

            // Save the User to the repository
            return userRepository.save(user);
        }
    }


    @Override
    public void initRoleAndUser() {
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for the new user");
        roleRepository.save(userRole);

        Role sellerRole = new Role();
        sellerRole.setRoleName("Seller");
        sellerRole.setRoleDescription("Seller role");
        roleRepository.save(sellerRole);

    }
}
