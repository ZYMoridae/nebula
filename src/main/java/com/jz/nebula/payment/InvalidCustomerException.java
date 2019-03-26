package com.jz.nebula.payment;

public class InvalidCustomerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5105724925344043409L;

	public InvalidCustomerException() {

	}

	public InvalidCustomerException(String errorMsg) {
		super(errorMsg);
	}
}
