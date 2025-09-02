package com.auth0.example;

import com.auth0.SessionUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/portal/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String accessToken = (String) SessionUtils.get(req, "accessToken");
        final String idToken = (String) SessionUtils.get(req, "idToken");
        
        Map<String, Object> userProfile = new HashMap<>();
        String userId = "Anonymous";
        
        if (idToken != null) {
            try {
                // Decode the ID token without verification (Testing purpose)
                DecodedJWT jwt = JWT.decode(idToken);
                
                // Extract user profile information from the token
                userId = jwt.getSubject(); // 'sub' claim
                userProfile.put("sub", jwt.getSubject());
                userProfile.put("email", jwt.getClaim("email").asString());
                userProfile.put("email_verified", jwt.getClaim("email_verified").asBoolean());
                
                // Set the user profile for the JSP
                req.setAttribute("userProfile", userProfile);
                req.setAttribute("userId", userId);
                
            } catch (Exception e) {
                // If token decoding fails, fall back to showing the token itself
                req.setAttribute("userId", idToken);
                req.setAttribute("tokenError", "Failed to decode ID token: " + e.getMessage());
            }
        } else if (accessToken != null) {
            req.setAttribute("userId", accessToken);
        } else {
            req.setAttribute("userId", userId);
        }
        
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, res);
    }
}
