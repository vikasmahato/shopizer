package com.salesmanager.shop.admin.controller.quickbooks;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.accounting.quickbooks.factory.OAuth2PlatformClientFactory;
import com.salesmanager.core.business.services.accounting.service.AccountingService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.data.UserInfoResponse;
import com.intuit.oauth2.exception.OAuthException;


/**
 * @author dderose
 *
 */
@Controller
public class CallbackController {

    @Inject
    OAuth2PlatformClientFactory factory;

    @Inject
    AccountingService accountingService;

    private static final Logger logger = Logger.getLogger(CallbackController.class);

    /**
     *  This is the redirect handler you configure in your app on developer.intuit.com
     *  The Authorization code has a short lifetime.
     *  Hence Unless a user action is quick and mandatory, proceed to exchange the Authorization Code for
     *  BearerToken
     *
     * @param authCode
     * @param state
     * @param realmId
     * @param session
     * @return
     */
    @RequestMapping("/admin/quickbooks/oauth2redirect")
    public String callBackFromOAuth(@RequestParam("code") String authCode, @RequestParam("state") String state, @RequestParam(value = "realmId", required = false) String realmId, HttpSession session) {
        logger.info("inside oauth2redirect of sample"  );
        try {
            String csrfToken = (String) session.getAttribute("csrfToken");
            if (csrfToken.equals(state)) {
                session.setAttribute("realmId", realmId);
                session.setAttribute("auth_code", authCode);

                OAuth2PlatformClient client  = factory.getOAuth2PlatformClient();
                String redirectUri = factory.getPropertyValue("OAuth2AppRedirectUri");
                logger.info("inside oauth2redirect of sample -- redirectUri " + redirectUri  );
                BearerTokenResponse bearerTokenResponse = client.retrieveBearerTokens(authCode, redirectUri);

                session.setAttribute("access_token", bearerTokenResponse.getAccessToken());
                session.setAttribute("refresh_token", bearerTokenResponse.getRefreshToken());

	            /*
	                Update your Data store here with user's AccessToken and RefreshToken along with the realmId
                */

                accountingService.saveQuickbooksToken(bearerTokenResponse);

                /*
	                However, in case of OpenIdConnect, when you request OpenIdScopes during authorization,
	                you will also receive IDToken from Intuit.
	                You first need to validate that the IDToken actually came from Intuit.
	             */

               /* if (StringUtils.isNotBlank(bearerTokenResponse.getIdToken())) {
                    try {
                        if(client.validateIDToken(bearerTokenResponse.getIdToken())) {
                            logger.info("IdToken is Valid");
                            //get user info
                            saveUserInfo(bearerTokenResponse.getAccessToken(), session, client);
                        }
                    } catch (OpenIdException e) {
                        logger.error("Exception validating id token ", e);
                        logger.error("ErrorCode: " + e.getErrorCode());
                        logger.error("StackTrace: " + Arrays.toString(e.getStackTrace()));
                    }
                }
*/
                return "admin-quickbooks-success";
            }
            logger.info("csrf token mismatch " );
        } catch (OAuthException | ServiceException e) {
            logger.error("Exception in callback handler ", e);
        }
        return null;
    }


    private void saveUserInfo(String accessToken, HttpSession session, OAuth2PlatformClient client) {
        //Ideally you would fetch the realmId and the accessToken from the data store based on the user account here.

        try {
            UserInfoResponse response = client.getUserInfo(accessToken);

            session.setAttribute("sub", response.getSub());
            session.setAttribute("givenName", response.getGivenName());
            session.setAttribute("email", response.getEmail());

        }
        catch (Exception ex) {
            logger.error("Exception while retrieving user info ", ex);
        }
    }

}