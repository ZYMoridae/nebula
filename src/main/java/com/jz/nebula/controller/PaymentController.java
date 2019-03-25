package com.jz.nebula.controller;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jz.nebula.entity.Payment;
import com.jz.nebula.entity.Role;
import com.jz.nebula.payment.StripeProperties;
import com.jz.nebula.service.PaymentService;

@RestController
@RequestMapping("/payments")
public class PaymentController {
	@Autowired
	private StripeProperties properties;
	
	@Autowired
	private PaymentService paymentService;
	
	@PostConstruct
	public void paymentServiceInit() {
		this.paymentService.getPaymentGatway().loadConfig(properties.getApi().getKey());
	}
	
	@PostMapping("")
	@RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
	public @ResponseBody Object create(@RequestBody Payment payment) throws Exception {
		return this.paymentService.getPaymentGatway().doPayment(payment);
	}
    
}
