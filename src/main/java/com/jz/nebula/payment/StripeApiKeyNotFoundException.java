package com.jz.nebula.payment;

public class StripeApiKeyNotFoundException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -5601864655279063222L;

    public StripeApiKeyNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public StripeApiKeyNotFoundException() {

    }

}
