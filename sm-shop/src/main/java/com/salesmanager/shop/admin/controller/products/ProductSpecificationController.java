package com.salesmanager.shop.admin.controller.products;

import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.category.CategorySpecificationService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.specification.ProductSpecificationService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategorySpecification;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.specification.ProductSpecificationVariant;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.admin.model.web.Menu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
public class ProductSpecificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductSpecificationController.class);

    @Inject
    LanguageService languageService;

    @Inject
    CategoryService categoryService;

    @Inject
    CategorySpecificationService categorySpecificationService;

    @Inject
    ProductSpecificationService specificationService;

    @Inject
    private ProductService productService;

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/product/displayProductToSpecifications.html", method= RequestMethod.GET)
    public String displayProductSpecifications(@RequestParam("id") long productId, Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        setMenu(model,request);
        Language language = (Language)request.getAttribute("LANGUAGE");
        Product product = productService.getById(productId);

        if(product==null) {
            return "redirect:/admin/products/products.html";
        }

        ProductSpecificationVariant productSpecification = new ProductSpecificationVariant();
        Map<Long, ArrayList<String>> valueMap = new TreeMap<>();
        com.salesmanager.shop.admin.model.catalog.Category adminCategory = new com.salesmanager.shop.admin.model.catalog.Category();

        for(Category category : product.getCategories()) {
            List<CategorySpecification> specifications = new ArrayList<CategorySpecification>();
            for(CategorySpecification specf : category.getSpecifications()) {
                ArrayList<String> value = new ArrayList<>();
                List<ProductSpecificationVariant> variant = specificationService.getBySpecificationId(product.getId(), specf.getId());
                if (variant.size() == 0)
                    value.add("");
                else if (variant.size() == 1)
                    value.add(variant.get(0).getValue());
//                    value.set(Math.toIntExact(variant.get(0).getId()), variant.get(0).getValue());
                else
                    for (ProductSpecificationVariant var: variant)
                        value.add(var.getValue());
                valueMap.put(specf.getId(), value);
                specifications.add(specf);
            }
            /**
             ValueMap is a TreeMap, so it is sorted in natural order of its keys.
             We will have to sort specifications list in the same order as well.
             Since keys in ValueMap are specification id, therefore, we must also
             sort specifications list in order of id
            */
            specifications.sort(Comparator.comparing(CategorySpecification::getId));
            adminCategory.setSpecifications(specifications);
            adminCategory.setCategory(category);
        }
        productSpecification.setValueMap(valueMap);

        model.addAttribute("category", adminCategory);
        model.addAttribute("product_specification", productSpecification);
        model.addAttribute("product", product);
        return "catalogue-product-specifications";

    }

    @PreAuthorize("hasRole('PRODUCTS')")
    @RequestMapping(value="/admin/products/addProductSpecifications.html", method=RequestMethod.POST)
    public String saveSpecificationValue(@Valid @ModelAttribute("product_specification") ProductSpecificationVariant specification, BindingResult result, Model model, HttpServletRequest request, Locale locale) throws Exception {

        setMenu(model,request);

        Product product = productService.getById(Long.parseLong(request.getParameterMap().get("productId")[0]));
        Category category = categoryService.getById(Long.parseLong(request.getParameterMap().get("categoryId")[0]));
        model.addAttribute("product",product);
        Language language = (Language)request.getAttribute("LANGUAGE");
        Map<Long, ArrayList<String>> valueMap = specification.getValueMap();
        for (Long specification_id : valueMap.keySet())
        {
            List<ProductSpecificationVariant> variants= specificationService.getBySpecificationId(product.getId(), specification_id);
            for (ProductSpecificationVariant var : variants)
                if(!valueMap.get(specification_id).contains(var.getValue()))
                    specificationService.delete(var);

            for (String value : valueMap.get(specification_id))
            {
                ProductSpecificationVariant variant = specificationService.getBySpecificationIdAndValue(product.getId(), specification_id, value);
                if (variant == null)
                {
                    ProductSpecificationVariant specificationVariant = new ProductSpecificationVariant();
                    specificationVariant.setProduct(product);
                    specificationVariant.setCategory(category);
                    specificationVariant.setSpecification(categorySpecificationService.getById(specification_id));
                    specificationVariant.setLanguage(language);
                    specificationVariant.setValue(value);
                    specificationService.save(specificationVariant);
                }

            }
//            ArrayList<String> values = valueMap.get(specification_id);
//            Collections.sort(values);
//            List<ProductSpecificationVariant> variant = specificationService.getBySpecificationId(specification_id);

//            if (variant.size() == 0)
//            {
//                specification.setProduct(product);
//                specification.setCategory(category);
//                specification.setSpecification(categorySpecificationService.getById(specification_id));
//                specification.setLanguage(language);
////                variant.add(specification);
//            }
//            for (String value : values)
//            {
////                if(variant.size() == 1)
////                    specification.setId(variant.get(0).getId());
//                specification.setValue(value);
//                specificationService.saveOrUpdate(specification);
//            }
        }

        model.addAttribute("success","success");
        return "redirect:/admin/product/displayProductToSpecifications.html?id="+product.getId();

    }


    private String displaySpecifications()
    {
        return "catalogue-product-specifications";
    }

    private void setMenu(Model model, HttpServletRequest request) throws Exception {

        //display menu
        Map<String,String> activeMenus = new HashMap<String,String>();
        activeMenus.put("catalogue", "catalogue");
        activeMenus.put("catalogue-products", "catalogue-products");

        @SuppressWarnings("unchecked")
        Map<String, Menu> menus = (Map<String, Menu>)request.getAttribute("MENUMAP");

        Menu currentMenu = (Menu)menus.get("catalogue");
        model.addAttribute("currentMenu",currentMenu);
        model.addAttribute("activeMenus",activeMenus);
        //

    }
}
