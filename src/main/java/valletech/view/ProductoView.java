package valletech.view;

import valletech.controller.ProductoController;
import valletech.model.Producto;
import valletech.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Vista CRUD de Productos — ValleTech (MVC)
 */
public class ProductoView extends JFrame {

    // ── Paleta ────────────────────────────────────────────────────────────────
    private static final Color C_DARK    = new Color(15,  23,  42);
    private static final Color C_PANEL   = new Color(30,  41,  59);
    private static final Color C_ACCENT  = new Color(56, 189, 248);
    private static final Color C_GREEN   = new Color(74, 222, 128);
    private static final Color C_YELLOW  = new Color(250, 204,  21);
    private static final Color C_RED     = new Color(248, 113, 113);
    private static final Color C_TEXT    = new Color(226, 232, 240);
    private static final Color C_MUTED   = new Color(148, 163, 184);
    private static final Color C_BORDER  = new Color(51,  65,  85);
    private static final Color C_ROW_ALT = new Color(22,  33,  55);

    // ── Componentes ──────────────────────────────────────────────────────────
    private JTextField txtNombre, txtCategoria, txtPrecio, txtStock, txtBuscar;
    private JLabel     lblId;
    private JTable     tabla;
    private DefaultTableModel modeloTabla;

    private final ProductoController controller = new ProductoController();
    private final Usuario            usuario;
    private int productoSeleccionadoId = -1;

    public ProductoView(Usuario usuario) {
        this.usuario = usuario;
        initUI();
        cargarTabla(controller.listar());
    }

    private void initUI() {
        setTitle("ValleTech — Gestión de Productos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1080, 680);
        setLocationRelativeTo(null);
        getContentPane().setBackground(C_DARK);
        setLayout(new BorderLayout(0, 0));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildCentro(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);

        setVisible(true);
    }

    // ── HEADER ────────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(C_PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, C_BORDER),
            new EmptyBorder(14, 24, 14, 24)
        ));
        JLabel lbl = new JLabel("⬡ ValleTech  /  📦 Gestión de Productos");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setForeground(C_ACCENT);

        JButton btnVolver = roundBtn("← Dashboard", C_BORDER, C_TEXT);
        btnVolver.addActionListener(e -> { dispose(); new DashboardView(usuario); });

        p.add(lbl,       BorderLayout.WEST);
        p.add(btnVolver, BorderLayout.EAST);
        return p;
    }

    // ── CENTRO ────────────────────────────────────────────────────────────────
    private JSplitPane buildCentro() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buildFormPanel(), buildTablaPanel());
        split.setDividerLocation(340);
        split.setDividerSize(4);
        split.setBackground(C_DARK);
        split.setBorder(null);
        return split;
    }

    // ── FORMULARIO ────────────────────────────────────────────────────────────
    private JPanel buildFormPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBackground(C_PANEL);
        p.setBorder(new EmptyBorder(24, 24, 24, 20));

        JLabel titulo = new JLabel("Datos del Producto");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titulo.setForeground(C_TEXT);
        titulo.setAlignmentX(LEFT_ALIGNMENT);

        // ID (solo lectura)
        lblId = new JLabel("ID: —");
        lblId.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblId.setForeground(C_MUTED);
        lblId.setAlignmentX(LEFT_ALIGNMENT);

        txtNombre    = formField();
        txtCategoria = formField();
        txtPrecio    = formField();
        txtStock     = formField();

        // Botones CRUD
        JButton btnRegistrar  = roundBtn("＋  Registrar",  C_GREEN,  C_DARK);
        JButton btnModificar  = roundBtn("✎  Modificar",  C_YELLOW, C_DARK);
        JButton btnEliminar   = roundBtn("✕  Eliminar",   C_RED,    Color.WHITE);
        JButton btnLimpiar    = roundBtn("⟳  Limpiar",    C_BORDER, C_TEXT);

        for (JButton b : new JButton[]{btnRegistrar, btnModificar, btnEliminar, btnLimpiar}) {
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
            b.setAlignmentX(LEFT_ALIGNMENT);
        }

        btnRegistrar.addActionListener(e -> accionRegistrar());
        btnModificar.addActionListener(e -> accionModificar());
        btnEliminar.addActionListener(e  -> accionEliminar());
        btnLimpiar.addActionListener(e   -> limpiarFormulario());

        p.add(titulo);
        p.add(Box.createVerticalStrut(4));
        p.add(lblId);
        p.add(Box.createVerticalStrut(18));
        p.add(formLabel("Nombre"));
        p.add(Box.createVerticalStrut(4));
        p.add(txtNombre);
        p.add(Box.createVerticalStrut(12));
        p.add(formLabel("Categoría"));
        p.add(Box.createVerticalStrut(4));
        p.add(txtCategoria);
        p.add(Box.createVerticalStrut(12));
        p.add(formLabel("Precio (S/)"));
        p.add(Box.createVerticalStrut(4));
        p.add(txtPrecio);
        p.add(Box.createVerticalStrut(12));
        p.add(formLabel("Stock"));
        p.add(Box.createVerticalStrut(4));
        p.add(txtStock);
        p.add(Box.createVerticalStrut(24));
        p.add(btnRegistrar);
        p.add(Box.createVerticalStrut(8));
        p.add(btnModificar);
        p.add(Box.createVerticalStrut(8));
        p.add(btnEliminar);
        p.add(Box.createVerticalStrut(8));
        p.add(btnLimpiar);

        return p;
    }

    // ── TABLA ────────────────────────────────────────────────────────────────
    private JPanel buildTablaPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 12));
        p.setBackground(C_DARK);
        p.setBorder(new EmptyBorder(18, 12, 18, 20));

        // Barra de búsqueda
        JPanel barraTop = new JPanel(new BorderLayout(8, 0));
        barraTop.setBackground(C_DARK);

        txtBuscar = new JTextField();
        txtBuscar.setBackground(C_PANEL);
        txtBuscar.setForeground(C_TEXT);
        txtBuscar.setCaretColor(C_ACCENT);
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER),
            new EmptyBorder(6, 10, 6, 10)
        ));

        JButton btnBuscar = roundBtn("🔍 Buscar", C_ACCENT, C_DARK);
        JButton btnTodos  = roundBtn("↺ Todos",   C_BORDER, C_TEXT);

        btnBuscar.addActionListener(e -> {
            String q = txtBuscar.getText().trim();
            cargarTabla(q.isEmpty() ? controller.listar() : controller.buscar(q));
        });
        btnTodos.addActionListener(e -> { txtBuscar.setText(""); cargarTabla(controller.listar()); });

        barraTop.add(txtBuscar, BorderLayout.CENTER);
        barraTop.add(btnBuscar, BorderLayout.EAST);

        JPanel barraBtns = new JPanel(new BorderLayout());
        barraBtns.setBackground(C_DARK);
        barraBtns.add(barraTop, BorderLayout.CENTER);
        barraBtns.add(btnTodos, BorderLayout.EAST);

        // Tabla
        String[] cols = {"ID", "Nombre", "Categoría", "Precio (S/)", "Stock"};
        modeloTabla = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modeloTabla) {
            public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(56, 189, 248, 60));
                } else {
                    c.setBackground(row % 2 == 0 ? C_PANEL : C_ROW_ALT);
                }
                c.setForeground(C_TEXT);
                return c;
            }
        };
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setRowHeight(32);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setSelectionBackground(new Color(56, 189, 248, 80));
        tabla.setSelectionForeground(C_TEXT);
        tabla.setBackground(C_PANEL);
        tabla.setForeground(C_TEXT);
        tabla.getTableHeader().setReorderingAllowed(false);

        JTableHeader th = tabla.getTableHeader();
        th.setFont(new Font("Segoe UI", Font.BOLD, 12));
        th.setBackground(new Color(15, 23, 42));
        th.setForeground(C_ACCENT);
        th.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, C_BORDER));

        // Anchos de columna
        tabla.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(130);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(70);

        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { cargarFilaEnFormulario(); }
        });

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBackground(C_DARK);
        scroll.getViewport().setBackground(C_PANEL);
        scroll.setBorder(BorderFactory.createLineBorder(C_BORDER));

        p.add(barraBtns, BorderLayout.NORTH);
        p.add(scroll,    BorderLayout.CENTER);
        return p;
    }

    // ── FOOTER ───────────────────────────────────────────────────────────────
    private JPanel buildFooter() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.setBackground(C_PANEL);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, C_BORDER));
        JLabel l = new JLabel("ValleTech © 2025  |  Sistema de Gestión de Productos");
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(C_MUTED);
        p.add(l);
        return p;
    }

    // ── ACCIONES CRUD ────────────────────────────────────────────────────────
    private void accionRegistrar() {
        if (!validarCampos()) return;
        Producto p = buildProductoDesdeFormulario();
        if (controller.registrar(p)) {
            mostrarMensaje("✔ Producto registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla(controller.listar());
        } else {
            mostrarMensaje("✘ No se pudo registrar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionModificar() {
        if (productoSeleccionadoId < 0) {
            mostrarMensaje("Selecciona un producto de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!validarCampos()) return;
        Producto p = buildProductoDesdeFormulario();
        p.setId(productoSeleccionadoId);
        if (controller.modificar(p)) {
            mostrarMensaje("✔ Producto modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTabla(controller.listar());
        } else {
            mostrarMensaje("✘ No se pudo modificar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminar() {
        if (productoSeleccionadoId < 0) {
            mostrarMensaje("Selecciona un producto de la tabla primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Eliminar el producto seleccionado? Esta acción es irreversible.",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.eliminar(productoSeleccionadoId)) {
                mostrarMensaje("✔ Producto eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarTabla(controller.listar());
            } else {
                mostrarMensaje("✘ No se pudo eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ── HELPERS ──────────────────────────────────────────────────────────────
    private void cargarTabla(List<Producto> lista) {
        modeloTabla.setRowCount(0);
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getCategoria(),
                String.format("%.2f", p.getPrecio()), p.getStock()
            });
        }
    }

    private void cargarFilaEnFormulario() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) return;
        productoSeleccionadoId = (int) modeloTabla.getValueAt(fila, 0);
        lblId.setText("ID: " + productoSeleccionadoId);
        txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        txtCategoria.setText(modeloTabla.getValueAt(fila, 2).toString());
        txtPrecio.setText(modeloTabla.getValueAt(fila, 3).toString());
        txtStock.setText(modeloTabla.getValueAt(fila, 4).toString());
    }

    private void limpiarFormulario() {
        productoSeleccionadoId = -1;
        lblId.setText("ID: —");
        txtNombre.setText("");
        txtCategoria.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        tabla.clearSelection();
    }

    private boolean validarCampos() {
        if (txtNombre.getText().trim().isEmpty() || txtCategoria.getText().trim().isEmpty()) {
            mostrarMensaje("Nombre y Categoría son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stock     = Integer.parseInt(txtStock.getText().trim());
            if (precio < 0 || stock < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarMensaje("Precio y Stock deben ser números positivos válidos.", "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private Producto buildProductoDesdeFormulario() {
        return new Producto(
            0,
            txtNombre.getText().trim(),
            txtCategoria.getText().trim(),
            Double.parseDouble(txtPrecio.getText().trim()),
            Integer.parseInt(txtStock.getText().trim())
        );
    }

    private void mostrarMensaje(String msg, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, msg, titulo, tipo);
    }

    // ── COMPONENTES UI ───────────────────────────────────────────────────────
    private JLabel formLabel(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(new Font("Segoe UI", Font.BOLD, 11));
        l.setForeground(C_MUTED);
        l.setAlignmentX(LEFT_ALIGNMENT);
        return l;
    }

    private JTextField formField() {
        JTextField f = new JTextField();
        f.setBackground(C_DARK);
        f.setForeground(C_TEXT);
        f.setCaretColor(C_ACCENT);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER),
            new EmptyBorder(6, 10, 6, 10)
        ));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        f.setAlignmentX(LEFT_ALIGNMENT);
        return f;
    }

    private JButton roundBtn(String texto, Color bg, Color fg) {
        JButton b = new JButton(texto);
        b.setFont(new Font("Segoe UI", Font.BOLD, 12));
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setBorder(new EmptyBorder(8, 16, 8, 16));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(bg.brighter()); }
            public void mouseExited(MouseEvent e)  { b.setBackground(bg); }
        });
        return b;
    }
}
