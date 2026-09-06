package com.trabajo6.controller;

import com.trabajo6.dao.CarDAO;
import com.trabajo6.model.Car;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.*;
import java.util.List;

@WebServlet("/autos")
@MultipartConfig(maxFileSize = 20 * 1024 * 1024, maxRequestSize = 20 * 1024 * 1024)
public class CarServlet extends HttpServlet {
    private final CarDAO dao = new CarDAO();
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("importCsv".equals(req.getParameter("action"))) {
            if (!isAdministrator(req)) { resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo los administradores pueden importar el inventario"); return; }
            req.getRequestDispatcher("/WEB-INF/views/import-csv.jsp").forward(req, resp);
            return;
        }
        try { String action = req.getParameter("action"); if (isWriteAction(action) && !isAdministrator(req)) { resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo los administradores pueden modificar el inventario"); return; } if ("new".equals(action)) { req.getRequestDispatcher("/WEB-INF/views/car-form.jsp").forward(req, resp); return; } if ("edit".equals(action)) { req.setAttribute("car", dao.findById(Integer.parseInt(req.getParameter("id")))); req.getRequestDispatcher("/WEB-INF/views/car-form.jsp").forward(req, resp); return; } if ("delete".equals(action)) { dao.delete(Integer.parseInt(req.getParameter("id"))); resp.sendRedirect(req.getContextPath() + "/autos"); return; } int page = parseInt(req.getParameter("page")); if (page < 1) page = 1; String search = req.getParameter("q"); String sort = req.getParameter("sort"); String direction = req.getParameter("dir"); int pageSize = 100; int total = dao.count(search); req.setAttribute("cars", dao.findPage(page, pageSize, search, sort, direction)); req.setAttribute("page", page); req.setAttribute("total", total); req.setAttribute("totalPages", Math.max(1, (total + pageSize - 1) / pageSize)); req.setAttribute("search", search == null ? "" : search); req.setAttribute("sort", sort == null ? "id" : sort); req.setAttribute("dir", "desc".equalsIgnoreCase(direction) ? "desc" : "asc"); req.setAttribute("esAdministrador", isAdministrator(req)); req.getRequestDispatcher("/WEB-INF/views/cars.jsp").forward(req, resp); }
        catch (Exception e) { throw new ServletException("No fue posible consultar el inventario", e); }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try { if (!isAdministrator(req)) { resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo los administradores pueden modificar el inventario"); return; } if ("importCsv".equals(req.getParameter("action"))) { Part file = req.getPart("csvFile"); if (file == null || file.getSize() == 0) throw new IllegalArgumentException("Selecciona un archivo CSV"); List<Car> cars = CarCsvParser.parse(file.getInputStream()); dao.replaceAll(cars); resp.sendRedirect(req.getContextPath() + "/autos?imported=" + cars.size()); return; } Car car = new Car(); car.setId(parseInt(req.getParameter("id"))); car.setMarca(req.getParameter("marca")); car.setModelo(req.getParameter("modelo")); car.setCodigo(req.getParameter("codigo")); car.setAnio(parseInt(req.getParameter("anio"))); car.setNumero(parseInt(req.getParameter("numero"))); car.setColor(req.getParameter("color")); car.setSerie(req.getParameter("serie")); car.setOtro(req.getParameter("otro")); car.setThunt(req.getParameter("thunt")); dao.save(car); resp.sendRedirect(req.getContextPath() + "/autos"); }
        catch (IllegalArgumentException e) { if ("importCsv".equals(req.getParameter("action"))) { req.setAttribute("importError", e.getMessage()); req.getRequestDispatcher("/WEB-INF/views/import-csv.jsp").forward(req, resp); } else { throw e; } }
        catch (Exception e) { throw new ServletException("No fue posible guardar el auto", e); }
    }
    private int parseInt(String value) { return value == null || value.isBlank() ? 0 : Integer.parseInt(value); }
    private boolean isWriteAction(String action) { return "new".equals(action) || "edit".equals(action) || "delete".equals(action) || "importCsv".equals(action); }
    private boolean isAdministrator(HttpServletRequest req) { Object user = req.getSession(false).getAttribute("usuario"); return user instanceof com.trabajo6.model.User && ((com.trabajo6.model.User) user).isAdministrador(); }
}
