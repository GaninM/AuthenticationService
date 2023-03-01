package com.example.authenticationservice.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "user_password")
    private String userPassword;

    @Transient
    private String userPasswordConfirm;

    @ManyToMany
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public User(String fullName, String username, String userPassword, String userPasswordConfirm) {
        this.fullName = fullName;
        this.username = username;
        this.userPassword = userPassword;
        this.userPasswordConfirm = userPasswordConfirm;
    }
}
