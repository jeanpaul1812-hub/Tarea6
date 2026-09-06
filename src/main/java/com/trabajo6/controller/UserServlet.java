package com.trabajo6.controller;

import com.trabajo6.dao.UserDAO;
import com.trabajo6.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/usuarios")
public class UserServlet extends HttpServlet {
    private final UserDAO dao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdministrator(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo los administradores pueden administrar usuarios");
            return;
        }
        try {
            String action = request.getParameter("action");
            if ("new".equals(action)) {
                request.getRequestDispatcher("/WEB-INF/views/user-form.jsp").forward(request, response);
                return;
            }
            if ("edit".equals(action)) {
                request.setAttribute("managedUser", dao.findById(Integer.parseInt(request.getParameter("id"))));
                request.getRequestDispatcher("/WEB-INF/views/user-form.jsp").forward(request, response);
                return;
            }
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                if (id == currentUser(request).getId()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No puedes eliminar tu propio usuario");
                    return;
                }
                dao.delete(id);
                response.sendRedirect(request.getContextPath() + "/usuarios");
                return;
            }
            request.setAttribute("users", dao.findAll());
            request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
        } catch (Exception exception) {
            throw new ServletException("No fue posible administrar los usuarios", exception);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdministrator(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo los administradores pueden administrar usuarios");
            return;
        }
        try {
            int id = parseInt(request.getParameter("id"));
            User existingUser = id == 0 ? null : dao.findById(id);
            String password = request.getParameter("clave");
            if (existingUser != null && (password == null || password.isBlank())) password = existingUser.getClave();
            if (password == null || password.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "La clave es obligatoria");
                return;
            }
            User user = new User(id, request.getParameter("usuario"), password, request.getParameter("administrador") != null);
            dao.save(user);
            response.sendRedirect(request.getContextPath() + "/usuarios");
        } catch (Exception exception) {
            throw new ServletException("No fue posible guardar el usuario", exception);
        }
    }

    private int parseInt(String value) { return value == null || value.isBlank() ? 0 : Integer.parseInt(value); }
    private User currentUser(HttpServletRequest request) { return (User) request.getSession(false).getAttribute("usuario"); }
    private boolean isAdministrator(HttpServletRequest request) { return currentUser(request).isAdministrador(); }
}