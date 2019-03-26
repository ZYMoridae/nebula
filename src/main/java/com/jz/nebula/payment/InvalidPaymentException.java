package com.jz.nebula.payment;

public class InvalidPaymentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9145698554404112507L;

	public InvalidPaymentException() {

	}

	public InvalidPaymentException(String errorMsg) {
		super(errorMsg);
	}

}
