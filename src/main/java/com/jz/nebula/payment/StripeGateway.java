package com.jz.nebula.payment;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jz.nebula.entity.Payment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;

public class StripeGateway implements PaymentGateway{	
	
	private final Logger logger = LoggerFactory.getLogger(StripeGateway.class);
	
	public StripeGateway() {
	}
	
	@Override
	public void loadConfig(String apiKey) {
		Stripe.apiKey = apiKey;
	}
	
	private boolean isValidPayment(Payment payment) throws InvalidPaymentException {
		boolean isValid = false;
		if(payment.getAmount() <= 0 || payment.getCurrency().isEmpty()) {
			throw new InvalidPaymentException();
		}
		
		return isValid;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> constructChargeParams(Payment payment) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	return objectMapper.convertValue(payment, Map.class);
	}
	
	private synchronized Charge doStripePayment(Payment payment) throws InvalidPaymentException, StripeException {
		
		this.isValidPayment(payment);
		
		Map<String, Object> chargeParams = this.constructChargeParams(payment);
		Charge chargedPayment = Charge.create(chargeParams);
		
		return chargedPayment;
	}
	
	public synchronized Customer createCustomer(Customer paramCustomer) throws StripeException, InvalidCustomerException {
		Map<String, Object> customerParams = new HashMap<String, Object>();
		String customerEmail = paramCustomer.getEmail();
		if(customerEmail == null || customerEmail.isEmpty()) {
			throw new InvalidCustomerException();
		}
		customerParams.put("email", paramCustomer.getEmail());
		Customer customer = Customer.create(customerParams);
		return customer;
	}
	
	public synchronized Customer updateCustomer(Customer paramCustomer, String tokenId) throws InvalidCustomerException, StripeException {
		Map<String, Object> params = new HashMap<String, Object>();
		String customerId = paramCustomer.getId();
		if(customerId == null || customerId.isEmpty()) {
			throw new InvalidCustomerException();
		}
		params.put("source", tokenId);
		paramCustomer.update(params);
		return Customer.retrieve(customerId);
	}

	@Override
	public Object doPayment(Object paymentInfo) throws InvalidPaymentException, StripeException {
		return this.doStripePayment((Payment) paymentInfo);
	}

	@Override
	public String getName() throws Exception {
		return this.getClass().getName().toLowerCase().replace("gateway", "");
	}

	@Override
	public Object doRefund(Object refundInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
