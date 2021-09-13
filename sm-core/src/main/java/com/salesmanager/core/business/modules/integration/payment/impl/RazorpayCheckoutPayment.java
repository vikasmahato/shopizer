package com.salesmanager.core.business.modules.integration.payment.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.inject.Inject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.salesmanager.core.model.order.OrderTotalSummary;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.salesmanager.core.business.utils.ProductPriceUtils;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.Transaction;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.modules.integration.IntegrationException;
import com.salesmanager.core.modules.integration.payment.model.PaymentModule;

public class RazorpayCheckoutPayment implements PaymentModule {

    @Inject
    private ProductPriceUtils productPriceUtils;


    private static final Logger LOGGER = LoggerFactory.getLogger(RazorpayCheckoutPayment.class);


    public String getOrderId(MerchantStore store, IntegrationConfiguration config, OrderTotalSummary orderTotalSummary) throws RazorpayException, UnirestException {

        Unirest.setTimeouts(0, 0);
        String key = Base64.getEncoder().encodeToString((config.getIntegrationKeys().get("key_id") + ":" + config.getIntegrationKeys().get("key_secret") ).getBytes());
        HttpResponse<JsonNode> response = Unirest.post("https://api.razorpay.com/v1/orders")
                .header("Accept", "application/json")
                .header("Authorization", "Basic "+key)
                .header("Content-Type", "application/json")
                .body("{\n    \"amount\": "+orderTotalSummary.getTotal().multiply(BigDecimal.valueOf(100)).intValue()+",\n    \"currency\": \"INR\",\n    \"receipt\": \"receipt#1\"\n}")
                .asJson();

        return  response.getBody().getObject().get("id").toString();

        /*RazorpayClient razorpay = new RazorpayClient(config.getIntegrationKeys().get("key_id"), config.getIntegrationKeys().get("key_secret"));
        new RazorpayClient("rzp_test_bZuQuARGBl0Q0i", "YpyF0qDqdNZK7inG6cyvG9ru");
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", orderTotalSummary.getTotal().multiply(BigDecimal.valueOf(100)).longValue()); // amount in the smallest currency unit
        orderRequest.put("currency", store.getCurrency().getCode());

        com.razorpay.Order razorPayOrder = razorpay.Orders.create(orderRequest);
        return razorPayOrder;*/
    }

    @Override
    public void validateModuleConfiguration(IntegrationConfiguration integrationConfiguration, MerchantStore store) throws IntegrationException {

        List<String> errorFields = null;

        Map<String,String> keys = integrationConfiguration.getIntegrationKeys();

        if(keys==null || StringUtils.isBlank(keys.get("key_secret"))) {
            errorFields = new ArrayList<String>();
            errorFields.add("key_secret");
        }


        if(keys==null || StringUtils.isBlank(keys.get("key_id"))) {
            if(errorFields==null) {
                errorFields = new ArrayList<String>();
            }
            errorFields.add("key_id");
        }

        if(keys==null || StringUtils.isBlank(keys.get("transaction"))) {
            if(errorFields==null) {
                errorFields = new ArrayList<String>();
            }
            errorFields.add("transaction");
        }



        if(errorFields!=null) {
            IntegrationException ex = new IntegrationException(IntegrationException.ERROR_VALIDATION_SAVE);
            ex.setErrorFields(errorFields);
            throw ex;

        }

    }



    @Override
    public Transaction initTransaction(MerchantStore store, Customer customer,
                                       BigDecimal amount, Payment payment,
                                       IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException {
        Validate.notNull(configuration,"Configuration cannot be null");
        String publicKey = configuration.getIntegrationKeys().get("key_id");
        Validate.notNull(publicKey,"Key id not found in configuration");

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDetails(publicKey);
        transaction.setPaymentType(payment.getPaymentType());
        transaction.setTransactionDate(new Date());
        transaction.setTransactionType(payment.getTransactionType());
        transaction.setRazorpayOrderId(payment.getPaymentMetaData().get("r_order_id"));

        return transaction;
    }

    @Override
    public Transaction authorize(MerchantStore store, Customer customer,
                                 List<ShoppingCartItem> items, BigDecimal amount, Payment payment,
                                 IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException {

        Transaction transaction = new Transaction();
        try {

            transaction.setAmount(amount);
            //transaction.setOrder(order);
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType(TransactionType.AUTHORIZE);
            transaction.setPaymentType(PaymentType.RAZORPAY);
            transaction.getTransactionDetails().put("TRANSACTIONID", "");
            transaction.getTransactionDetails().put("TRNAPPROVED", "");
            transaction.getTransactionDetails().put("TRNORDERNUMBER", "");


        } catch (Exception e) {

            throw buildException(e);

        }

        return transaction;


    }

    @Override
    public Transaction capture(MerchantStore store, Customer customer,
                               Order order, Transaction capturableTransaction,
                               IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException {
        Transaction transaction = new Transaction();
        try {

            transaction.setAmount(order.getTotal());
            //transaction.setOrder(order);
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType(TransactionType.AUTHORIZE);
            transaction.setPaymentType(PaymentType.RAZORPAY);
            transaction.getTransactionDetails().put("TRANSACTIONID", "");
            transaction.getTransactionDetails().put("TRNAPPROVED", "");
            transaction.getTransactionDetails().put("TRNORDERNUMBER", "");


        } catch (Exception e) {

            throw buildException(e);

        }

        return transaction;

    }

    @Override
    public Transaction authorizeAndCapture(MerchantStore store, Customer customer,
                                           List<ShoppingCartItem> items, BigDecimal amount, Payment payment,
                                           IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException {

        Transaction transaction = new Transaction();
        try {

            transaction.setAmount(amount);
            //transaction.setOrder(order);
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType(TransactionType.AUTHORIZE);
            transaction.setPaymentType(PaymentType.RAZORPAY);
            transaction.getTransactionDetails().put("TRANSACTIONID", "");
            transaction.getTransactionDetails().put("TRNAPPROVED", "");
            transaction.getTransactionDetails().put("TRNORDERNUMBER", "");


        } catch (Exception e) {

            throw buildException(e);

        }

        return transaction;

    }

    @Override
    public Transaction refund(boolean partial, MerchantStore store, Transaction transaction,
                              Order order, BigDecimal amount,
                              IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException {

        return null;

    }

    private IntegrationException buildException(Exception ex) {

        if (ex instanceof RazorpayException) {
            LOGGER.error("Error with Razorpay", ex.getMessage());
            RazorpayException e = (RazorpayException)ex;
            // Display a very generic error to the user, and maybe send
            // yourself an email
            IntegrationException te = new IntegrationException(
                    "Can't process Razorpay authorize, missing invalid payment parameters");
            te.setExceptionType(IntegrationException.TRANSACTION_EXCEPTION);
            te.setMessageCode("message.payment.error");
            te.setErrorCode(IntegrationException.TRANSACTION_EXCEPTION);
            return te;



        } else if (ex instanceof Exception) {
            LOGGER.error("Razorpay module error", ex.getMessage());
            if(ex instanceof IntegrationException) {
                return (IntegrationException)ex;
            } else {
                IntegrationException te = new IntegrationException(
                        "Can't process Razorpay authorize, exception", ex);
                te.setExceptionType(IntegrationException.TRANSACTION_EXCEPTION);
                te.setMessageCode("message.payment.error");
                te.setErrorCode(IntegrationException.TRANSACTION_EXCEPTION);
                return te;
            }


        } else {
            LOGGER.error("Razorpay module error", ex.getMessage());
            IntegrationException te = new IntegrationException(
                    "Can't process Razorpay authorize, exception", ex);
            te.setExceptionType(IntegrationException.TRANSACTION_EXCEPTION);
            te.setMessageCode("message.payment.error");
            te.setErrorCode(IntegrationException.TRANSACTION_EXCEPTION);
            return te;
        }

    }





}
