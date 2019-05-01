package com.jz.nebula.exception;

public class ProductStockException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 4350668488100066043L;

    public ProductStockException() {
    }

    public ProductStockException(String message) {
        super(message);
    }
}
