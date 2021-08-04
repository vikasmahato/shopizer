package com.salesmanager.shop.admin.controller.products;

import com.salesmanager.core.business.services.catalog.product.brand.BrandService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.business.utils.ajax.AjaxPageableResponse;
import com.salesmanager.core.business.utils.ajax.AjaxResponse;
import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.core.model.catalog.product.brand.BrandDescription;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.controller.ControllerConstants;
import com.salesmanager.shop.admin.model.web.Menu;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.utils.LabelUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.util.*;

@Controller
public class BrandController {
	
	@Inject
	private LanguageService languageService;
	
	@Inject
	private BrandService brandService;
	
	@Inject
	LabelUtils messages;
	
	@Inject
	private CoreConfiguration configuration;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BrandController.class);
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/catalogue/brand/list.html", method=RequestMethod.GET)
	public String getbrands(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		this.setMenu(model, request);
		
		return ControllerConstants.Tiles.Product.brandList;
	}
	
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/catalogue/brand/create.html", method=RequestMethod.GET)
	public String createbrand(  Model model,  HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		return displaybrand(null,model,request,response);		
	}
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/catalogue/brand/edit.html", method=RequestMethod.GET)
	public String editbrand(@RequestParam("id") long brandId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		return displaybrand(brandId,model,request,response);
	}
	
	private String displaybrand(Long brandId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//display menu
		setMenu(model,request);
		
		//List<Language> languages = languageService.getLanguages();
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		List<Language> languages = store.getLanguages();
		
		
		com.salesmanager.shop.admin.model.catalog.Brand brand = new com.salesmanager.shop.admin.model.catalog.Brand();
		List<BrandDescription> descriptions = new ArrayList<BrandDescription>();

		
		if( brandId!=null && brandId.longValue()!=0) {	//edit mode

			Brand dbbrand = new Brand();
			dbbrand = brandService.getById( brandId );
			
			if(dbbrand==null) {
				return ControllerConstants.Tiles.Product.brandList;
			}
			
			if(dbbrand.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
				return ControllerConstants.Tiles.Product.brandList;
			}
			
			Set<BrandDescription> brandDescriptions = dbbrand.getDescriptions();

			
			for(Language l : languages) {

				BrandDescription manufDescription = null;
				if(brandDescriptions!=null) {
					
					for(BrandDescription desc : brandDescriptions) {
						String code = desc.getLanguage().getCode();
						if(code.equals(l.getCode())) {
							manufDescription = desc;
						}

					}
					
				}
				
				if(manufDescription==null) {
					manufDescription = new BrandDescription();
					manufDescription.setLanguage(l);
				}
				
				brand.getDescriptions().add(manufDescription);
				
			}
			
			brand.setbrand( dbbrand );
		
			brand.setCode(dbbrand.getCode());
			brand.setOrder( dbbrand.getOrder() );
			
		} else {	// Create mode

			Brand brandTmp = new Brand();
			brand.setbrand( brandTmp );
			
			for(Language l : languages) {// for each store language

				BrandDescription brandDesc = new BrandDescription();
				brandDesc.setLanguage(l);
				descriptions.add(  brandDesc );
				brand.setDescriptions(descriptions);
				
			}
		}

		model.addAttribute("languages",languages);
		model.addAttribute("brand", brand);
		
		return ControllerConstants.Tiles.Product.brandDetails;
	}
		
	@PreAuthorize("hasRole('PRODUCTS')")  
	@RequestMapping(value="/admin/catalogue/brand/save.html", method=RequestMethod.POST)
	public String savebrand( @Valid @ModelAttribute("brand") com.salesmanager.shop.admin.model.catalog.Brand brand, BindingResult result, Model model,  HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

		this.setMenu(model, request);
		//save or edit a brand

		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		List<Language> languages = languageService.getLanguages();

		if(brand.getDescriptions()!=null && brand.getDescriptions().size()>0) {

			for(BrandDescription description : brand.getDescriptions()) {

				//validate Url Clicked
/*				if ( description.getUrlClicked() != null && !description.getUrlClicked().toString().isEmpty()) {
					try{
						Integer.parseInt( description.getUrlClicked().toString() );

					} catch (Exception e) {

						ObjectError error = new ObjectError("descriptions[${counter.index}].urlClicked","URL Clicked must be a number");
						result.addError(error);
					}
				}*/
			}
		}


	//validate image
		if(brand.getImage()!=null && !brand.getImage().isEmpty()) {

			try {

				String maxHeight = configuration.getProperty("PRODUCT_IMAGE_MAX_HEIGHT_SIZE");
				String maxWidth = configuration.getProperty("PRODUCT_IMAGE_MAX_WIDTH_SIZE");
				String maxSize = configuration.getProperty("PRODUCT_IMAGE_MAX_SIZE");

				BufferedImage image = ImageIO.read(brand.getImage().getInputStream());

				if(!StringUtils.isBlank(maxHeight)) {

					int maxImageHeight = Integer.parseInt(maxHeight);
					if(image.getHeight()>maxImageHeight) {
						ObjectError error = new ObjectError("image",messages.getMessage("message.image.height", locale) + " {"+maxHeight+"}");
						result.addError(error);
					}
				}

				if(!StringUtils.isBlank(maxWidth)) {

					int maxImageWidth = Integer.parseInt(maxWidth);
					if(image.getWidth()>maxImageWidth) {
						ObjectError error = new ObjectError("image",messages.getMessage("message.image.width", locale) + " {"+maxWidth+"}");
						result.addError(error);
					}
				}

				if(!StringUtils.isBlank(maxSize)) {

					int maxImageSize = Integer.parseInt(maxSize);
					if(brand.getImage().getSize()>maxImageSize) {
						ObjectError error = new ObjectError("image",messages.getMessage("message.image.size", locale) + " {"+maxSize+"}");
						result.addError(error);
					}
				}

			} catch (Exception e) {
				LOGGER.error("Cannot validate brand image", e);
			}

		}

		if (result.hasErrors()) {
			model.addAttribute("languages",languages);
			return ControllerConstants.Tiles.Product.brandDetails;
		}

		Brand newbrand = brand.getbrand();

		if ( brand.getbrand().getId() !=null && brand.getbrand().getId()  > 0 ){

			newbrand = brandService.getById( brand.getbrand().getId() );

			if(newbrand.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
				return ControllerConstants.Tiles.Product.brandList;
			}

		}

//		for(brandImage image : brand.getImages()) {
//			if(image.isDefaultImage()) {
//				brand.setProductImage(image);
//			}
//		}

		Set<BrandDescription> descriptions = new HashSet<BrandDescription>();
		if(brand.getDescriptions()!=null && brand.getDescriptions().size()>0) {
			
			for(BrandDescription desc : brand.getDescriptions()) {
				
				desc.setbrand(newbrand);
				descriptions.add(desc);
			}
		}
		newbrand.setDescriptions(descriptions );
		newbrand.setOrder( brand.getOrder() );
		newbrand.setMerchantStore(store);
		newbrand.setCode(brand.getCode());


//		if(brand.getbrandImage()!=null && brand.getbrandImage().getId() == null) {
//			newbrand.setProductImage(null);
//		}



		if(brand.getImage()!=null && !brand.getImage().isEmpty()) {
//
//			String imageName = brand.getImage().getOriginalFilename();
//
//			brandImage brandImage = new brandImage();
//			brandImage.setDefaultImage(true);
//			brandImage.setImage(brand.getImage().getInputStream());
//			brandImage.setbrandImage(imageName);
//
//			List<brandImageDescription> imagesDescriptions = new ArrayList<brandImageDescription>();
//
//			for(Language l : languages) {
//
//				brandImageDescription imageDescription = new brandImageDescription();
//				imageDescription.setName(imageName);
//				imageDescription.setLanguage(l);
//				imageDescription.setbrandImage(productImage);
//				imagesDescriptions.add(imageDescription);
//
//			}
//
//			brandImage.setDescriptions(imagesDescriptions);
//			brandImage.setProduct(newbrand);
//
//			newbrand.getImages().add(brandImage);
//
//			brandService.saveOrUpdate(newbrand);
//
//			//brand displayed
//			brand.setProductImage(brandImage);


		} else {

			brandService.saveOrUpdate(newbrand);
		}

		model.addAttribute("brand", brand);
		model.addAttribute("languages",languages);
		model.addAttribute("success","success");

		return ControllerConstants.Tiles.Product.brandDetails;

	}
	
	
	@SuppressWarnings("unchecked")
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/catalogue/brand/paging.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> pagebrands(HttpServletRequest request, HttpServletResponse response) {
		
		AjaxResponse resp = new AjaxResponse();
		try {
			
			Language language = (Language)request.getAttribute("LANGUAGE");	
			MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
			
			List<Brand> brands = null;
			brands = brandService.listByStore(store, language);
			
				
			for(Brand brand : brands) {
				
				@SuppressWarnings("rawtypes")
				Map entry = new HashMap();
				entry.put("id", brand.getId());

				BrandDescription description = brand.getDescriptions().iterator().next();
				
				entry.put("name", description.getName());
				entry.put("code", brand.getCode());
				entry.put("order", brand.getOrder());
				resp.addDataEntry(entry);
				
			}
			
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);	
		
		} catch (Exception e) {
			LOGGER.error("Error while paging brands", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
		}
		
		resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_SUCCESS);
		
		String returnString = resp.toJSONString();
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/catalogue/brand/remove.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> deletebrand(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		Long sid =  Long.valueOf(request.getParameter("id") );
	
	
		AjaxResponse resp = new AjaxResponse();
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		
		try{
			Brand delbrand = brandService.getById( sid  );
			if(delbrand==null || delbrand.getMerchantStore().getId().intValue() != store.getId().intValue()) {
				resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				String returnString = resp.toJSONString();
				return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
			} 
			
			int count = brandService.getCountManufAttachedProducts( delbrand ).intValue();
			//IF already attached to products it can't be deleted
			if ( count > 0 ){
				resp.setStatusMessage(messages.getMessage("message.product.association", locale));
				resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);			
				String returnString = resp.toJSONString();
				return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
			}	

			brandService.delete( delbrand );
			
			resp.setStatusMessage(messages.getMessage("message.success", locale));
			resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);
			
		} catch (Exception e) {
			
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);	
			LOGGER.error("Cannot delete brand.", e);
		}
		
		String returnString = resp.toJSONString();
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
		
	}
	
	
	@PreAuthorize("hasRole('PRODUCTS')")
	@RequestMapping(value="/admin/brand/checkCode.html", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> checkCode(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		String code = request.getParameter("code");
		String id = request.getParameter("id");


		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
		
		
		AjaxResponse resp = new AjaxResponse();
		
		if(StringUtils.isBlank(code)) {
			resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
			String returnString = resp.toJSONString();
			return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
		}

		
		try {
			
		Brand brand = brandService.getByCode(store, code);
		
		if(brand!=null && StringUtils.isBlank(id)) {
			resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
			String returnString = resp.toJSONString();
			return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
		}
		
		
		if(brand!=null && !StringUtils.isBlank(id)) {
			try {
				Long lid = Long.parseLong(id);
				
				if(brand.getCode().equals(code) && brand.getId().longValue()==lid) {
					resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
					String returnString = resp.toJSONString();
					return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
				}
			} catch (Exception e) {
				resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
				String returnString = resp.toJSONString();
				return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
			}

		}
		
		
		
		

	
		
			


			resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);

		} catch (Exception e) {
			LOGGER.error("Error while getting brand", e);
			resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
			resp.setErrorMessage(e);
		}
		
		String returnString = resp.toJSONString();
		return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
	}
	
	
	
	private void setMenu(Model model, HttpServletRequest request) throws Exception {		
		//display menu
		Map<String,String> activeMenus = new HashMap<String,String>();
		activeMenus.put("catalogue", "catalogue");
		activeMenus.put("brand-list", "brand-list");
		
		@SuppressWarnings("unchecked")
		Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
		
		Menu currentMenu = (Menu)menus.get("catalogue");
		model.addAttribute("currentMenu",currentMenu);
		model.addAttribute("activeMenus",activeMenus);
	}

}
