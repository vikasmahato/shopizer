package com.salesmanager.core.business.repositories.store;

import com.salesmanager.core.model.store.AskForPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<AskForPrice, Long> {

}
