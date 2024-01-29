package com.alten.shop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alten.shop.entity.User;

/**
 * Repository interface for managing User entities.
 * @Autor Dénez Fauchon
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

}
