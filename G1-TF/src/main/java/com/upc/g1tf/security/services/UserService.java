/*
package com.upc.g1tf.security.services;

import com.upc.g1tf.security.entities.Role;
import com.upc.g1tf.security.entities.Usuario;
import com.upc.g1tf.security.repositories.RoleRepository;
import com.upc.g1tf.security.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void save(Usuario user) {
        userRepository.save(user);
    }

    @Transactional
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Transactional
    public void insertUserRole(Integer userId, Integer roleId) {
        userRepository.insertUserRole(userId, roleId);
    }
}*/