package com.salesmanager.core.business.repositories.quickbooks;

import com.salesmanager.core.model.quickbooks.QuickbooksToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuickbooksRepository extends JpaRepository<QuickbooksToken, Long> {
}
