package com.jz.nebula.validator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.entity.CartItem;
import com.jz.nebula.entity.Product;

@Component("cartItemValidator")
public class CartItemValidator extends BaseValidator implements ValidatorInterface, Serializable {

    private final Logger logger = LogManager.getLogger(CartItemValidator.class);

    /**
     *
     */
    private static final long serialVersionUID = 1869981127906910401L;

    @Autowired
    private ProductRepository productRepository;

    public CartItemValidator() {

    }

    private Predicate<Method> filterValidationMethod() {
        return p -> p.getName().startsWith("is") && p.getAnnotation(Order.class) != null;
    }

    private boolean validateCallback(Method method, CartItem cartItem) {
        try {
            boolean conditionValid = (boolean) method.invoke(this, cartItem);
            return conditionValid;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error("Method invoke failed, method name:[{}]", method.getName());
            e.printStackTrace();
        }
        return true;
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

        Arrays.asList(allMethods).stream().filter(this.filterValidationMethod())
                .sorted((m1, m2) -> this.validatorSorter(m1, m2))
                .forEach(method -> isValid.set(isValid.get() && this.validateCallback(method, paramCartItem)));

        return isValid.get();
    }

    @Order(1)
    private boolean isProductExist(CartItem cartItem) {
        long productId = cartItem.getProductId();
        Optional<Product> optional = productRepository.findById(productId);
        boolean isPresent = optional.isPresent();
        if (isPresent) {
            logger.info("Product with id:[{}] is present", productId);
        } else {
            logger.info("Product with id:[{}] is not present", productId);
        }

        return isPresent;
    }

    @Order(2)
    private boolean isQuantityValid(CartItem cartItem) {
        Optional<Product> optional = productRepository.findById(cartItem.getProductId());
        boolean isValid = true;

        if (optional.isPresent()) {
            Product product = optional.get();
            isValid = product.getUnitsInStock() >= cartItem.getQuantity();
            logger.info("Quantity for product id:[{}] is valid", cartItem.getQuantity());
        } else {
            isValid = false;
            logger.info("Product with id:[{}] is not present", cartItem.getProductId());
        }

        return isValid;
    }

}
