package com.jz.nebula.exception;

public class SkuOutOfStockException extends Exception {

    public SkuOutOfStockException() {
    }

    public SkuOutOfStockException(String message) {
        super(message);
    }
}
