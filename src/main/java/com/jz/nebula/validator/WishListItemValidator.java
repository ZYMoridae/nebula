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
import com.jz.nebula.entity.product.Product;
import com.jz.nebula.entity.WishListItem;

@Component("wishListItemValidator")
public class WishListItemValidator extends BaseValidator implements ValidatorInterface, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1869981127906910401L;
    private final Logger logger = LogManager.getLogger(WishListItemValidator.class);
    @Autowired
    private ProductRepository productRepository;

    public WishListItemValidator() {

    }

    private Predicate<Method> filterValidationMethod() {
        return p -> p.getName().startsWith("is") && p.getAnnotation(Order.class) != null;
    }

    private boolean validateCallback(Method method, WishListItem wishListItem) {
        try {
            boolean conditionValid = (boolean) method.invoke(this, wishListItem);
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
        WishListItem wishListItem = null;
        if (obj instanceof WishListItem) {
            wishListItem = (WishListItem) obj;
        }

        Method[] allMethods = this.getClass().getDeclaredMethods();

        final WishListItem paramWishListItem = wishListItem;

        Arrays.asList(allMethods).stream().filter(this.filterValidationMethod())
                .sorted((m1, m2) -> this.validatorSorter(m1, m2))
                .forEach(method -> isValid.set(isValid.get() && this.validateCallback(method, paramWishListItem)));

        return isValid.get();
    }

    // FIXME: Duplicate logic in other validator
    @Order(1)
    private boolean isProductExist(WishListItem wishListItem) {
        long productId = wishListItem.getProductId();
        Optional<Product> optional = productRepository.findById(productId);
        boolean isPresent = optional.isPresent();
        if (isPresent) {
            logger.info("Product with id:[{}] is present", productId);
        } else {
            logger.info("Product with id:[{}] is not present", productId);
        }

        return isPresent;
    }

    // FIXME: Duplicate logic in other validator
    @Order(2)
    private boolean isQuantityValid(WishListItem wishListItem) {
        Optional<Product> optional = productRepository.findById(wishListItem.getProductId());
        boolean isValid = true;

        if (optional.isPresent()) {
            Product product = optional.get();
            isValid = product.getUnitsInStock() >= wishListItem.getQuantity();
            logger.info("Quantity for product id:[{}] is valid", wishListItem.getQuantity());
        } else {
            isValid = false;
            logger.info("Product with id:[{}] is not present", wishListItem.getProductId());
        }

        return isValid;
    }

}
