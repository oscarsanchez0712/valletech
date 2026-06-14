package valletech.view;

import valletech.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Dashboard principal — ValleTech
 */
public class DashboardView extends JFrame {

    private static final Color C_DARK   = new Color(15,  23,  42);
    private static final Color C_PANEL  = new Color(30,  41,  59);
    private static final Color C_ACCENT = new Color(56, 189, 248);
    private static final Color C_INDIG  = new Color(99, 102, 241);
    private static final Color C_RED    = new Color(248, 113, 113);
    private static final Color C_TEXT   = new Color(226, 232, 240);
    private static final Color C_MUTED  = new Color(148, 163, 184);
    private static final Color C_BORDER = new Color(51,  65,  85);

    private final Usuario usuarioActual;

    public DashboardView(Usuario usuario) {
        this.usuarioActual = usuario;
        initUI();
    }

    private void initUI() {
        setTitle("ValleTech — Dashboard");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(C_DARK);
        setLayout(new BorderLayout());

        // ── Header ───────────────────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(C_PANEL);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, C_BORDER),
            new EmptyBorder(18, 30, 18, 30)
        ));

        JLabel lblTitulo = new JLabel("⬡ ValleTech");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(C_ACCENT);

        JLabel lblUser = new JLabel("● " + usuarioActual.getNombre() + "  |  " + usuarioActual.getUsuario());
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setForeground(C_MUTED);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(lblUser,   BorderLayout.EAST);

        // ── Centro: tarjetas de módulos ───────────────────────────────────────
        JPanel centro = new JPanel(new GridBagLayout());
        centro.setBackground(C_DARK);
        centro.setBorder(new EmptyBorder(50, 60, 50, 60));

        JLabel lblBienvenida = new JLabel("¿Qué deseas gestionar hoy?");
        lblBienvenida.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBienvenida.setForeground(C_TEXT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 36, 0);
        centro.add(lblBienvenida, gbc);

        // Tarjeta Gestión de Productos
        JPanel cardProductos = crearTarjeta(
            "📦", "Gestión de Productos",
            "Registrar, modificar, eliminar\ny buscar productos.",
            C_ACCENT, C_INDIG
        );
        cardProductos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                new ProductoView(usuarioActual);
            }
        });

        // Tarjeta Cerrar Sesión
        JPanel cardSalir = crearTarjeta(
            "🔒", "Cerrar Sesión",
            "Salir del sistema de forma\nsegura.",
            C_RED, new Color(220, 38, 38)
        );
        cardSalir.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = JOptionPane.showConfirmDialog(
                    DashboardView.this,
                    "¿Estás seguro de que deseas cerrar sesión?",
                    "Cerrar Sesión",
                    JOptionPane.YES_NO_OPTION
                );
                if (r == JOptionPane.YES_OPTION) {
                    dispose();
                    new LoginView();
                }
            }
        });

        gbc.gridwidth = 1;
        gbc.gridy     = 1;
        gbc.gridx     = 0;
        gbc.insets    = new Insets(0, 0, 0, 16);
        gbc.fill      = GridBagConstraints.BOTH;
        gbc.weightx   = 1;
        gbc.weighty   = 1;
        centro.add(cardProductos, gbc);

        gbc.gridx  = 1;
        gbc.insets = new Insets(0, 16, 0, 0);
        centro.add(cardSalir, gbc);

        // ── Footer ────────────────────────────────────────────────────────────
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setBackground(C_PANEL);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, C_BORDER));
        JLabel lblFooter = new JLabel("ValleTech © 2025  |  Sistema de Gestión de Productos");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(C_MUTED);
        footer.add(lblFooter);

        add(header, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel crearTarjeta(String icono, String titulo, String desc,
                                 Color colorBorde, Color colorHover) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(C_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(colorBorde, 2),
            new EmptyBorder(30, 28, 30, 28)
        ));
        card.setPreferredSize(new Dimension(240, 200));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel lblIco = new JLabel(icono);
        lblIco.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        lblIco.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTit = new JLabel(titulo);
        lblTit.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTit.setForeground(C_TEXT);
        lblTit.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea txtDesc = new JTextArea(desc);
        txtDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDesc.setForeground(C_MUTED);
        txtDesc.setBackground(C_PANEL);
        txtDesc.setEditable(false);
        txtDesc.setFocusable(false);
        txtDesc.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblIco);
        card.add(Box.createVerticalStrut(12));
        card.add(lblTit);
        card.add(Box.createVerticalStrut(8));
        card.add(txtDesc);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(colorHover, 2),
                    new EmptyBorder(30, 28, 30, 28)
                ));
                card.setBackground(new Color(colorHover.getRed(), colorHover.getGreen(), colorHover.getBlue(), 30));
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(colorBorde, 2),
                    new EmptyBorder(30, 28, 30, 28)
                ));
                card.setBackground(C_PANEL);
            }
        });

        return card;
    }
}
