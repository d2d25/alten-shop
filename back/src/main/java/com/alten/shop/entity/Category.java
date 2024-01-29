package com.alten.shop.entity;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/*
 * Enumerated type for {@link Product} category
 * @Author Dénez Fauchon
 */
public enum Category {
    ACCESSORIES("Accessories"),
    CLOTHING("Clothing"),
    ELECTRONICS("Electronics"),
    FITNESS("Fitness");

    private String value;

    /*
     * Constructor
     * @param value
     * @Return Category
     */
    Category(String value) {
        this.value = value;
    }

    /*
     * Getter for value
     * @Return String
     */
    @JsonValue
    public String getValue() {
        return value;
    }
    
    /*
     * Getter Category by name or value
     * @param string
     * @Return Optional<Category>
     */
    @JsonCreator
    public static Optional<Category> get(String string){
		for (Category category : Category.values()) {
			if (category.name().equals(string) || category.getValue().equals(string)) {
				return Optional.of(category);
			}
		}
		return Optional.empty();
    }
}