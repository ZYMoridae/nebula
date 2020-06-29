package com.jz.nebula.service.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

public interface PaymentGateway {
    String getName() throws Exception;

    Object doPayment(Object paymentInfo, Object paymentMethodInfo) throws Exception;

    Object doRefund(Object refundInfo) throws Exception;

    Charge retrieveCharge(String chargeId) throws StripeException;
}
