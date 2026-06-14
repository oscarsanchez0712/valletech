package valletech.controller;

import valletech.database.Conexion;
import valletech.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controlador de autenticación de usuarios.
 */
public class UsuarioController {

    /**
     * Valida las credenciales contra MySQL.
     * @return Usuario si es válido, null si no.
     */
    public Usuario login(String usuario, String contrasena) {
        String sql = "SELECT id, usuario, nombre FROM usuarios WHERE usuario = ? AND contrasena = ?";
        Connection conn = Conexion.getInstancia();

        if (conn == null) return null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setString(2, contrasena);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    contrasena,
                    rs.getString("nombre")
                );
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Login: " + e.getMessage());
        }
        return null;
    }
}
