package com.salesmanager.core.business.services.accounting.service;

import com.github.javaparser.utils.Log;
import com.intuit.ipp.util.Logger;
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

    private static final org.slf4j.Logger LOG = Logger.getLogger();
    QuickbooksRepository quickbooksRepository;

    @Inject
    public AccountingServiceImpl(QuickbooksRepository quickbooksRepository) {
        super(quickbooksRepository);
        this.quickbooksRepository = quickbooksRepository;
    }

    @Override
    @Transactional
    public void saveQuickbooksToken(BearerTokenResponse bearerTokenResponse, String realmId) throws ServiceException {
        LOG.error(bearerTokenResponse.getAccessToken());
        LOG.error("Access token length"+bearerTokenResponse.getAccessToken().length());
        QuickbooksToken quickbooksToken = new QuickbooksToken(bearerTokenResponse.getAccessToken(), bearerTokenResponse.getRefreshToken());
        quickbooksToken.setRealmId(realmId);
        super.save(quickbooksToken);
    }

    @Override
    public String getRealmId() {
        return quickbooksRepository.getRealmId();
    }

    @Override
    public String getAccessToken() {
        return quickbooksRepository.getLastAccessToken();
    }
}
