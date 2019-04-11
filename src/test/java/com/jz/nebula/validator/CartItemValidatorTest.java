package com.jz.nebula.validator;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import com.jz.nebula.Application;
import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CartItemValidatorTest {
	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	CartItemValidator cartItemValidator;

	@Before
	public void beforeTests() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void isProductExistTrueTest() {
		CartItem parameterCartItem = new CartItem();
		parameterCartItem.setProductId((long) 1);
		parameterCartItem.setQuantity(3);
		
		Product returnedProduct = new Product();
		returnedProduct.setUnitsInStock(5);
		Optional<Product> optional = Optional.of(returnedProduct);
		when(productRepository.findById((long) 1)).thenReturn(optional);
		
		boolean isExist = cartItemValidator.validate(parameterCartItem);
		
		assertTrue(isExist);
	}
	
	
	@Test
	public void isProductNotExistTest() {
		CartItem parameterCartItem = new CartItem();
		parameterCartItem.setProductId((long) 1);
		parameterCartItem.setQuantity(3);
		
		Product returnedProduct = new Product();
		returnedProduct.setUnitsInStock(5);
		Optional<Product> optional = Optional.ofNullable(null);
		when(productRepository.findById((long) 1)).thenReturn(optional);
		
		boolean isExist = cartItemValidator.validate(parameterCartItem);
		
		assertTrue(!isExist);
	}
	
	@Test
	public void isQuantityInvalidTest() {
		CartItem parameterCartItem = new CartItem();
		parameterCartItem.setProductId((long) 1);
		parameterCartItem.setQuantity(3);
		
		Product returnedProduct = new Product();
		returnedProduct.setUnitsInStock(1);
		Optional<Product> optional = Optional.of(returnedProduct);
		when(productRepository.findById((long) 1)).thenReturn(optional);
		
		boolean isExist = cartItemValidator.validate(parameterCartItem);
		
		assertTrue(!isExist);
	}
}
