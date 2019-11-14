package com.jz.nebula.exception;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class InvalidEntityException extends Exception implements Serializable {

    public InvalidEntityException() {

    }

    public InvalidEntityException(@NotNull String msg) {
        super(msg);
    }
}
