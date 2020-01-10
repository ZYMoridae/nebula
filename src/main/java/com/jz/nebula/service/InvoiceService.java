package com.jz.nebula.service;

import java.io.*;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.html2pdf.HtmlConverter;
import com.jz.nebula.dao.InvoiceRepository;
import com.jz.nebula.entity.Invoice;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jz.nebula.mail.EmailService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@Service
public class InvoiceService {

    public static final int INVOICE_LENGTH = 24;

    @Autowired
    PaymentService paymentService;
    @Autowired
    EmailService emailService;
    @Autowired
    InvoiceRepository invoiceRepository;

    Pattern invoicePattern;

    private Logger logger = Logger.getLogger(InvoiceService.class);

    public InvoiceService() {
        this.invoicePattern = Pattern.compile("^(INV)[A-Z]{5}[0-9]{16}");
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

    /**
     * Find by invoice id
     *
     * @param invoiceId
     * @return
     */
    public Invoice findByInvoiceId(String invoiceId) {
        return invoiceRepository.findByInvoiceId(invoiceId);
    }

    private void updateDbInovice(Charge charge) {
        // TODO: Update purchase info

    }

    private void sendInvoice(File invoice) {
        // TODO: send invoice
    }

    public ByteArrayInputStream getInvoice(String path) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        URL url = new URL(path);
//        InputStream targetStream = new FileInputStream(file);
        HtmlConverter.convertToPdf(url.openStream(), out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * Find invoice by id
     *
     * @param id
     *
     * @return
     */
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id).get();
    }

    /**
     * Check invoice id is valid or not
     *
     * @param invoiceId
     *
     * @return
     */
    public boolean isInvoiceIdValid(String invoiceId) {
        return this.invoicePattern.matcher(invoiceId).matches();
    }

    /**
     * Save invoice
     *
     * @param invoice
     *
     * @return
     */
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    /**
     * Left padding 0 for invoice number
     * FIXME: We should add country code in the invoice number
     *
     * @param orderId
     *
     * @return
     */
    public String formatInvoiceId(Long orderId, String entityType) {
        String sOrderId = String.valueOf(orderId);
        String _invoiceId = StringUtils.leftPad(sOrderId, INVOICE_LENGTH - 8, "0");
        return "INV" + entityType + "CN" + _invoiceId;
    }
}
