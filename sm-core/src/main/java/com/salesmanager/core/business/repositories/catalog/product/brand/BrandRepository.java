package com.salesmanager.core.business.repositories.catalog.product.brand;

import java.util.List;

import com.salesmanager.core.model.catalog.product.brand.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long> {

	@Query("select count(distinct p) from Product as p where p.brand.id=?1")
	Long countByProduct(Long brandId);
	
	@Query("select m from Brand m left join fetch m.descriptions md join fetch m.merchantStore ms where ms.id=?1 and md.language.id=?2")
	List<Brand> findByStoreAndLanguage(Integer storeId, Integer languageId);
	
	@Query("select m from Brand m left join fetch  m.descriptions md join fetch m.merchantStore ms where m.id=?1")
	Brand findOne(Long id);
	
	@Query("select m from Brand m left join fetch m.descriptions md join fetch m.merchantStore ms where ms.id=?1")
	List<Brand> findByStore(Integer storeId);
	
    @Query("select m from Brand m join fetch m.descriptions md join fetch m.merchantStore ms join fetch md.language mdl where ms.id=?1 and mdl.id=?2 and (?3 is null or md.name like %?3%)")
	//@Query("select m from brand m join fetch m.descriptions md join fetch m.merchantStore ms join fetch md.language mdl where ms.id=?1 and mdl.id=?2")
	//@Query("select m from brand m left join m.descriptions md join fetch m.merchantStore ms where ms.id=?1")
	List<Brand> findByStore(Integer storeId, Integer languageId, String name);
	

	@Query("select distinct brand from Product as p join p.brand brand join brand.descriptions md join p.categories categs where categs.id in (?1) and md.language.id=?2")
	List<Brand> findByCategoriesAndLanguage(List<Long> categoryIds, Integer languageId);
	
	@Query("select m from Brand m left join m.descriptions md join fetch m.merchantStore ms where m.code=?1 and ms.id=?2")
	Brand findByCodeAndMerchandStore(String code, Integer storeId);
	
	@Query("select count(distinct m) from Brand as m where m.merchantStore.id=?1")
	int count(Integer storeId);
	
	@Query(value="select distinct brand from Product as p "
			+ "join p.brand brand "
			+ "left join brand.descriptions pmd "
			+ "join fetch brand.merchantStore pms "
			+ "join p.categories pc "
			+ "where pms.id = ?1 "
			+ "and pc.id IN (select c.id from Category c where c.lineage like %?2% and pmd.language.id = ?3)")
	List<Brand> findByProductInCategoryId(Integer storeId, String lineage, Integer languageId);
}
