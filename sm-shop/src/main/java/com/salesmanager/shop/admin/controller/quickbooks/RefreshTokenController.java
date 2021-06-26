package com.salesmanager.shop.admin.controller.quickbooks;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.exception.OAuthException;
import com.salesmanager.core.business.services.accounting.quickbooks.factory.OAuth2PlatformClientFactory;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
public class RefreshTokenController {

    @Autowired
    OAuth2PlatformClientFactory factory;

    private static final Logger logger = Logger.getLogger(RefreshTokenController.class);

    /**
     * Call to refresh tokens
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/refreshToken")
    public String refreshToken(HttpSession session) {

        String failureMsg="Failed";

        try {

            OAuth2PlatformClient client  = factory.getOAuth2PlatformClient();
            String refreshToken = (String)session.getAttribute("refresh_token");
            BearerTokenResponse bearerTokenResponse = client.refreshToken(refreshToken);
            session.setAttribute("access_token", bearerTokenResponse.getAccessToken());
            session.setAttribute("refresh_token", bearerTokenResponse.getRefreshToken());
            String jsonString = new JSONObject()
                    .put("access_token", bearerTokenResponse.getAccessToken())
                    .put("refresh_token", bearerTokenResponse.getRefreshToken()).toString();
            return jsonString;
        }
        catch (OAuthException ex) {
            logger.error("OAuthException while calling refreshToken ", ex);
            logger.error("ErrorCode: " + ex.getErrorCode());
            logger.error("StackTrace: " + Arrays.toString(ex.getStackTrace()));
            return new JSONObject().put("response", ex.getMessage()).toString();
        }
        catch (Exception ex) {
            logger.error("Exception while calling refreshToken ", ex);
            return new JSONObject().put("response", failureMsg).toString();
        }

    }

}