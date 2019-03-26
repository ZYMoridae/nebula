package com.jz.nebula.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.payment.PaymentGateway;

@Service
@Component("paymentService")
public class PaymentService {

	@Autowired
	@Qualifier("stripeGateway")
	private PaymentGateway paymentGatway;

	public PaymentService() {
	}

	public PaymentGateway getPaymentGatway() {
		return paymentGatway;
	}

	public void setPaymentGatway(PaymentGateway paymentGatway) {
		this.paymentGatway = paymentGatway;
	}
}
