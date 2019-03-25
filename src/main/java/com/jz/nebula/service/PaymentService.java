package com.jz.nebula.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.payment.PaymentGateway;
import com.jz.nebula.payment.StripeGateway;

@Service
@Component("paymentService")
public class PaymentService {
	
	private PaymentGateway paymentGatway;
	
	public PaymentService() {
		this.paymentGatway = new StripeGateway();
	}

	public PaymentGateway getPaymentGatway() {
		return paymentGatway;
	}

	public void setPaymentGatway(PaymentGateway paymentGatway) {
		this.paymentGatway = paymentGatway;
	}
}
