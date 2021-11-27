package com.salesmanager.core.business.repositories.quickbooks;

import com.salesmanager.core.model.quickbooks.QuickbooksToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuickbooksRepository extends JpaRepository<QuickbooksToken, Long> {

    @Query(value = "SELECT ACCESSTOKEN FROM SALESMANAGER.QUICKBOOKS_TOKENS ORDER BY ID DESC LIMIT 1", nativeQuery = true)
    String getLastAccessToken();

    @Query(value = "SELECT QB_REALM FROM SALESMANAGER.QUICKBOOKS_TOKENS ORDER BY ID DESC LIMIT 1", nativeQuery = true)
    String getRealmId();
}
