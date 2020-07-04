package com.jz.nebula.service;

import com.jz.nebula.util.auth.AuthenticationFacade;
import com.jz.nebula.dao.OrderLogisticsInfoRepository;
import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.dao.OrderStatusRepository;
import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.dao.sku.SkuRepository;
import com.jz.nebula.entity.*;
import com.jz.nebula.entity.edu.ClazzOrder;
import com.jz.nebula.entity.order.Order;
import com.jz.nebula.entity.order.OrderItem;
import com.jz.nebula.entity.order.OrderLogisticsInfo;
import com.jz.nebula.entity.order.OrderStatus;
import com.jz.nebula.entity.payment.PaymentMethodInfo;
import com.jz.nebula.entity.sku.Sku;
import com.jz.nebula.component.exception.ProductStockException;
import com.jz.nebula.service.payment.PaymentGateway;
import com.jz.nebula.service.payment.PaymentType;
import com.jz.nebula.service.edu.ClazzOrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class PaymentService {
    private final Logger logger = LogManager.getLogger(PaymentService.class);

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    @Qualifier("stripeGateway")
    private PaymentGateway paymentGateway;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OrderLogisticsInfoRepository orderLogisticsInfoRepository;

    @Autowired
    private OrderService orderService;

    private ClazzOrderService clazzOrderService;


    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public PaymentService(ClazzOrderService clazzOrderService) {
        this.clazzOrderService = clazzOrderService;
    }


    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public void setPaymentGateway(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    /**
     * Get order for current user
     *
     * @return
     */
    private Order getMyOrder() {
        List<Order> orders = orderRepository.findByUserIdAndOrderStatusId(authenticationFacade.getUser().getId(),
                OrderStatus.StatusType.PENDING.value);

        Order order = orders.get(0);
//        if (orders.size() == 1) {
//            order = ;
//        }
        return order;
    }

    /**
     * Update stock. We will update the SKU based on the sku code stored in side order item.
     *
     * @param orderItem
     *
     * @throws ProductStockException
     */
    private synchronized void updateStock(OrderItem orderItem) throws ProductStockException {
        Optional<Sku> skuOptional = skuRepository.findBySkuCode(orderItem.getSkuCode());
        if (skuOptional.isPresent()) {
            Sku sku = skuOptional.get();
            AtomicInteger currentStock = new AtomicInteger(sku.getStock());
            currentStock.addAndGet(orderItem.getQuantity() * -1);
            if (currentStock.get() < 0) throw new ProductStockException();

            sku.setStock(currentStock.get());
            skuRepository.save(sku);
            logger.info("updateStock::Sku code:[{}] stock was updated", sku.getSkuCode());
        }
    }

    /**
     * Update order status
     *
     * @param order
     *
     * @return
     */
    private Order updateOrderStatus(Order order) {
        order.setOrderStatus(orderStatusRepository.findById((long) OrderStatus.StatusType.PAID.value).get());
        return this.orderRepository.save(order);
    }

    /**
     * Finalise order by id
     *
     * @param id
     * @param paymentMethodInfo
     *
     * @return
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class, ProductStockException.class})
    public Object finaliseOrder(Long id, PaymentMethodInfo paymentMethodInfo) throws Exception {
        Optional<Order> order = orderRepository.findById(id);

        applicationEventPublisher.publishEvent(order.get());
        if (!order.isPresent()) {
            throw new Exception();
        }
        return this.processOrder(order.get(), paymentMethodInfo);
    }


    /**
     * Finalise order and rollback when encounter a exception
     *
     * @return
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class, ProductStockException.class})
    public Object finaliseOrder(PaymentMethodInfo paymentMethodInfo) throws Exception {
        Order order = this.getMyOrder();
        if (order == null) throw new Exception();
        return this.processOrder(order, paymentMethodInfo);
    }

    private Optional getTotalAmount(Order order) {
        Optional<Double> totalAmount = order.getOrderItems().stream().map(item -> item.getAmount()).reduce((x, y) -> x + y);
        logger.debug("getTotalAmount:: order id: [{}] with total amount [{]]", order.getId(), totalAmount);

        // TODO: Currency mapping. e.g. USD => AUD, CNY => AUD

        // TODO: Put markup fee or credit surcharge here
        return totalAmount;
    }

    /**
     * @param order
     * @param paymentMethodInfo
     *
     * @return
     *
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class, ProductStockException.class})
    protected Object processOrder(Order order, PaymentMethodInfo paymentMethodInfo) throws Exception {

        Order finalOrder = order;
        // Once finalise order failed, we need to delete logistic info which saved in the previous step
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            public void afterCommit(int status) {
                //do stuff right after commit
                if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {
                    OrderLogisticsInfo orderLogisticsInfo = orderLogisticsInfoRepository.findByOrdersId(finalOrder.getId()).get();
                    if (orderLogisticsInfo != null) {
                        orderLogisticsInfoRepository.delete(orderLogisticsInfo);
                    }
                }
            }
        });

        Payment payment = new Payment();
        Map<String, Object> result = new ConcurrentHashMap<>();

        Optional<Double> totalAmount = getTotalAmount(order);
        Object charge;

        // Update order stock
        order.getOrderItems().stream().forEach(item -> {
            try {
                this.updateStock(item);
            } catch (ProductStockException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        });

        if (totalAmount.isPresent()) {
            payment.setAmount(totalAmount.get().intValue());
            payment.setType(PaymentType.CHARGE);

            // TODO: Need to be changed for currency
            payment.setCurrency("aud");
            payment.setSource("tok_visa");
            payment.setReceiptEmail(authenticationFacade.getUser().getEmail());

            charge = this.doPayment(payment, paymentMethodInfo);
            logger.info("Order id:[{}] has been charged", order.getId());

            // Update the order status
            order.setOrderStatus(orderStatusRepository.findByName("PAID").get());
            order = this.updateOrderStatus(order);
            logger.info("Order id:[{}] status has been updated", order.getId());

            // Delete product from shopping cart
            this.orderService.deleteCartItems(order.getOrderItems());

            // Generate invoice
            generateInvoice(order.getId(), Order.PREFIX);
        } else {
            throw new Exception();
        }

        // FIXME: Should we return stripe info to user?
        result.put("payment", charge);
        result.put("order", order);

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public HashMap finaliseClazzOrder(long orderId, PaymentMethodInfo paymentMethodInfo) throws Exception {
        HashMap<String, Object> result = new HashMap<>();
        Payment payment = new Payment();
        Object charge = null;
        ClazzOrder clazzOrder = clazzOrderService.findById(orderId);
        Optional<Double> totalAmount = clazzOrder.getTotalAmount(true);

        if (totalAmount.isPresent() && totalAmount.get() > 0) {
            payment.setAmount(totalAmount.get().intValue());

            payment.setType(PaymentType.CHARGE);

            // TODO: Need to be changed for currency
            payment.setCurrency("aud");
            payment.setSource("tok_visa");
            payment.setReceiptEmail(authenticationFacade.getUser().getEmail());

            charge = this.doPayment(payment, paymentMethodInfo);
            logger.info("ClazzOrder id:[{}] has been charged", orderId);

//            clazzOrder.setStatusId((long) OrderStatus.StatusType.PAID.value);

            clazzOrder = clazzOrderService.save(clazzOrder);

            // Generate invoice
            generateInvoice(orderId, ClazzOrder.PREFIX);
        }

        result.put("payment", charge);
        result.put("order", clazzOrder);

        return result;
    }

    public Invoice generateInvoice(long entityId, String entityType) {
        Invoice invoice = new Invoice();
        invoice.setEntityId(entityId);
        invoice.setInvoiceId(invoiceService.formatInvoiceId(entityId, entityType));
        invoice.setEntityType(entityType);
        return this.invoiceService.save(invoice);
    }

//    /**
//     * Delete product from shopping cart after order finalised
//     *
//     * @param finalisedOrderItems
//     */
//    public void deleteCartItems(Set<OrderItem> finalisedOrderItems) {
//        Cart cart = this.cartService.getMyCart();
//
//        Set<CartItem> cartItems = cart.getCartItems();
//
//        List<Long> finalisedOrderItemsId = finalisedOrderItems.stream().map(orderItem -> orderItem.getProductId()).collect(Collectors.toList());
//
//        for (CartItem cartItem : cartItems) {
//            if (finalisedOrderItemsId.contains(cartItem.getProductId())) {
//                cartItemService.delete(cartItem.getId());
//            }
//        }
//    }


    /**
     * Do payment
     *
     * @param payment
     *
     * @return
     *
     * @throws Exception
     */
    public Object doPayment(Payment payment, PaymentMethodInfo paymentMethodInfo) throws Exception {
        return paymentGateway.doPayment(payment, paymentMethodInfo);
    }

}
