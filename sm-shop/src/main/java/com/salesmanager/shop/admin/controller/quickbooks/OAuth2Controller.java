package com.salesmanager.shop.admin.controller.quickbooks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.salesmanager.core.business.services.accounting.quickbooks.factory.OAuth2PlatformClientFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.config.Scope;
import com.intuit.oauth2.exception.InvalidRequestException;

@Controller
public class OAuth2Controller {
    private static final Logger logger = Logger.getLogger(OAuth2Controller.class);

    @Inject
    OAuth2PlatformClientFactory factory;

    @RequestMapping("/admin/quickbooks/connected")
    public String connected() {
        return "connected";
    }

    /**
     * Controller mapping for connectToQuickbooks button
     * @return
     */
    @RequestMapping("/admin/quickbooks/connectToQuickbooks")
    public View connectToQuickbooks(HttpSession session) {
        logger.info("inside connectToQuickbooks ");
        OAuth2Config oauth2Config = factory.getOAuth2Config();

        String redirectUri = factory.getPropertyValue("OAuth2AppRedirectUri");

        String csrf = oauth2Config.generateCSRFToken();
        session.setAttribute("csrfToken", csrf);
        try {
            List<Scope> scopes = new ArrayList<>();
            scopes.add(Scope.Accounting);
            return new RedirectView(oauth2Config.prepareUrl(scopes, redirectUri, csrf), true, true, false);
        } catch (InvalidRequestException e) {
            logger.error("Exception calling connectToQuickbooks ", e);
            logger.error("ErrorCode: " + e.getErrorCode());
            logger.error("StackTrace: " + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    /**
     * Controller mapping for signInWithIntuit button
     * @return
     */
    @RequestMapping("/admin/quickbooks/signInWithIntuit")
    public View signInWithIntuit(HttpSession session) {
        logger.info("inside signInWithIntuit ");
        OAuth2Config oauth2Config = factory.getOAuth2Config();

        String csrf = oauth2Config.generateCSRFToken();
        session.setAttribute("csrfToken", csrf);

        String redirectUri = factory.getPropertyValue("OAuth2AppRedirectUri");
        try {
            List<Scope> scopes = new ArrayList<>();
            scopes.add(Scope.OpenIdAll);
            return new RedirectView(oauth2Config.prepareUrl(scopes, redirectUri, csrf), true, true, false);
        } catch (InvalidRequestException e) {
            logger.error("Exception calling signInWithIntuit ", e);
            logger.error("ErrorCode: " + e.getErrorCode());
            logger.error("StackTrace: " + Arrays.toString(e.getStackTrace()));
        }
        return null;

    }

    /**
     * Controller mapping for getAppNow button
     * @return
     */
    @RequestMapping("/admin/quickbooks/getAppNow")
    public View getAppNow(HttpSession session) {
        logger.info("inside getAppNow "  );
        OAuth2Config oauth2Config = factory.getOAuth2Config();

        String csrf = oauth2Config.generateCSRFToken();
        session.setAttribute("csrfToken", csrf);

        String redirectUri = factory.getPropertyValue("OAuth2AppRedirectUri");
        try {
            List<Scope> scopes = new ArrayList<Scope>();
            scopes.add(Scope.OpenIdAll);
            scopes.add(Scope.Accounting);
            return new RedirectView(oauth2Config.prepareUrl(scopes, redirectUri, csrf), true, true, false);
        } catch (InvalidRequestException e) {
            logger.error("Exception calling getAppNow ", e);
            logger.error("ErrorCode: " + e.getErrorCode());
            logger.error("StackTrace: " + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

}
