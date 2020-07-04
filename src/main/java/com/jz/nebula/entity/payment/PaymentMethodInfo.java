package com.jz.nebula.entity.payment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PaymentMethodInfo {

    public static final String TYPE_CCC = "CCC";

    private String paymentType;

    private CreditCard creditCard;

    private String reference;
}
