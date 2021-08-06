package com.salesmanager.shop.store.api.v1.product;

import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.salesmanager.core.business.services.catalog.product.brand.BrandService;
import com.salesmanager.core.model.catalog.product.brand.Brand;
import com.salesmanager.shop.model.catalog.brand.PersistableBrand;
import com.salesmanager.shop.model.catalog.brand.ReadableBrand;
import com.salesmanager.shop.model.catalog.brand.ReadableBrandList;
import com.salesmanager.shop.store.controller.brand.facade.BrandFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.entity.EntityExists;
import com.salesmanager.shop.model.entity.ListCriteria;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

/**
 * brand management Collection, brand ...
 *
 * @author c.samson
 */
@Controller
@RequestMapping("/api/v1")
@Api(tags = { "brand / Brand management resource (brand / Brand Management Api)" })
@SwaggerDefinition(tags = {
		@Tag(name = "brand / Brand Management Api", description = "Edit brand / Brand") })
public class ProductBrandApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductBrandApi.class);

	@Inject
	private BrandService brandService;

	@Inject
	private BrandFacade brandFacade;

	/**
	 * Method for creating a brand
	 *
	 * @param brand
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/private/brand", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public PersistableBrand create(@Valid @RequestBody PersistableBrand brand,
										@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language, HttpServletResponse response) {

		try {
			brandFacade.saveOrUpdatebrand(brand, merchantStore, language);

			return brand;

		} catch (Exception e) {
			LOGGER.error("Error while creating brand", e);
			try {
				response.sendError(503, "Error while creating brand " + e.getMessage());
			} catch (Exception ignore) {
			}

			return null;
		}
	}

	@RequestMapping(value = "/brands/{id}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public ReadableBrand get(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
							 @ApiIgnore Language language, HttpServletResponse response) {

		try {
			ReadableBrand brand = brandFacade.getbrand(id, merchantStore, language);

			if (brand == null) {
				response.sendError(404, "No brand found for ID : " + id);
			}

			return brand;

		} catch (Exception e) {
			LOGGER.error("Error while getting brand", e);
			try {
				response.sendError(503, "Error while getting brand " + e.getMessage());
			} catch (Exception ignore) {
			}
		}

		return null;
	}

	
	@RequestMapping(value = "/private/brands/", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	@ApiOperation(httpMethod = "GET", value = "List brands by store", notes = "This request supports paging or not. Paging supports page number and request count", response = ReadableBrandList.class)
	public ReadableBrandList listByStore(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {

		ListCriteria listCriteria = new ListCriteria();
		listCriteria.setName(name);
		return brandFacade.listByStore(merchantStore, language, listCriteria, page, count);
	}
	
	
	@RequestMapping(value = "/brands/", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	@ApiOperation(httpMethod = "GET", value = "List brands by store", notes = "This request supports paging or not. Paging supports page number and request count", response = ReadableBrandList.class)
	public ReadableBrandList list(@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {

		ListCriteria listCriteria = new ListCriteria();
		listCriteria.setName(name);
		return brandFacade.getAllbrands(merchantStore, language, listCriteria, page, count);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/brand/unique" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	@ApiOperation(httpMethod = "GET", value = "Check if brand code already exists", notes = "", response = EntityExists.class)
	public ResponseEntity<EntityExists> exists(@RequestParam(value = "code") String code,
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

		boolean exists = brandFacade.brandExist(merchantStore, code);
		return new ResponseEntity<EntityExists>(new EntityExists(exists), HttpStatus.OK);

	}

	@RequestMapping(value = "/private/brand/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public void update(@PathVariable Long id,
			@Valid @RequestBody PersistableBrand brand, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) {

		try {
			brand.setId(id);
			brandFacade.saveOrUpdatebrand(brand, merchantStore, language);
		} catch (Exception e) {
			LOGGER.error("Error while creating brand", e);
			try {
				response.sendError(503, "Error while creating brand " + e.getMessage());
			} catch (Exception ignore) {
			}
		}
	}

	@RequestMapping(value = "/brand/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public void delete(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
			HttpServletResponse response) {

		try {
			Brand brand = brandService.getById(id);

			if (brand != null) {
				brandFacade.deletebrand(brand, merchantStore, language);
			} else {
				response.sendError(404, "No brand found for ID : " + id);
			}

		} catch (Exception e) {
			LOGGER.error("Error while deleting brand id " + id, e);
			try {
				response.sendError(503, "Error while deleting brand id " + id + " - " + e.getMessage());
			} catch (Exception ignore) {
			}
		}
	}

	@RequestMapping(value = "/category/{id}/brands", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Get all brands for all items in a given category", notes = "", produces = "application/json", response = List.class)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public List<ReadableBrand> list(@PathVariable final Long id, // category
																					// id
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language, HttpServletResponse response)
			throws Exception {

		return brandFacade.getByProductInCategory(merchantStore, language, id);

	}

}
