package com.jz.nebula.controller.api;

import com.jz.nebula.entity.Invoice;
import com.jz.nebula.entity.order.OrderItem;
import com.jz.nebula.service.InvoiceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Controller
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final static Logger logger = LogManager.getLogger(InvoiceController.class);

    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getInvoice(@PathVariable String id) throws IOException {

        // FIXME: Is there any issue for concurrency requests
        ByteArrayInputStream bis = invoiceService.getInvoice("http://localhost:8080/api/invoices/internal/" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping(value = "/internal/{invoiceId}")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public String generateInvoice(@PathVariable String invoiceId, Model model) {
        // Process invoice id


        Invoice invoice = null;
        HashMap<String, String> amount = new HashMap<>();
        NumberFormat formatter = new DecimalFormat("#0.00");
        double subtotal = 0;
        if (invoiceService.isInvoiceIdValid(invoiceId)) {
            String _invoiceId = invoiceId.toUpperCase().substring(5, invoiceId.length());
            try {
                invoice = invoiceService.findById(Long.valueOf(_invoiceId));





                Set<OrderItem> orderItems =  invoice.getOrder().getOrderItems();

                for (OrderItem orderItem: orderItems
                ) {
                    subtotal += orderItem.getAmount();
                }
            } catch (Exception e) {
                logger.debug("generateInvoice:: can not find invoice [{}]", _invoiceId);
            }
        } else {
            logger.debug("generateInvoice:: invalid invoice id");
        }

        model.addAttribute("order", invoice != null ? invoice.getOrder() : null);
        model.addAttribute("invoiceId", invoice != null ? invoice.getInvoiceId() : null);

        Date date = new java.util.Date(invoice.getCreatedAt().getTime());
        SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
        model.addAttribute("invoiceDate", invoice != null ? dateFormater.format(date) : null);

        amount.put("subtotal", formatter.format(subtotal));
        amount.put("tax", formatter.format(subtotal * 0.1));
        amount.put("total", formatter.format(subtotal * 1.1));
        model.addAttribute("amount", amount);

        return "invoice/index";
    }
}
