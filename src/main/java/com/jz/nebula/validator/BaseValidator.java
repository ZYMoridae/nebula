package com.jz.nebula.validator;

import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

public class BaseValidator {
    protected int validatorSorter(Method m1, Method m2) {
        int order1 = m1.getAnnotation(Order.class).value();
        int order2 = m2.getAnnotation(Order.class).value();
        if (order1 < order2) {
            return -1;
        } else if (order1 > order2) {
            return 1;
        } else {
            return 0;
        }
    }
}
