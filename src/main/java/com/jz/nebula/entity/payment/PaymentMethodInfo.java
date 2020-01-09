package com.jz.nebula.entity.payment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodInfo {

    public static final String TYPE_CCC = "CCC";

    private String paymentType;

    private CreditCard creditCard;

    private String reference;

    @Override
    public String toString() {
        return "PaymentMethodInfo{" +
                "paymentType='" + paymentType + '\'' +
                ", creditCard=" + creditCard +
                '}';
    }
}
