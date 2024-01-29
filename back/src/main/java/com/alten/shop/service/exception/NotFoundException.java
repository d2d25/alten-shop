package com.alten.shop.service.exception;

/**
 * Exception class for not found entity
 * @Autor Dénez Fauchon
 */
public class NotFoundException extends Exception{
    public NotFoundException(String format) {
        super(format);
    }
}