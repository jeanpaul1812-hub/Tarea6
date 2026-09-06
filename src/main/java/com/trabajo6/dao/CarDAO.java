package com.trabajo6.dao;

import com.trabajo6.model.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    public List<Car> findAll() throws SQLException {
        List<Car> cars = new ArrayList<>();
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM diecast ORDER BY id DESC"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) cars.add(map(rs));
        }
        return cars;
    }
    public List<Car> findPage(int page, int pageSize, String search, String sort, String direction) throws SQLException {
        List<Car> cars = new ArrayList<>();
        String filter = "%" + (search == null ? "" : search.trim()) + "%";
        String orderColumn = sortColumn(sort);
        String orderDirection = "desc".equalsIgnoreCase(direction) ? "DESC" : "ASC";
        String sql = "SELECT * FROM diecast WHERE COALESCE(marca, '') <> '' AND COALESCE(modelo, '') <> '' AND COALESCE(codigo, '') <> '' AND LOWER(CONCAT_WS(' ', marca, modelo, codigo, anio, numero, color, serie, otro, thunt)) LIKE LOWER(?) ORDER BY " + orderColumn + " " + orderDirection + " LIMIT ? OFFSET ?";
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, filter); ps.setInt(2, pageSize); ps.setInt(3, (page - 1) * pageSize);
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) cars.add(map(rs)); }
        }
        return cars;
    }
    private String sortColumn(String sort) {
        if ("marca".equals(sort)) return "marca";
        if ("modelo".equals(sort)) return "modelo";
        if ("codigo".equals(sort)) return "codigo";
        if ("anio".equals(sort)) return "anio";
        if ("numero".equals(sort)) return "numero";
        if ("color".equals(sort)) return "color";
        if ("serie".equals(sort)) return "serie";
        if ("thunt".equals(sort)) return "thunt";
        return "id";
    }
    public int count(String search) throws SQLException {
        String filter = "%" + (search == null ? "" : search.trim()) + "%";
        String sql = "SELECT COUNT(*) FROM diecast WHERE COALESCE(marca, '') <> '' AND COALESCE(modelo, '') <> '' AND COALESCE(codigo, '') <> '' AND LOWER(CONCAT_WS(' ', marca, modelo, codigo, anio, numero, color, serie, otro, thunt)) LIKE LOWER(?)";
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) { ps.setString(1, filter); try (ResultSet rs = ps.executeQuery()) { rs.next(); return rs.getInt(1); } }
    }
    public Car findById(int id) throws SQLException {
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement("SELECT * FROM diecast WHERE id = ?")) {
            ps.setInt(1, id); try (ResultSet rs = ps.executeQuery()) { return rs.next() ? map(rs) : null; }
        }
    }
    public void save(Car c) throws SQLException {
        String sql = c.getId() == 0 ? "INSERT INTO diecast (marca, modelo, codigo, anio, numero, color, serie, otro, thunt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" : "UPDATE diecast SET marca=?, modelo=?, codigo=?, anio=?, numero=?, color=?, serie=?, otro=?, thunt=? WHERE id=?";
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            int i = 1; ps.setString(i++, c.getMarca()); ps.setString(i++, c.getModelo()); ps.setString(i++, c.getCodigo()); ps.setInt(i++, c.getAnio()); ps.setInt(i++, c.getNumero()); ps.setString(i++, c.getColor()); ps.setString(i++, c.getSerie()); ps.setString(i++, c.getOtro()); ps.setString(i++, c.getThunt()); if (c.getId() != 0) ps.setInt(i, c.getId()); ps.executeUpdate();
        }
    }
    public void delete(int id) throws SQLException { try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement("DELETE FROM diecast WHERE id=?")) { ps.setInt(1, id); ps.executeUpdate(); } }
    public void replaceAll(List<Car> cars) throws SQLException {
        try (Connection cn = Database.getConnection()) {
            cn.setAutoCommit(false);
            try (Statement delete = cn.createStatement(); PreparedStatement insert = cn.prepareStatement("INSERT INTO diecast (id, marca, modelo, codigo, anio, numero, color, serie, otro, thunt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                delete.executeUpdate("DELETE FROM diecast");
                for (Car car : cars) {
                    insert.setInt(1, car.getId()); insert.setString(2, car.getMarca()); insert.setString(3, car.getModelo()); insert.setString(4, car.getCodigo()); insert.setInt(5, car.getAnio()); insert.setInt(6, car.getNumero()); insert.setString(7, car.getColor()); insert.setString(8, car.getSerie()); insert.setString(9, car.getOtro()); insert.setString(10, car.getThunt()); insert.addBatch();
                }
                insert.executeBatch();
                cn.commit();
            } catch (SQLException exception) { cn.rollback(); throw exception; }
            finally { cn.setAutoCommit(true); }
        }
    }
    private Car map(ResultSet rs) throws SQLException { return new Car(rs.getInt("id"), rs.getString("marca"), rs.getString("modelo"), rs.getString("codigo"), rs.getInt("anio"), rs.getInt("numero"), rs.getString("color"), rs.getString("serie"), rs.getString("otro"), rs.getString("thunt")); }
}
