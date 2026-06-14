package valletech.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton para gestionar la conexión a MySQL.
 */
public class Conexion {

    private static final String URL      = "jdbc:mysql://localhost:3306/valletech_db?useSSL=false&serverTimezone=America/Lima";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "root123";

    private static Connection instancia = null;

    private Conexion() {}

    public static Connection getInstancia() {
        try {
            if (instancia == null || instancia.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                instancia = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("[ERROR] Driver MySQL no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("[ERROR] Conexión fallida: " + e.getMessage());
        }
        return instancia;
    }

    public static void cerrar() {
        try {
            if (instancia != null && !instancia.isClosed()) {
                instancia.close();
                instancia = null;
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] Al cerrar conexión: " + e.getMessage());
        }
    }
}
