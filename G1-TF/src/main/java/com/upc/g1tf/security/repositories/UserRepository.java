/*
package com.upc.g1tf.security.repositories;

import com.upc.g1tf.security.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
    void insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
}*/