package com.trabajo6.controller;

import com.trabajo6.dao.UserDAO;
import com.trabajo6.model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException { req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp); }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usuario = req.getParameter("usuario"); String clave = req.getParameter("clave");
        try { User user = userDAO.authenticate(usuario, clave); if (user == null) { req.setAttribute("error", "Usuario o clave incorrectos"); doGet(req, resp); return; } req.getSession().setAttribute("usuario", user); resp.sendRedirect(req.getContextPath() + "/autos"); }
        catch (Exception e) { throw new ServletException("No fue posible validar el usuario", e); }
    }
}
