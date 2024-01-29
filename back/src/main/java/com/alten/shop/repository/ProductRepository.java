package com.alten.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alten.shop.entity.Product;

/**
 * Repository interface for managing Product entities.
 * @Autor Dénez Fauchon
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
}
