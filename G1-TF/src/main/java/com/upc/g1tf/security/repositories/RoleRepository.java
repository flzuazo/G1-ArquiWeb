
package com.upc.g1tf.security.repositories;

import com.upc.g1tf.security.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}
