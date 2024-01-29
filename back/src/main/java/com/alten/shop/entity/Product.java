package com.alten.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Entity class for Product
 * @Author Dénez Fauchon
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String code;
    private String name;
    private String description;
    private Integer price;
    private Integer quantity;
    @Column
    @Enumerated
    private InventoryStatus inventoryStatus;
    @Column
    @Enumerated
    private Category category;
    private String image;
    private Integer rating;
    
    // Constructor
    public Product(String code, String name, String description, Integer price, Integer quantity, InventoryStatus inventoryStatus, Category category, String image, Integer rating) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.inventoryStatus = inventoryStatus;
        this.category = category;
        this.image = image;
        this.rating = rating;
    }
}