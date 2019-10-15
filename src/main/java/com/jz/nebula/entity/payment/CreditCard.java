package com.jz.nebula.entity.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditCard {
    private String cardNumber;

    private String name;

    private String expiry;

    private String cvv;

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", name='" + name + '\'' +
                ", expiry='" + expiry + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
