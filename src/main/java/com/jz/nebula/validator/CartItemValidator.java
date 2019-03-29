package com.jz.nebula.validator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.Product;

@Component
public class CartItemValidator implements ValidatorInterface, Serializable {

	private final Logger logger = Logger.getLogger(CartItemValidator.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1869981127906910401L;

	@Autowired
	private ProductRepository productRepository;

	public CartItemValidator() {

	}

	private Predicate<Method> filterValidationMethod() {
		return p -> p.getName().startsWith("is");
	}

	@Override
	public boolean validate(Object obj) {
		AtomicBoolean isValid = new AtomicBoolean(true);
		CartItem cartItem = null;
		if (obj instanceof CartItem) {
			cartItem = (CartItem) obj;
		}

		Method[] allMethods = this.getClass().getDeclaredMethods();

		final CartItem paramCartItem = cartItem;

		Arrays.asList(allMethods).stream().filter(this.filterValidationMethod()).forEach(method -> {
			try {
				boolean conditionValid = (boolean) method.invoke(this, paramCartItem);
				isValid.set(isValid.get() && conditionValid);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		});
		
		return isValid.get();
	}

	@SuppressWarnings("unused")
	private boolean isProductExist(CartItem cartItem) {
		Optional<Product> optional = productRepository.findById(cartItem.getProductId());
		return optional.isPresent();
	}

	@SuppressWarnings("unused")
	private boolean isQuantityValid(CartItem cartItem) {

		Optional<Product> optional = productRepository.findById(cartItem.getProductId());
		boolean isValid = true;

		Product product = optional.get();
		isValid = product.getUnitsInStock() >= cartItem.getQuantity();
		
		return isValid;
	}

}
