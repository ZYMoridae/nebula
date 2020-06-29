package com.jz.nebula.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jz.nebula.service.payment.PaymentType;

public class Payment {
    private int amount;
    private String description;
    private String currency;

    @JsonProperty("receipt_email")
    private String receiptEmail;
    private String source;

    @JsonIgnore
    private PaymentType type;

    public PaymentType getType() {
        return type;
    }

    public void setType(PaymentType type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReceiptEmail() {
        return receiptEmail;
    }

    public void setReceiptEmail(String receiptEmail) {
        this.receiptEmail = receiptEmail;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", currency='" + currency + '\'' +
                ", receiptEmail='" + receiptEmail + '\'' +
                ", source='" + source + '\'' +
                ", type=" + type +
                '}';
    }
}
