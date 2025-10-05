package com.upc.g1tf.security.repositories;

import com.upc.g1tf.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
}
