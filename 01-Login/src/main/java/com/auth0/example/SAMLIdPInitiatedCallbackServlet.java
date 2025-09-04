package com.auth0.example;

import com.auth0.AuthenticationController;
import com.auth0.IdentityVerificationException;
import com.auth0.SessionUtils;
import com.auth0.Tokens;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * The Servlet endpoint used as the callback handler in the SAML IdP Initiated flow.
 */
@WebServlet(urlPatterns = {"/saml-callback"})
public class SAMLIdPInitiatedCallbackServlet extends HttpServlet {

    private AuthenticationController authenticationController;


    /**
     * Initialize this servlet with required configuration.
     * <p>
     * Parameters needed on the Local Servlet scope:
     * <ul>
     * <li>'com.auth0.redirect_on_success': where to redirect after a successful authentication.</li>
     * <li>'com.auth0.redirect_on_error': where to redirect after a failed authentication.</li>
     * </ul>
     * Parameters needed on the Local/Global Servlet scope:
     * <ul>
     * <li>'com.auth0.domain': the Auth0 domain.</li>
     * <li>'com.auth0.client_id': the Auth0 Client id.</li>
     * <li>'com.auth0.client_secret': the Auth0 Client secret.</li>
     * </ul>
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        try {
            authenticationController = AuthenticationControllerProvider.getInstance(config);
        } catch (UnsupportedEncodingException e) {
            throw new ServletException("Couldn't create the AuthenticationController instance. Check the configuration.", e);
        }
    }

    /**
     * Process a call to initiate SAML IdP authentication with a GET HTTP method.
     * Obtains the connection parameter and builds the authorize URL with it.
     *
     * @param req the received request with the connection parameter.
     * @param res the response to send back to the server.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        // Extract connection parameter from query string
        String connection = req.getParameter("connection");
        
        // Build redirect URI same as LoginServlet
        String redirectUri = req.getScheme() + "://" + req.getServerName();
        if ((req.getScheme().equals("http") && req.getServerPort() != 80) || (req.getScheme().equals("https") && req.getServerPort() != 443)) {
            redirectUri += ":" + req.getServerPort();
        }
        redirectUri += "/callback";

        // Build authorize URL with connection parameter
        String authorizeUrl = authenticationController.buildAuthorizeUrl(req, res, redirectUri)
                .withAudience("https://sample-api.com/")
                .withScope("openid email")
                .withParameter("connection", connection)
                .build();
        res.sendRedirect(authorizeUrl);
    }
}
