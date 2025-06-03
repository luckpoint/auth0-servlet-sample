package com.auth0.example;

import com.auth0.SessionUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/portal/home"})
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        final String accessToken = (String) SessionUtils.get(req, "accessToken");
        final String idToken = (String) SessionUtils.get(req, "idToken");
        if (accessToken != null) {
            req.setAttribute("userId", accessToken);
        } else if (idToken != null) {
            req.setAttribute("userId", idToken);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(req, res);
    }
}
