package valletech.model;

/**
 * Modelo de Usuario.
 */
public class Usuario {

    private int    id;
    private String usuario;
    private String contrasena;
    private String nombre;

    public Usuario() {}

    public Usuario(int id, String usuario, String contrasena, String nombre) {
        this.id         = id;
        this.usuario    = usuario;
        this.contrasena = contrasena;
        this.nombre     = nombre;
    }

    public int    getId()          { return id; }
    public String getUsuario()     { return usuario; }
    public String getContrasena()  { return contrasena; }
    public String getNombre()      { return nombre; }

    public void setId(int id)                  { this.id = id; }
    public void setUsuario(String usuario)     { this.usuario = usuario; }
    public void setContrasena(String c)        { this.contrasena = c; }
    public void setNombre(String nombre)       { this.nombre = nombre; }
}
