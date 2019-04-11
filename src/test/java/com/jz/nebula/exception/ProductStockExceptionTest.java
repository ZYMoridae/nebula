package com.jz.nebula.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ProductStockExceptionTest {
	@Test
	public void creationTest() {
		ProductStockException stockException = new ProductStockException();
		assertTrue(stockException != null);
	}
	
	@Test
	public void creationWithMessageTest() {
		ProductStockException stockException = new ProductStockException("test");
		assertEquals("test", stockException.getMessage());
	}
}
