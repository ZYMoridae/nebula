package com.jz.nebula.component.exception;

import java.io.Serializable;

public class InvalidEntityException extends Exception implements Serializable {

    public InvalidEntityException() {

    }

    public InvalidEntityException(String msg) {
        super(msg);
    }
}
