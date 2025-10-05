package com.upc.g1tf.security.repositories;

import com.upc.g1tf.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    //modificacion
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_roles (user_id, role_id) VALUES (:user_id, :role_id)", nativeQuery = true)
    void insertUserRole(@Param("user_id") Integer userId, @Param("role_id") Integer roleId);
}
