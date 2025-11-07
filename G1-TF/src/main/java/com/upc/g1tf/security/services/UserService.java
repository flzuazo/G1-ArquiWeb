package com.upc.g1tf.security.services;

import com.upc.g1tf.security.entities.Role;
import com.upc.g1tf.security.entities.User;
import com.upc.g1tf.security.repositories.RoleRepository;
import com.upc.g1tf.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void grabar(Role role) {
        roleRepository.save(role);
    }
    public Integer insertUserRol(Integer user_id, Integer rol_id) {
        Integer result = 0;
        userRepository.insertUserRole(user_id, rol_id);
        return 1;
    }

}
