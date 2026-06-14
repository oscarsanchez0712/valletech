package valletech.controller;

import valletech.database.Conexion;
import valletech.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador CRUD de Productos.
 */
public class ProductoController {

    private Connection conn() {
        return Conexion.getInstancia();
    }

    // ── LISTAR TODOS ──────────────────────────────────────────────────────────
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, categoria, precio, stock FROM productos ORDER BY id";
        try (PreparedStatement ps = conn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Listar: " + e.getMessage());
        }
        return lista;
    }

    // ── BUSCAR ────────────────────────────────────────────────────────────────
    public List<Producto> buscar(String texto) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, categoria, precio, stock FROM productos " +
                     "WHERE nombre LIKE ? OR categoria LIKE ? ORDER BY id";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            String like = "%" + texto + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            System.err.println("[ERROR] Buscar: " + e.getMessage());
        }
        return lista;
    }

    // ── OBTENER POR ID ────────────────────────────────────────────────────────
    public Producto obtenerPorId(int id) {
        String sql = "SELECT id, nombre, categoria, precio, stock FROM productos WHERE id = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapear(rs);
        } catch (SQLException e) {
            System.err.println("[ERROR] ObtenerPorId: " + e.getMessage());
        }
        return null;
    }

    // ── REGISTRAR ─────────────────────────────────────────────────────────────
    public boolean registrar(Producto p) {
        String sql = "INSERT INTO productos (nombre, categoria, precio, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Registrar: " + e.getMessage());
        }
        return false;
    }

    // ── MODIFICAR ─────────────────────────────────────────────────────────────
    public boolean modificar(Producto p) {
        String sql = "UPDATE productos SET nombre=?, categoria=?, precio=?, stock=? WHERE id=?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCategoria());
            ps.setDouble(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.setInt(5, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Modificar: " + e.getMessage());
        }
        return false;
    }

    // ── ELIMINAR ──────────────────────────────────────────────────────────────
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR] Eliminar: " + e.getMessage());
        }
        return false;
    }

    // ── HELPER ───────────────────────────────────────────────────────────────
    private Producto mapear(ResultSet rs) throws SQLException {
        return new Producto(
            rs.getInt("id"),
            rs.getString("nombre"),
            rs.getString("categoria"),
            rs.getDouble("precio"),
            rs.getInt("stock")
        );
    }
}
