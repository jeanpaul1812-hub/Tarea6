package com.trabajo6.controller;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException { req.getSession().invalidate(); resp.sendRedirect(req.getContextPath() + "/login"); }
}
