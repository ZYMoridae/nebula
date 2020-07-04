package com.jz.nebula.entity.payment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreditCard {
    private String cardNumber;

    private String name;

    private String expiry;

    private String cvv;
}
