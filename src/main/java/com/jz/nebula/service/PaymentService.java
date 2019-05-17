package com.jz.nebula.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.dao.OrderStatusRepository;
import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.entity.Order;
import com.jz.nebula.entity.OrderItem;
import com.jz.nebula.entity.OrderStatus;
import com.jz.nebula.entity.Payment;
import com.jz.nebula.entity.Product;
import com.jz.nebula.exception.ProductStockException;
import com.jz.nebula.payment.PaymentGateway;
import com.jz.nebula.payment.PaymentType;

@Service
@Transactional
public class PaymentService {
    private final Logger logger = LogManager.getLogger(PaymentService.class);

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    @Qualifier("stripeGateway")
    private PaymentGateway paymentGateway;

    public PaymentService() {
    }

    public PaymentGateway getPaymentGatway() {
        return paymentGateway;
    }

    public void setPaymentGatway(PaymentGateway paymentGatway) {
        this.paymentGateway = paymentGatway;
    }

    /**
     * Get order for current user
     *
     * @return
     */
    private Order getMyOrder() {
        List<Order> orders = orderRepository.findByUserIdAndOrderStatusId(authenticationFacade.getUser().getId(),
                OrderStatus.StatusType.PENDING.value);

        Order order = null;
        if (orders.size() == 1) {
            order = orders.get(0);
        }
        return order;
    }

    /**
     * Update stock
     *
     * @param orderItem
     * @throws ProductStockException
     */
    private synchronized void updateStock(OrderItem orderItem) throws ProductStockException {
        Optional<Product> optional = productRepository.findById(orderItem.getProductId());
        if (optional.isPresent()) {
            Product product = optional.get();
            AtomicInteger currentStock = new AtomicInteger(product.getUnitsInStock());
            currentStock.addAndGet(orderItem.getQuantity() * -1);
            if (currentStock.get() < 0) {
                throw new ProductStockException();
            }

            product.setUnitsInStock(currentStock.get());
            productRepository.save(product);
            logger.info("Product id:[{}] stock was updated", product.getId());
        }
    }

    /**
     * Update order status
     *
     * @param order
     * @return
     */
    private Order updateOrderStatus(Order order) {
        order.setOrderStatus(orderStatusRepository.findById((long) OrderStatus.StatusType.PAID.value).get());
        return this.orderRepository.save(order);
    }

    /**
     * Finalise order
     *
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = {Exception.class, ProductStockException.class})
    public Object finaliseOrder() throws Exception {
        Payment payment = new Payment();
        Order order = this.getMyOrder();
        if (order == null) {
            throw new Exception();
        }
        Optional<Double> totalAmount = order.getOrderItems().stream().map(item -> item.getAmount()).reduce((x, y) -> x + y);
        Object charge;
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
            payment.setCurrency("aud");
            payment.setSource("tok_visa");
            payment.setReceiptEmail(authenticationFacade.getUser().getEmail());
            charge = this.doPayment(payment);
            logger.info("Order id:[{}] has been charged", order.getId());

            // Update the order status
            order.setOrderStatusId((long) OrderStatus.StatusType.PAID.value);
            order = this.updateOrderStatus(order);
            logger.info("Order id:[{}] status has been updated", order.getId());
        } else {
            throw new Exception();
        }
        Map<String, Object> result = new ConcurrentHashMap<>();
        result.put("payment", charge);
        result.put("order", order);

        return result;
    }

    /**
     * Do payment
     *
     * @param payment
     * @return
     * @throws Exception
     */
    public Object doPayment(Payment payment) throws Exception {
        return paymentGateway.doPayment(payment);
    }

}
