package com.jz.nebula.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jz.nebula.service.payment.PaymentType;
import lombok.Data;

@Data
public class Payment {
    private int amount;
    private String description;
    private String currency;

    @JsonProperty("receipt_email")
    private String receiptEmail;
    private String source;

    @JsonIgnore
    private PaymentType type;
}
