package com.salesmanager.core.model.payments;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderTotalSummary;
import com.salesmanager.core.model.system.IntegrationConfiguration;

/**
 * When the user performs a payment using paypal
 * @author Carl Samson
 *
 */
public class RazorpayPayment extends Payment {

	//express checkout
	private String payerId;
	private String paymentToken;

	public RazorpayPayment() {
		super.setPaymentType(PaymentType.RAZORPAY);
	}
	
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	public String getPayerId() {
		return payerId;
	}
	public void setPaymentToken(String paymentToken) {
		this.paymentToken = paymentToken;
	}
	public String getPaymentToken() {
		return paymentToken;
	}

}
