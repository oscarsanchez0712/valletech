package valletech.model;

/**
 * Modelo de Producto.
 */
public class Producto {

    private int    id;
    private String nombre;
    private String categoria;
    private double precio;
    private int    stock;

    public Producto() {}

    public Producto(int id, String nombre, String categoria, double precio, int stock) {
        this.id        = id;
        this.nombre    = nombre;
        this.categoria = categoria;
        this.precio    = precio;
        this.stock     = stock;
    }

    public int    getId()        { return id; }
    public String getNombre()    { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecio()    { return precio; }
    public int    getStock()     { return stock; }

    public void setId(int id)                  { this.id = id; }
    public void setNombre(String nombre)       { this.nombre = nombre; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setPrecio(double precio)       { this.precio = precio; }
    public void setStock(int stock)            { this.stock = stock; }

    @Override
    public String toString() {
        return nombre;
    }
}
