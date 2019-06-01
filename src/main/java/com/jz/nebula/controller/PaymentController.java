package com.jz.nebula.controller;

import com.jz.nebula.entity.Payment;
import com.jz.nebula.entity.Role;
import com.jz.nebula.entity.payment.PaymentMethodInfo;
import com.jz.nebula.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

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
     * @throws Exception
     */
    @PostMapping("/finalise")
    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public @ResponseBody
    Object finalise(@RequestBody PaymentMethodInfo paymentMethodInfo) throws Exception {
        return this.paymentService.finaliseOrder(paymentMethodInfo);
    }

}
