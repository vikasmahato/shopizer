package com.salesmanager.core.business.modules.integration.payment.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
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

public class RazorpayPayment implements PaymentModule {

    @Inject
    private ProductPriceUtils productPriceUtils;


    private static final Logger LOGGER = LoggerFactory.getLogger(RazorpayPayment.class);

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

        return transaction;
    }

    @Override
    public Transaction authorize(MerchantStore store, Customer customer,
                                 List<ShoppingCartItem> items, BigDecimal amount, Payment payment,
                                 IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException {

        Transaction transaction = new Transaction();
        try {


            String apiKey = configuration.getIntegrationKeys().get("key_secret");

            if(payment.getPaymentMetaData()==null || StringUtils.isBlank(apiKey)) {
                IntegrationException te = new IntegrationException(
                        "Can't process Razorpay, missing payment.metaData");
                te.setExceptionType(IntegrationException.TRANSACTION_EXCEPTION);
                te.setMessageCode("message.payment.error");
                te.setErrorCode(IntegrationException.TRANSACTION_EXCEPTION);
                throw te;
            }

            /**
             * this is send by Razorpay from tokenization ui
             */
            String token = payment.getPaymentMetaData().get("Razorpay_token");

            if(StringUtils.isBlank(token)) {
                IntegrationException te = new IntegrationException(
                        "Can't process Razorpay, missing Razorpay token");
                te.setExceptionType(IntegrationException.TRANSACTION_EXCEPTION);
                te.setMessageCode("message.payment.error");
                te.setErrorCode(IntegrationException.TRANSACTION_EXCEPTION);
                throw te;
            }


            String amnt = productPriceUtils.getAdminFormatedAmount(store, amount);

            //Razorpay does not support floating point
            //so amnt * 100 or remove floating point
            //553.47 = 55347

            String strAmount = String.valueOf(amnt);
            strAmount = strAmount.replace(".","");

            Map<String, Object> chargeParams = new HashMap<String, Object>();

            RazorpayClient razorpay = new RazorpayClient(configuration.getIntegrationKeys().get("key_id"), apiKey);
            JSONObject options = new JSONObject();
            options.put("amount", strAmount);   //TODO: AJAY This amount should be in Paise?
            options.put("currency", store.getCurrency().getCode());

            com.razorpay.Order order = razorpay.Orders.create(options);

            //Map<String,String> metadata = ch.getMetadata();


            transaction.setAmount(amount);
            transaction.setRazorpayOrderId(order.get("id"));
            transaction.setTransactionDate(new Date());
            transaction.setTransactionType(TransactionType.AUTHORIZE);
            transaction.setPaymentType(PaymentType.CREDITCARD);
            transaction.getTransactionDetails().put("TRANSACTIONID", token);
            transaction.getTransactionDetails().put("MESSAGETEXT", null);

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
        //TODO : AJAY Capture payment
        return null;

    }

    @Override
    public Transaction authorizeAndCapture(MerchantStore store, Customer customer,
                                           List<ShoppingCartItem> items, BigDecimal amount, Payment payment,
                                           IntegrationConfiguration configuration, IntegrationModule module)
            throws IntegrationException {

        return  null;

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
