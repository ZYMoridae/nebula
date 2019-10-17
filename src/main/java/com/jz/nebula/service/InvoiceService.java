package com.jz.nebula.service;

import java.io.File;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jz.nebula.mail.EmailService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
@Component("invoiceService")
public class InvoiceService {

    @Autowired
    PaymentService paymentService;
    @Autowired
    EmailService emailService;
    private Logger logger = Logger.getLogger(InvoiceService.class);

    public InvoiceService() {

    }

    /**
     * Save invoice
     *
     * @param chargeId
     */
    public void saveInvoice(String chargeId) {
        try {
            Charge charge = paymentService.getPaymentGateway().retrieveCharge(chargeId);
            // TODO: Generate pdf file
            File invoice = null;

            this.updateDbInovice(charge);
            this.sendInvoice(invoice);
        } catch (StripeException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private void updateDbInovice(Charge charge) {
        // TODO: Update purchase info
    }

    private void sendInvoice(File invoice) {
        // TODO: send invoice
    }

}
