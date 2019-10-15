package com.jz.nebula.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jz.nebula.amqp.MessageProducer;
import com.jz.nebula.entity.Payment;
import com.jz.nebula.entity.payment.CreditCard;
import com.jz.nebula.entity.payment.PaymentMethodInfo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component("stripeGateway")
public class StripeGateway implements PaymentGateway {
    private final Logger logger = LoggerFactory.getLogger(StripeGateway.class);
    @Autowired
    private StripeProperties properties;
    @Autowired
    private MessageProducer messageProducer;

    public StripeGateway() {

    }

    /**
     * Load config
     */
    @PostConstruct
    public void loadConfig() {
        Stripe.apiKey = properties.getApi().getKey();
    }

    /**
     * @param payment
     * @return
     * @throws InvalidPaymentException
     */
    private boolean isValidPayment(Payment payment) throws InvalidPaymentException {
        boolean isValid = false;
        if (payment.getAmount() <= 0 || payment.getCurrency().isEmpty()) {
            throw new InvalidPaymentException();
        }

        return isValid;
    }

    /**
     * @param payment
     * @return
     */
    private Map<String, Object> constructChargeParams(Payment payment) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(payment, Map.class);
    }

    private synchronized Charge doStripePayment(Payment payment, PaymentMethodInfo paymentMethodInfo) throws InvalidPaymentException, StripeException {
        this.isValidPayment(payment);
        logger.info("Sending charging request to Stripe...");
//        Token token = this.createPaymentMethod(paymentMethodInfo);

        // FIXME: Change token id
//        payment.setSource(token.getId());

        payment.setSource("tok_visa");

        Charge chargedPayment = Charge.create(this.constructChargeParams(payment));
        messageProducer.sendMessage("invoice." + chargedPayment.getId());
        logger.info("Charge success...");
        return chargedPayment;
    }


    private boolean validateCreditCard(CreditCard creditCard) {
        boolean isValid = true;

        //TODO: cc validation

        return isValid;
    }


    private synchronized Token createPaymentMethod(PaymentMethodInfo paymentMethodInfo) throws StripeException, InvalidPaymentException {
        Token token = null;

        if (paymentMethodInfo != null) {
            CreditCard creditCard = paymentMethodInfo.getCreditCard();

            Map<String, Object> tokenParams = new HashMap<>();
            Map<String, Object> cardParams = new HashMap<>();
            cardParams.put("number", creditCard.getCardNumber());

            String expiry = creditCard.getExpiry().substring(0, 4);
            if (expiry.length() != 4) {
                throw new InvalidPaymentException();
            }

            String expiryMonth = expiry.substring(0, 2);
            String expiryYear = expiry.substring(2, 4);

            cardParams.put("exp_month", Integer.valueOf(expiryMonth));
            cardParams.put("exp_year", Integer.valueOf("20" + expiryYear));
            cardParams.put("cvc", creditCard.getCvv());
            tokenParams.put("card", cardParams);

            token = token.create(tokenParams);
        }

        return token;
    }


    /**
     * @param paramCustomer
     * @return
     * @throws StripeException
     * @throws InvalidCustomerException
     */
    public synchronized Customer createCustomer(Customer paramCustomer)
            throws StripeException, InvalidCustomerException {
        Map<String, Object> customerParams = new HashMap<String, Object>();
        String customerEmail = paramCustomer.getEmail();
        if (customerEmail == null || customerEmail.isEmpty()) {
            throw new InvalidCustomerException();
        }
        customerParams.put("email", paramCustomer.getEmail());
        Customer customer = Customer.create(customerParams);
        return customer;
    }

    /**
     * @param paramCustomer
     * @param tokenId
     * @return
     * @throws InvalidCustomerException
     * @throws StripeException
     */
    public synchronized Customer updateCustomer(Customer paramCustomer, String tokenId)
            throws InvalidCustomerException, StripeException {
        Map<String, Object> params = new HashMap<String, Object>();
        String customerId = paramCustomer.getId();
        if (customerId == null || customerId.isEmpty()) {
            throw new InvalidCustomerException();
        }
        params.put("source", tokenId);
        paramCustomer.update(params);
        return Customer.retrieve(customerId);
    }

    @Override
    public Object doPayment(Object paymentInfo, Object paymentMethodInfo) throws InvalidPaymentException, StripeException {
        return this.doStripePayment((Payment) paymentInfo, (PaymentMethodInfo) paymentMethodInfo);
    }

    @Override
    public String getName() throws Exception {
        return this.getClass().getName().toLowerCase().replace("gateway", "");
    }

    @Override
    public Object doRefund(Object refundInfo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Charge retrieveCharge(String chargeId) throws StripeException {
        return Charge.retrieve(chargeId);
    }

}
