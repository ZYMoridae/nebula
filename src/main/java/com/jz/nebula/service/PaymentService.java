package com.jz.nebula.service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jz.nebula.auth.IAuthenticationFacade;
import com.jz.nebula.dao.OrderRepository;
import com.jz.nebula.dao.ProductRepository;
import com.jz.nebula.entity.Order;
import com.jz.nebula.entity.OrderItem;
import com.jz.nebula.entity.Payment;
import com.jz.nebula.entity.Product;
import com.jz.nebula.exception.ProductStockException;
import com.jz.nebula.payment.PaymentGateway;
import com.jz.nebula.payment.PaymentType;

@Service
@Component("paymentService")
@Transactional
public class PaymentService {

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

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

	private Order getMyOrder() {
		Optional<Order> order = orderRepository.findByUserId(authenticationFacade.getUser().getId());
		return order.isPresent() ? order.get() : null;
	}

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
		}
	}

	@Transactional(rollbackFor = { Exception.class, ProductStockException.class })
	public Object finaliseOrder() throws Exception {
		Payment payment = new Payment();
		Order order = this.getMyOrder();
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

		} else {
			throw new Exception();
		}
		return charge;
	}

	public Object doPayment(Payment payment) throws Exception {
		return paymentGateway.doPayment(payment);
	}

}
