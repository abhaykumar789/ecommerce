package com.ecommerce.loginservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    private String userEmail;
    private String userFullName;
    private String userPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
               joinColumns = {@JoinColumn(name = "USER_ID")},
               inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")})
    private Set<Role> role;

}
