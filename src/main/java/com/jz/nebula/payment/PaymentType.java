package com.jz.nebula.payment;

public enum PaymentType {
	CHARGE(1), REFUND(2);

	public final int value;

	private PaymentType(int value) {
		this.value = value;
	}

}
