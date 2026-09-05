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
}
