package com.jz.nebula.controller.api;

import com.jz.nebula.entity.Invoice;
import com.jz.nebula.entity.edu.ClazzOrder;
import com.jz.nebula.entity.edu.ClazzOrderItem;
import com.jz.nebula.entity.order.Order;
import com.jz.nebula.entity.order.OrderItem;
import com.jz.nebula.service.InvoiceService;
import com.jz.nebula.service.OrderService;
import com.jz.nebula.service.edu.ClazzOrderService;
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
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/api/invoices")
public class InvoiceController {
    private final static Logger logger = LogManager.getLogger(InvoiceController.class);

    private InvoiceService invoiceService;

    private OrderService orderService;

    private ClazzOrderService clazzOrderService;
    private NumberFormat formatter;
    private SimpleDateFormat dateFormatter;

    public InvoiceController() {
        this.formatter = new DecimalFormat("#0.00");
        this.dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Autowired
    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setClazzOrderService(ClazzOrderService clazzOrderService) {
        this.clazzOrderService = clazzOrderService;
    }

    /**
     * Get invoice based on invoice id
     *
     * @param id
     *
     * @return
     *
     * @throws IOException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> getInvoice(@PathVariable String id) throws IOException {

        // FIXME: Is there any issue for concurrency requests
        ByteArrayInputStream byteArrayInputStream = invoiceService.getInvoice("http://localhost:8080/api/invoices/internal/" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=" + id + ".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(byteArrayInputStream));
    }

    /**
     * Internal api call for generating PDF document
     *
     * @param invoiceId
     * @param model
     *
     * @return
     */
    @GetMapping(value = "/internal/{invoiceId}")
//    @RolesAllowed({Role.ROLE_USER, Role.ROLE_VENDOR, Role.ROLE_ADMIN})
    public String generateInvoice(@PathVariable String invoiceId, Model model) {
        // Process invoice id
        Invoice invoice = null;
        HashMap<String, String> amount = new HashMap<>();

        HashMap<String, Object> orderMetaInfo = new HashMap<>();
        String entityType = "";
        if (invoiceService.isInvoiceIdValid(invoiceId)) {
            String _invoiceId = invoiceId.toUpperCase().substring(8, invoiceId.length());
            try {
                invoice = invoiceService.findByInvoiceId(invoiceId);

                entityType = invoice.getEntityType();

                // Get order meta info based on type
                if (entityType.equals(Order.PREFIX)) {
                    orderMetaInfo = getOrderMetaInfo(invoice.getEntityId());
                } else if (entityType.equals(ClazzOrder.PREFIX)) {
                    orderMetaInfo = getClazzOrderMetaInfo(invoice.getEntityId());
                }

            } catch (Exception e) {
                logger.debug("generateInvoice:: can not find invoice [{}]", _invoiceId);
            }
        } else {
            logger.debug("generateInvoice:: invalid invoice id");
        }

        model.addAttribute("order", invoice != null ? orderMetaInfo.get("order") : null);
        model.addAttribute("invoiceId", invoice != null ? invoice.getInvoiceId() : null);
        model.addAttribute("entityType", entityType);

        Date date = new java.util.Date(invoice.getCreatedAt().getTime());
        model.addAttribute("invoiceDate", invoice != null ? dateFormatter.format(date) : null);

        amount.put("subtotal", (String) orderMetaInfo.get("subtotal"));
        amount.put("tax", (String) orderMetaInfo.get("tax"));
        amount.put("total", (String) orderMetaInfo.get("total"));
        model.addAttribute("amount", amount);

        return "invoice/index";
    }

    /**
     * Get meta info based on shopping order
     *
     * @param orderId
     *
     * @return
     *
     * @throws Exception
     */
    private HashMap getOrderMetaInfo(long orderId) throws Exception {
        HashMap<String, Object> res = new HashMap<>();

        Order order = orderService.findById(orderId);
        Set<OrderItem> orderItems = order.getOrderItems();
        double subtotal = 0;

        for (OrderItem orderItem : orderItems
        ) {
            subtotal += orderItem.getAmount();
        }

        res.put("order", order);
        res.put("subtotal", formatter.format(subtotal));
        res.put("tax", formatter.format(subtotal * 0.1));
        res.put("total", formatter.format(subtotal * 1.1));

        return res;
    }

    /**
     * Get meta info based on clazz order id
     *
     * @param orderId
     *
     * @return
     *
     * @throws Exception
     */
    private HashMap getClazzOrderMetaInfo(long orderId) throws Exception {
        HashMap<String, Object> res = new HashMap<>();

        ClazzOrder order = clazzOrderService.findById(orderId);
        double subtotal = order.getTotalAmount(false).get();

        res.put("order", order);
        res.put("subtotal", formatter.format(subtotal));
        res.put("tax", formatter.format(subtotal * 0.1));
        res.put("total", formatter.format(subtotal * 1.1));

        return res;
    }
}
