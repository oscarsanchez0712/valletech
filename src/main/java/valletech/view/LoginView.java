package valletech.view;

import valletech.controller.UsuarioController;
import valletech.model.Usuario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Vista de Login — ValleTech
 */
public class LoginView extends JFrame {

    // ── Paleta ValleTech ──────────────────────────────────────────────────────
    private static final Color C_DARK    = new Color(15,  23,  42);   // fondo principal
    private static final Color C_PANEL   = new Color(30,  41,  59);   // panel tarjeta
    private static final Color C_ACCENT  = new Color(56, 189, 248);   // azul ValleTech
    private static final Color C_ACCENT2 = new Color(99, 102, 241);   // índigo secundario
    private static final Color C_TEXT    = new Color(226, 232, 240);
    private static final Color C_MUTED   = new Color(148, 163, 184);
    private static final Color C_BORDER  = new Color(51,  65,  85);
    private static final Color C_ERROR   = new Color(248,  113, 113);
    private static final Color C_SUCCESS = new Color(74,  222, 128);

    private JTextField     txtUsuario;
    private JPasswordField txtContrasena;
    private JButton        btnIngresar;
    private JLabel         lblMensaje;

    private final UsuarioController controller = new UsuarioController();

    public LoginView() {
        initUI();
    }

    private void initUI() {
        setTitle("ValleTech — Acceso al Sistema");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setSize(460, 560);
        setLocationRelativeTo(null);
        getContentPane().setBackground(C_DARK);
        setLayout(new GridBagLayout());

        // ── Tarjeta central ──────────────────────────────────────────────────
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(C_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER, 1),
            new EmptyBorder(40, 44, 40, 44)
        ));
        card.setPreferredSize(new Dimension(380, 460));

        // Logo / título
        JLabel lblLogo = new JLabel("⬡ ValleTech");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblLogo.setForeground(C_ACCENT);
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Gestión de Productos");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setForeground(C_MUTED);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(C_BORDER);

        // Campo usuario
        JLabel lUsuario = fieldLabel("Usuario");
        txtUsuario = styledField();

        // Campo contraseña
        JLabel lPass = fieldLabel("Contraseña");
        txtContrasena = new JPasswordField();
        styleComponent(txtContrasena);

        // Mensaje feedback
        lblMensaje = new JLabel(" ");
        lblMensaje.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón ingresar
        btnIngresar = new JButton("Ingresar al Sistema");
        btnIngresar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIngresar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnIngresar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnIngresar.setBackground(C_ACCENT);
        btnIngresar.setForeground(C_DARK);
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorderPainted(false);
        btnIngresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        btnIngresar.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btnIngresar.setBackground(C_ACCENT2); btnIngresar.setForeground(Color.WHITE); }
            public void mouseExited(MouseEvent e)  { btnIngresar.setBackground(C_ACCENT);  btnIngresar.setForeground(C_DARK); }
        });

        btnIngresar.addActionListener(e -> doLogin());
        txtContrasena.addActionListener(e -> doLogin());
        txtUsuario.addActionListener(e -> txtContrasena.requestFocus());

        // Footer hint
        JLabel lblHint = new JLabel("admin / admin123  ·  oscar / oscar123");
        lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblHint.setForeground(new Color(71, 85, 105));
        lblHint.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ── Ensamblado ───────────────────────────────────────────────────────
        card.add(lblLogo);
        card.add(Box.createVerticalStrut(6));
        card.add(lblSub);
        card.add(Box.createVerticalStrut(24));
        card.add(sep);
        card.add(Box.createVerticalStrut(28));
        card.add(lUsuario);
        card.add(Box.createVerticalStrut(6));
        card.add(txtUsuario);
        card.add(Box.createVerticalStrut(18));
        card.add(lPass);
        card.add(Box.createVerticalStrut(6));
        card.add(txtContrasena);
        card.add(Box.createVerticalStrut(20));
        card.add(lblMensaje);
        card.add(Box.createVerticalStrut(10));
        card.add(btnIngresar);
        card.add(Box.createVerticalStrut(20));
        card.add(lblHint);

        add(card);
        setVisible(true);
    }

    private void doLogin() {
        String user = txtUsuario.getText().trim();
        String pass = new String(txtContrasena.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            setMensaje("⚠  Completa todos los campos.", C_ERROR);
            return;
        }

        btnIngresar.setEnabled(false);
        btnIngresar.setText("Verificando...");

        SwingWorker<Usuario, Void> worker = new SwingWorker<>() {
            protected Usuario doInBackground() {
                return controller.login(user, pass);
            }
            protected void done() {
                try {
                    Usuario u = get();
                    if (u != null) {
                        setMensaje("✔  ¡Bienvenido, " + u.getNombre() + "!", C_SUCCESS);
                        Timer t = new Timer(800, ev -> {
                            dispose();
                            new DashboardView(u);
                        });
                        t.setRepeats(false);
                        t.start();
                    } else {
                        setMensaje("✘  Usuario o contraseña incorrectos.", C_ERROR);
                        txtContrasena.setText("");
                        btnIngresar.setEnabled(true);
                        btnIngresar.setText("Ingresar al Sistema");
                    }
                } catch (Exception ex) {
                    setMensaje("✘  Error de conexión con el servidor.", C_ERROR);
                    btnIngresar.setEnabled(true);
                    btnIngresar.setText("Ingresar al Sistema");
                }
            }
        };
        worker.execute();
    }

    private void setMensaje(String txt, Color color) {
        lblMensaje.setText(txt);
        lblMensaje.setForeground(color);
    }

    private JLabel fieldLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(C_MUTED);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private JTextField styledField() {
        JTextField f = new JTextField();
        styleComponent(f);
        return f;
    }

    private void styleComponent(JComponent c) {
        c.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        c.setBackground(C_DARK);
        c.setForeground(C_TEXT);
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(C_BORDER, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (c instanceof JTextField) ((JTextField) c).setCaretColor(C_ACCENT);
    }
}
