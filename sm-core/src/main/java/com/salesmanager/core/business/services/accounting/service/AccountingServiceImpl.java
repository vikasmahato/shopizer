package com.salesmanager.core.business.services.accounting.service;

import com.intuit.oauth2.data.BearerTokenResponse;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.quickbooks.QuickbooksRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.quickbooks.QuickbooksToken;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.transaction.Transactional;

@Service("accountingService")
public class AccountingServiceImpl extends SalesManagerEntityServiceImpl<Long, QuickbooksToken> implements AccountingService{

    QuickbooksRepository quickbooksRepository;

    @Inject
    public AccountingServiceImpl(QuickbooksRepository quickbooksRepository) {
        super(quickbooksRepository);
        this.quickbooksRepository = quickbooksRepository;
    }

    @Override
    @Transactional
    public void saveQuickbooksToken(BearerTokenResponse bearerTokenResponse) throws ServiceException {
        QuickbooksToken quickbooksToken = new QuickbooksToken(bearerTokenResponse.getAccessToken(), bearerTokenResponse.getRefreshToken());
        super.save(quickbooksToken);
    }
}
