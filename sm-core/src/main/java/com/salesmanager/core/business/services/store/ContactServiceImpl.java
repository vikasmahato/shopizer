package com.salesmanager.core.business.services.store;

import com.salesmanager.core.business.repositories.store.ContactRepository;
import com.salesmanager.core.model.store.AskForPrice;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ContactServiceImpl implements ContactService{

    @Inject
    ContactRepository contactRepository;

    @Override
    public AskForPrice saveContactForm(AskForPrice askForPrice) {
        return contactRepository.save(askForPrice);
    }
}
