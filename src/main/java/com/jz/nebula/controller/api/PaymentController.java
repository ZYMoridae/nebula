package com.jz.nebula.controller.api;

import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.edu.ClazzOrder;
import com.jz.nebula.entity.order.Order;
import com.jz.nebula.entity.payment.PaymentMethodInfo;
import com.jz.nebula.entity.payment.PaymentTokenCategory;
import com.jz.nebula.component.exception.payment.InvalidPaymentTokenException;
import com.jz.nebula.service.PaymentService;
import com.jz.nebula.service.TokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final static Logger logger = LogManager.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TokenService tokenService;

//    /**
//     * @param payment
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
//    public @ResponseBody
//    Object create(@RequestBody Payment payment) throws Exception {
//        return this.paymentService.doPayment(payment);
//    }

    /**
     * @return
     *
     * @throws Exception
     */
//    @PostMapping("/finalise")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
//    public @ResponseBody
//    Object finalise(@RequestBody PaymentMethodInfo paymentMethodInfo) throws Exception {
//        return this.paymentService.finaliseOrder(paymentMethodInfo);
//    }

    /**
     * @param id
     * @param paymentMethodInfo
     *
     * @return
     *
     * @throws Exception
     */
    @PostMapping("/{id}/finalise")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Object finaliseById(@PathVariable("id") long id, @RequestBody PaymentMethodInfo paymentMethodInfo, @RequestParam String paymentToken) throws Exception {
        // Check payment token still alive in Redis
        if (!tokenService.isPaymentTokenValid(id, paymentToken, PaymentTokenCategory.SHOPPING)) {
            throw new InvalidPaymentTokenException();
        }

        return this.paymentService.finaliseOrder(id, paymentMethodInfo);
    }

    /**
     * This method is general payment method, user need to pass in the valid order reference
     *
     * @param paymentMethodInfo
     * @param paymentToken
     *
     * @return
     *
     * @throws Exception
     */
    @PostMapping("/finalise")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Object finaliseOrder(@RequestBody PaymentMethodInfo paymentMethodInfo, @RequestParam String paymentToken) throws Exception {
        String orderReferenceNumber = paymentMethodInfo.getReference();

        String prefix = "";
        long orderId = -1;
        Object paymentResult = null;

        // Get first three chars to determine is valid reference number
        if (orderReferenceNumber.length() >= 3) {
            prefix = orderReferenceNumber.substring(0, 3);
            logger.debug("finaliseOrder:: prefix is [{}]", prefix);
        }

        logger.debug("finaliseOrder:: order id is [{}]", orderReferenceNumber.substring(3));
        orderId = Long.parseLong(orderReferenceNumber.substring(3));

        PaymentTokenCategory paymentTokenCategory = null;

        switch (prefix) {
            case Order.PREFIX: {
                paymentTokenCategory = PaymentTokenCategory.SHOPPING;
                // Check payment token still alive in Redis
                if (!tokenService.isPaymentTokenValid(orderId, paymentToken, paymentTokenCategory)) {
                    throw new InvalidPaymentTokenException();
                }
                paymentResult = this.paymentService.finaliseOrder(orderId, paymentMethodInfo);
                break;
            }
            case ClazzOrder.PREFIX: {
                paymentTokenCategory = PaymentTokenCategory.CLAZZ;
                // Check payment token still alive in Redis
                if (!tokenService.isPaymentTokenValid(orderId, paymentToken, paymentTokenCategory)) {
                    throw new InvalidPaymentTokenException();
                }
                paymentResult = paymentService.finaliseClazzOrder(orderId, paymentMethodInfo);
                break;
            }
            default:
                throw new Exception("Invalid payment reference");
        }
        return paymentResult;
    }

}
