package com.trabajo6.dao;

import com.trabajo6.model.User;
import java.sql.*;

public class UserDAO {
    public User authenticate(String usuario, String clave) throws SQLException {
        String sql = "SELECT id, usuario, clave, administrador FROM usuarios WHERE usuario = ? AND clave = ?";
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, usuario); ps.setString(2, clave);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? new User(rs.getInt("id"), rs.getString("usuario"), rs.getString("clave"), rs.getBoolean("administrador")) : null; }
        }
    }

    public java.util.List<User> findAll() throws SQLException {
        java.util.List<User> users = new java.util.ArrayList<>();
        String sql = "SELECT id, usuario, clave, administrador FROM usuarios ORDER BY usuario";
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) users.add(new User(rs.getInt("id"), rs.getString("usuario"), rs.getString("clave"), rs.getBoolean("administrador")));
        }
        return users;
    }

    public User findById(int id) throws SQLException {
        String sql = "SELECT id, usuario, clave, administrador FROM usuarios WHERE id = ?";
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? new User(rs.getInt("id"), rs.getString("usuario"), rs.getString("clave"), rs.getBoolean("administrador")) : null;
            }
        }
    }

    public void save(User user) throws SQLException {
        if (user.getId() == 0) {
            String sql = "INSERT INTO usuarios (usuario, clave, administrador) VALUES (?, ?, ?)";
            try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setString(1, user.getUsuario());
                ps.setString(2, user.getClave());
                ps.setBoolean(3, user.isAdministrador());
                ps.executeUpdate();
            }
            return;
        }
        String sql = "UPDATE usuarios SET usuario = ?, clave = ?, administrador = ? WHERE id = ?";
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, user.getUsuario());
            ps.setString(2, user.getClave());
            ps.setBoolean(3, user.isAdministrador());
            ps.setInt(4, user.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection cn = Database.getConnection(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
