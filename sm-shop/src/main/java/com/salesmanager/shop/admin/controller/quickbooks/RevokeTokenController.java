package com.salesmanager.shop.admin.controller.quickbooks;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.data.PlatformResponse;
import com.intuit.oauth2.exception.ConnectionException;
import com.salesmanager.core.business.services.accounting.quickbooks.factory.OAuth2PlatformClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
public class RevokeTokenController {

    @Autowired
    OAuth2PlatformClientFactory factory;

    private static final Logger logger = Logger.getLogger(RevokeTokenController.class);

    /**
     * Call to revoke tokens
     *
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/revokeToken")
    public String revokeToken(HttpSession session) {

        String failureMsg="Failed";

        try {

            OAuth2PlatformClient client  = factory.getOAuth2PlatformClient();
            String refreshToken = (String)session.getAttribute("refresh_token");
            PlatformResponse response  = client.revokeToken(refreshToken);
            logger.info("raw result for revoke token request= " + response.getStatus());
            return new JSONObject().put("response", "Revoke successful").toString();
        }
        catch (ConnectionException ex) {
            logger.error("ConnectionException while calling refreshToken ", ex);
            logger.error("ErrorCode: " + ex.getErrorCode());
            logger.error("StackTrace: " + Arrays.toString(ex.getStackTrace()));
            return new JSONObject().put("response", ex.getMessage()).toString();
        }
        catch (Exception ex) {
            logger.error("Exception while calling revokeToken ", ex);
            return new JSONObject().put("response",failureMsg).toString();
        }

    }

}