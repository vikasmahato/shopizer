package com.salesmanager.core.business.services.accounting.service;

import com.intuit.oauth2.data.BearerTokenResponse;
import com.salesmanager.core.business.exception.ServiceException;

public interface AccountingService {
    void saveQuickbooksToken(BearerTokenResponse bearerTokenResponse) throws ServiceException;
}
