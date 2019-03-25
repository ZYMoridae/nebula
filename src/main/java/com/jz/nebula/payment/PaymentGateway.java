package com.jz.nebula.payment;

public interface PaymentGateway {	
	String getName() throws Exception;
	Object doPayment(Object paymentInfo) throws Exception;
	Object doRefund(Object refundInfo) throws Exception;
	public void loadConfig(String apiKey);
}
