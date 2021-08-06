package com.salesmanager.shop.admin.controller.products;

import com.salesmanager.core.business.services.catalog.product.vendor.VendorService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.business.utils.ajax.AjaxPageableResponse;
import com.salesmanager.core.business.utils.ajax.AjaxResponse;
import com.salesmanager.core.model.catalog.product.vendor.VendorDescription;
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
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
public class VendorController {

    @Inject
    private LanguageService languageService;

    @Inject
    private VendorService vendorService;

    @Inject
    LabelUtils messages;

    @Inject
    private CoreConfiguration configuration;

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorController.class);

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/catalogue/vendor/list.html", method= RequestMethod.GET)
    public String getVendors(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        this.setMenu(model, request);

        return ControllerConstants.Tiles.Product.vendorList;
    }

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/catalogue/vendor/create.html", method=RequestMethod.GET)
    public String createVendor(  Model model,  HttpServletRequest request, HttpServletResponse response) throws Exception {

        return displayVendor(null,model,request,response);
    }

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/catalogue/vendor/edit.html", method=RequestMethod.GET)
    public String editVendor(@RequestParam("id") long vendorId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        return displayVendor(vendorId,model,request,response);
    }

    private String displayVendor(Long vendorId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //display menu
        setMenu(model,request);

        List<Language> languages = languageService.getLanguages();

        com.salesmanager.shop.admin.model.catalog.Vendor vendor = new com.salesmanager.shop.admin.model.catalog.Vendor();
        List<VendorDescription> descriptions = new ArrayList<>();


        if( vendorId!=null && vendorId.longValue()!=0) {	//edit mode

            com.salesmanager.core.model.catalog.product.vendor.Vendor dbVendor =  vendorService.getById( vendorId );

            if(dbVendor==null) {
                return ControllerConstants.Tiles.Product.vendorList;
            }

            Set<VendorDescription> vendorDescriptions = dbVendor.getDescriptions();


            for(Language l : languages) {

                VendorDescription vendorDescription = null;
                if(vendorDescriptions!=null) {

                    for(VendorDescription desc : vendorDescriptions) {
                        String code = desc.getLanguage().getCode();
                        if(code.equals(l.getCode())) {
                            vendorDescription = desc;
                        }

                    }

                }

                if(vendorDescription==null) {
                    vendorDescription = new VendorDescription();
                    vendorDescription.setLanguage(l);
                }

                vendor.getDescriptions().add(vendorDescription);

            }

            vendor.setVendor( dbVendor );

            vendor.setCode(dbVendor.getCode());
            vendor.setOrder( dbVendor.getSortOrder() );

        } else {	// Create mode

            com.salesmanager.core.model.catalog.product.vendor.Vendor vendorTmp = new com.salesmanager.core.model.catalog.product.vendor.Vendor();
            vendor.setVendor( vendorTmp );

            for(Language l : languages) {// for each store language

                VendorDescription vendorDescription = new VendorDescription();
                vendorDescription.setLanguage(l);
                descriptions.add(  vendorDescription );
                vendor.setDescriptions(descriptions);

            }
        }

        model.addAttribute("languages",languages);
        model.addAttribute("vendor", vendor);

        return ControllerConstants.Tiles.Product.vendorDetails;
    }

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/catalogue/vendor/save.html", method=RequestMethod.POST)
    public String saveVendor(@Valid @ModelAttribute("vendor") com.salesmanager.shop.admin.model.catalog.Vendor vendor, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response, Locale locale) throws Exception {

        this.setMenu(model, request);
        List<Language> languages = languageService.getLanguages();

        if(vendor.getDescriptions()!=null && vendor.getDescriptions().size()>0) {

            for(VendorDescription description : vendor.getDescriptions()) {

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

        if (result.hasErrors()) {
            model.addAttribute("languages",languages);
            return ControllerConstants.Tiles.Product.vendorDetails;
        }

        com.salesmanager.core.model.catalog.product.vendor.Vendor newVendor = vendor.getVendor();

        Set<VendorDescription> descriptions = new HashSet<>();
        if(vendor.getDescriptions()!=null && vendor.getDescriptions().size()>0) {

            for(VendorDescription desc : vendor.getDescriptions()) {

                desc.setVendor(newVendor);
                descriptions.add(desc);
            }
        }
        newVendor.setDescriptions(descriptions );
        newVendor.setSortOrder( vendor.getOrder() );
        newVendor.setName(vendor.getName());
        newVendor.setCode(vendor.getCode());
        newVendor.setEmail(vendor.getEmail());
        newVendor.setPhone(vendor.getPhone());

         vendorService.saveOrUpdate(newVendor);

        model.addAttribute("vendor", vendor);
        model.addAttribute("languages",languages);
        model.addAttribute("success","success");

        return ControllerConstants.Tiles.Product.vendorDetails;

    }

    @SuppressWarnings("unchecked")
    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/catalogue/vendor/paging.html", method=RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> pageVendors(HttpServletRequest request, HttpServletResponse response) {

        AjaxResponse resp = new AjaxResponse();
        try {

            Language language = (Language)request.getAttribute("LANGUAGE");
            MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);

            List<com.salesmanager.core.model.catalog.product.vendor.Vendor> vendors = vendorService.list(store, language);


            for(com.salesmanager.core.model.catalog.product.vendor.Vendor vendor : vendors) {

                @SuppressWarnings("rawtypes")
                Map entry = new HashMap();
                entry.put("id", vendor.getId());

                VendorDescription description = vendor.getDescriptions().iterator().next();

                entry.put("name", description.getName());
                entry.put("code", vendor.getCode());
                entry.put("order", vendor.getSortOrder());
                entry.put("email", vendor.getEmail());
                entry.put("phone", vendor.getPhone());

                resp.addDataEntry(entry);

            }

            resp.setStatus(AjaxResponse.RESPONSE_STATUS_SUCCESS);

        } catch (Exception e) {
            LOGGER.error("Error while paging Vendors", e);
            resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
        }

        resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_SUCCESS);

        String returnString = resp.toJSONString();
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new ResponseEntity<String>(returnString,httpHeaders, HttpStatus.OK);

    }

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/catalogue/vendor/remove.html", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> deleteVendor(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        Long sid =  Long.valueOf(request.getParameter("id") );


        AjaxResponse resp = new AjaxResponse();
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        try{
            com.salesmanager.core.model.catalog.product.vendor.Vendor delVendor = vendorService.getById( sid  );
            if(delVendor==null) {
                resp.setStatusMessage(messages.getMessage("message.unauthorized", locale));
                resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
                String returnString = resp.toJSONString();
                return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
            }

            int count = vendorService.getCountVendorAttachedProducts( delVendor ).intValue();
            LOGGER.error("Vendor Product Count.", count);
            //IF already attached to products it can't be deleted
            if ( count > 0 ){
                resp.setStatusMessage(messages.getMessage("message.product.association", locale));
                resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
                String returnString = resp.toJSONString();
                return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
            }

            vendorService.delete( delVendor );
//            vendorService.deleteVendor(delVendor);

            resp.setStatusMessage(messages.getMessage("message.success", locale));
            resp.setStatus(AjaxResponse.RESPONSE_OPERATION_COMPLETED);

        } catch (Exception e) {

            resp.setStatus(AjaxResponse.RESPONSE_STATUS_FAIURE);
            LOGGER.error("Cannot delete Vendor.", e);
        }

        String returnString = resp.toJSONString();
        return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);

    }

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/vendor/checkCode.html", method=RequestMethod.POST)
    public @ResponseBody ResponseEntity<String> checkCode(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        String code = request.getParameter("code");
        String id = request.getParameter("id");

        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        AjaxResponse resp = new AjaxResponse();

        if(StringUtils.isBlank(code)) {
            resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
            String returnString = resp.toJSONString();
            return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
        }

        try {

            com.salesmanager.core.model.catalog.product.vendor.Vendor vendor = vendorService.getByCode(code);

            if(vendor!=null && StringUtils.isBlank(id)) {
                resp.setStatus(AjaxResponse.CODE_ALREADY_EXIST);
                String returnString = resp.toJSONString();
                return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);
            }


            if(vendor!=null && !StringUtils.isBlank(id)) {
                try {
                    Long lid = Long.parseLong(id);

                    if(vendor.getCode().equals(code) && vendor.getId().longValue()==lid) {
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
            LOGGER.error("Error while getting vendor", e);
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
        activeMenus.put("vendor-list", "vendor-list");

        @SuppressWarnings("unchecked")
        Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");

        Menu currentMenu = (Menu)menus.get("catalogue");
        model.addAttribute("currentMenu",currentMenu);
        model.addAttribute("activeMenus",activeMenus);
    }


}
