package com.trabajo6.controller;

import com.trabajo6.dao.UserDAO;
import com.trabajo6.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebLoginController {
    private final UserDAO userDAO = new UserDAO();

    @GetMapping("/login")
    public String login() {
        return "forward:/WEB-INF/views/login.jsp";
    }

    @PostMapping("/login")
    public void autenticar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            User user = userDAO.authenticate(request.getParameter("usuario"), request.getParameter("clave"));
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login?error=1");
                return;
            }
            request.getSession(true).setAttribute("usuario", user);
            response.sendRedirect(request.getContextPath() + "/autos");
        } catch (Exception exception) {
            throw new IOException("No fue posible validar el usuario", exception);
        }
    }
}
