package com.salesmanager.shop.admin.controller.categories;

import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.category.CategorySpecificationService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.utils.ajax.AjaxPageableResponse;
import com.salesmanager.core.business.utils.ajax.AjaxResponse;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryDescription;
import com.salesmanager.core.model.catalog.category.CategorySpecification;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.*;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.model.web.Menu;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.utils.CategoryUtils;
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
public class CategorySpecificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategorySpecificationController.class);

    @Inject
    LanguageService languageService;

    @Inject
    CategoryService categoryService;

    @Inject
    CategorySpecificationService categorySpecificationService;

    @Inject
    private ProductService productService;

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/category/specifications/list.html", method= RequestMethod.GET)
    public String displayCategorySpecifications(@RequestParam("id") long categoryId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {


        setMenu(model,request);
        MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);

        Category category = categoryService.getById(categoryId);

        if(category==null) {
            return "redirect:/admin/categories/categories.html";
        }

        if(category.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
            return "redirect:/admin/categories/categories.html";
        }

        model.addAttribute("category",category);
        return "catalogue-categories-specifications";

    }

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/category/editSpecification.html", method=RequestMethod.GET)
    public String displaySpecificationEdit(@RequestParam("categoryId") Long categoryId, @RequestParam("id") Long id, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return displaySpecification(categoryId, id,model,request,response);

    }

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/category/createSpecification.html", method=RequestMethod.GET)
    public String displaySpecificationCreate(@RequestParam("categoryId") Long categoryId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return displaySpecification(categoryId, null,model,request,response);

    }

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/category/specifications/save.html", method=RequestMethod.POST)
    public String saveSpecification(@Valid @ModelAttribute("category_specification") CategorySpecification specification, BindingResult result, Model model, HttpServletRequest request, Locale locale) throws Exception {

        setMenu(model,request);

        Category category = categoryService.getById(specification.getCategory().getId());
        model.addAttribute("category",category);
        Language language = (Language)request.getAttribute("LANGUAGE");

        CategorySpecification dbEntity = specification;

        if(specification.getId() != null && specification.getId() >0) { //edit entry

            //get from DB
            dbEntity = categorySpecificationService.getById(specification.getId());

            if(dbEntity==null) {
                return "redirect:/admin/category/specifications/list.html";
            }
        }

        specification.setCategory(category);
        specification.setLanguage(language);
        model.addAttribute("specification",specification);
        categorySpecificationService.saveOrUpdate(specification);
        model.addAttribute("success","success");
            return "admin-category-specification-details";

    }

    private String displaySpecification(Long categoryId, Long id, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {


        //display menu
        setMenu(model,request);


        MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);
        Language language = (Language)request.getAttribute("LANGUAGE");

        //get product
        Category category =  categoryService.getById(categoryId);
        if(category.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
            return "redirect:/admin/categories/categories.html";
        }

        List<Language> languages = store.getLanguages();

        CategorySpecification specification = null;

        if(id!=null && id.intValue()!=0) {//edit mode

            specification = categorySpecificationService.getById(id);

        } else {

            specification = new CategorySpecification();
            specification.setCategory(category);
        }

        model.addAttribute("category_specification",specification);
        model.addAttribute("category",category);
        return "admin-category-specification-details";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/specifications/page.html", method=RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> pageAttributes(HttpServletRequest request, HttpServletResponse response) {

        //String attribute = request.getParameter("attribute");
        String sCaegoryId = request.getParameter("categoryId");


        AjaxResponse resp = new AjaxResponse();
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

        Long categoryId;
        Category category = null;

        try {
            categoryId = Long.parseLong(sCaegoryId);
        } catch (Exception e) {
            resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_FAIURE);
            resp.setErrorString("Category id is not valid");
            String returnString = resp.toJSONString();
            return new ResponseEntity<String>(returnString,httpHeaders, HttpStatus.OK);
        }


        try {


            category = categoryService.getById(categoryId);



            Language language = (Language)request.getAttribute("LANGUAGE");
            MerchantStore store = (MerchantStore)request.getAttribute(Constants.ADMIN_STORE);

            //List<ProductAttribute> attributes = productAttributeService.getByProductId(store, product, language);

            for(CategorySpecification specificataion : category.getSpecifications()) {

                Map entry = new HashMap();
                entry.put("specificationId", specificataion.getId());

                entry.put("specification", specificataion.getSpecification());
                entry.put("filter", specificataion.getFilter());
                entry.put("variant", specificataion.getVariant());

                resp.addDataEntry(entry);
            }

            resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_SUCCESS);

        } catch (Exception e) {
            LOGGER.error("Error while paging specifications", e);
            resp.setStatus(AjaxPageableResponse.RESPONSE_STATUS_FAIURE);
            resp.setErrorMessage(e);
        }

        String returnString = resp.toJSONString();
        return new ResponseEntity<String>(returnString,httpHeaders,HttpStatus.OK);


    }

    private void setMenu(Model model, HttpServletRequest request) {
        //display menu
        Map<String,String> activeMenus = new HashMap<>();
        activeMenus.put("catalogue", "catalogue");
        activeMenus.put("catalogue-categories", "catalogue-categories");
        @SuppressWarnings("unchecked")
        Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");
        Menu currentMenu = menus.get("catalogue");
        model.addAttribute("currentMenu",currentMenu);
        model.addAttribute("activeMenus",activeMenus);
        //
    }


}
