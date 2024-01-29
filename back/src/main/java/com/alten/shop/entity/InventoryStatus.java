package com.alten.shop.entity;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/*
 * Enumerated type for {@link Product} inventory status
 * @Author Dénez Fauchon
 */
public enum InventoryStatus {
    IN_STOCK("INSTOCK"),
    LOW_STOCK("LOWSTOCK"),
    OUT_OF_STOCK("OUTOFSTOCK");

    private String value;

    /*
     * Constructor
     * @param value
     * @Return InventoryStatus
     */
    InventoryStatus(String value) {
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
     * @Return Optional<InventoryStatus>
     */
    @JsonCreator
    public static Optional<InventoryStatus> get(String string){
        for (InventoryStatus inventoryStatus : InventoryStatus.values()) {
			if (inventoryStatus.name().equals(string) || inventoryStatus.getValue().equals(string)) {
				return Optional.of(inventoryStatus);
			}
        }
        return Optional.empty();
    }
}