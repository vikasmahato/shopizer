package com.salesmanager.shop.store.controller.store;


import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.store.controller.AbstractController;
import com.salesmanager.shop.store.controller.ControllerConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
public class FooterController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FooterController.class);


	@RequestMapping("/shop/store/privacy.html")
	public String privacy(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		/** template **/
		StringBuilder template = new StringBuilder().append(ControllerConstants.Tiles.Merchant.privacy).append(".").append(store.getStoreTemplate());
		return template.toString();

	}

	@RequestMapping("/shop/store/aboutus.html")
	public String aboutUs(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		/** template **/
		StringBuilder template = new StringBuilder().append(ControllerConstants.Tiles.Merchant.aboutus).append(".").append(store.getStoreTemplate());
		return template.toString();

	}

	@RequestMapping("/shop/store/returnscancellations.html")
	public String returnsCancellations(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		/** template **/
		StringBuilder template = new StringBuilder().append(ControllerConstants.Tiles.Merchant.returnscancellations).append(".").append(store.getStoreTemplate());
		return template.toString();

	}

	@RequestMapping("/shop/store/termsconditions.html")
	public String termsConditions(Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.MERCHANT_STORE);
		/** template **/
		StringBuilder template = new StringBuilder().append(ControllerConstants.Tiles.Merchant.termsconditions).append(".").append(store.getStoreTemplate());
		return template.toString();

	}

}
