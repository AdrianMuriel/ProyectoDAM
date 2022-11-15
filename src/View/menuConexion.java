package View;

//-------- IMPORTS ------------------------------------------------------------
//-------- PAQUETES -----------------------------------------------------------
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controller.gestionarIdioma;
import Controller.gestionarSockets;
//-------- CLASES -------------------------------------------------------------
import Model.Comics;

public class menuConexion extends JDialog {

    // ----------- Panel --------------------------------------------------------
    private final JPanel contentPanel = new JPanel();
    // ----------- Botones ------------------------------------------------------
    private JButton btnCancelar;
    private JButton btnGuardar;
    // ----------- Cursores -----------------------------------------------------
    private Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    private Cursor selectorCursor = new Cursor(Cursor.HAND_CURSOR);
    // ----------- Etiquetas -----------------------------------------------------
    private JLabel lblTitulo;
    private JLabel lblIP;
    private JLabel lblPuerto;
    private JLabel lblUsuario;
    private JLabel lblClave;
    private JLabel lblBD;
    // ----------- Texto ---------------------------------------------------------
    private JTextField txtIP;
    private JSpinner spPuerto;
    private JTextField txtUsuario;
    private JTextField txtClave;
    private JTextField txtBD;
    // ----------- Listas -------------------------------------------------------
    private static ArrayList<JLabel> listLabels = new ArrayList<>();
    private static ArrayList<JButton> listButtons = new ArrayList<>();
    // ----------- Otros --------------------------------------------------------
    static String titulo;
    static Comics comic;
    ClassLoader clLoad = this.getClass().getClassLoader();
    InputStream defaultProp = clLoad.getResourceAsStream("/data/language/default.properties");
    private HelpSet helpset = null;
    private HelpBroker browser = null;
    private static URL helpURL;
    public static Locale language;
    private static Properties properties = new Properties();

    /**
     * Launch the application.
     */
    public static void iniciar() {
        try {
            menuConexion dialog = new menuConexion();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public menuConexion() {
        setModal(true);
        setTitle("Configurar Conexión");
        setIconImage(
                Toolkit.getDefaultToolkit().getImage(menuConexion.class.getResource("/images/icons/connection.png")));
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        lblTitulo = new JLabel("CONFIGURAR CONEXIÓN");

        Image image = new ImageIcon(
                menuComics.class.getResource("/images/icons/connection.png")).getImage().getScaledInstance(
                        50,
                        50,
                        java.awt.Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(image);
        lblTitulo.setIcon(imageIcon);
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 30));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        lblIP = new JLabel("IP de la BD");

        lblPuerto = new JLabel("Puerto de la BD");

        lblUsuario = new JLabel("Usuario de la BD");

        lblClave = new JLabel("Clave de la BD");

        lblBD = new JLabel("Base de Datos");

        lblIP.setName("lblIP");
        lblPuerto.setName("lblPuerto");
        lblUsuario.setName("lblUsuario");
        lblClave.setName("lblClave");
        lblBD.setName("lblBD");

        txtIP = new JTextField();
        txtIP.setHorizontalAlignment(SwingConstants.RIGHT);
        txtIP.setColumns(10);

        txtUsuario = new JTextField();
        txtUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
        txtUsuario.setColumns(10);

        txtClave = new JTextField();
        txtClave.setHorizontalAlignment(SwingConstants.RIGHT);
        txtClave.setColumns(10);

        txtBD = new JTextField();
        txtBD.setHorizontalAlignment(SwingConstants.RIGHT);
        txtBD.setColumns(10);

        spPuerto = new JSpinner();
        GroupLayout gl_contentPanel;
        {
            gl_contentPanel = new GroupLayout(contentPanel);
            gl_contentPanel.setHorizontalGroup(
                    gl_contentPanel.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_contentPanel.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                            .addComponent(lblTitulo, GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                                            .addGroup(gl_contentPanel.createSequentialGroup()
                                                    .addGroup(gl_contentPanel
                                                            .createParallelGroup(Alignment.TRAILING, false)
                                                            .addComponent(lblBD, GroupLayout.DEFAULT_SIZE,
                                                                    GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(lblClave, Alignment.LEADING,
                                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                                    Short.MAX_VALUE)
                                                            .addComponent(lblPuerto, Alignment.LEADING,
                                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                                    Short.MAX_VALUE)
                                                            .addComponent(lblIP, Alignment.LEADING,
                                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                                    Short.MAX_VALUE)
                                                            .addComponent(lblUsuario, Alignment.LEADING,
                                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
                                                                    Short.MAX_VALUE))
                                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                                            .addComponent(txtBD, Alignment.TRAILING,
                                                                    GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                                                            .addComponent(txtIP, GroupLayout.DEFAULT_SIZE, 316,
                                                                    Short.MAX_VALUE)
                                                            .addComponent(txtUsuario, GroupLayout.DEFAULT_SIZE, 316,
                                                                    Short.MAX_VALUE)
                                                            .addComponent(txtClave, GroupLayout.DEFAULT_SIZE, 316,
                                                                    Short.MAX_VALUE)
                                                            .addComponent(spPuerto, GroupLayout.DEFAULT_SIZE, 316,
                                                                    Short.MAX_VALUE))))
                                    .addContainerGap()));
            gl_contentPanel.setVerticalGroup(
                    gl_contentPanel.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_contentPanel.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(lblIP)
                                            .addComponent(txtIP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(lblPuerto)
                                            .addComponent(spPuerto, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(lblUsuario)
                                            .addComponent(txtUsuario, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(lblClave)
                                            .addComponent(txtClave, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(lblBD)
                                            .addComponent(txtBD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.PREFERRED_SIZE))
                                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
            contentPanel.setLayout(gl_contentPanel);
        }
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnGuardar = new JButton("Guardar");
        buttonPane.add(btnGuardar);
        getRootPane().setDefaultButton(btnGuardar);

        btnCancelar = new JButton("Cancelar");
        buttonPane.add(btnCancelar);

        // ----------------------------------------------------------------------------------------------------------------------------
        // MÉTODOS
        // ----------------------------------------------------------------------------------------------------------------------------
        // ----------- COMPORTAMIENTO DE LA VENTANA DEL PROGRAMA
        addWindowListener(new WindowAdapter() {
            // ----------- ABRIENDO EL PROGRAMA
            @Override
            @Deprecated
            public void windowOpened(WindowEvent e) {
                setCursor(waitCursor);
                // --------------------------------------------------------------------
                // Aquí nombro cada elementos para poder traducirlos en otro método
                // --------------------------------------------------------------------
                btnCancelar.setName("btnCancelar");
                btnGuardar.setName("btnGuardar");
                // --------------------------------------------------------------------
                // Aquí añado todos los elementos traducibles a una lista para facilitar la
                // traducción
                // --------------------------------------------------------------------
                listLabels.add(lblClave);
                listLabels.add(lblIP);
                listLabels.add(lblPuerto);
                listLabels.add(lblBD);
                listLabels.add(lblUsuario);

                listButtons.add(btnGuardar);
                listButtons.add(btnCancelar);
                // --------------------------------------------------------------------
                // Aquí configuro la ayuda y la lectura del idioma por defecto
                // --------------------------------------------------------------------
                try {
                    File archivo = new File("./data/language/default.properties");
                    FileInputStream is = new FileInputStream(archivo);
                    properties.load(is);
                    String locLang = String.valueOf(properties.get("LANG"));
                    traducirPrograma(locLang);
                    String lang[] = locLang.split("_");
                    Locale.setDefault(new Locale(locLang));

                    switch (lang[0]) {
                        case "es":
                            helpURL = this.getClass().getResource("/data/help/help.hs");
                            break;
                        case "gl":
                            helpURL = this.getClass().getResource("/data/help/help_gl_ES.hs");
                            break;
                    }
                    helpset = new HelpSet(null, helpURL);
                    browser = helpset.createHelpBroker();
                    browser.enableHelpKey(getContentPane(), "conexion", helpset);
                } catch (HelpSetException | IOException ex1) {
                    ex1.printStackTrace();
                }
                // --------------------------------------------------------------------
                // Aquí añado los datos a los campos de texto
                // --------------------------------------------------------------------
                try {
                    String[] conData = gestionarSockets.gestCon.getConData();
                    txtIP.setText(conData[0]);
                    spPuerto.setValue(Integer.parseInt(conData[1]));
                    txtUsuario.setText(conData[2]);
                    txtClave.setText(conData[3]);
                    txtBD.setText(conData[4]);
                } catch (Exception ex2) {
                    ex2.printStackTrace();
                } finally {
                    setCursor(defaultCursor);
                }
            }
        });
        // ----------------------------------------------------------------------------------------------------------------------------
        // ----------- OTROS
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    setCursor(waitCursor);
                    boolean hasData = false;
                    if (txtIP.getText().length() > 0 | (int) spPuerto.getValue() > 0 | txtUsuario.getText().length() > 0
                            | txtClave.getText().length() > 0 | txtBD.getText().length() > 0) {
                        hasData = true;
                    } else {
                        hasData = false;
                    }

                    if (hasData) {
                        String[] conData = new String[5];
                        conData[0] = txtIP.getText();
                        conData[1] = spPuerto.getValue().toString();
                        conData[2] = txtUsuario.getText();
                        conData[3] = txtClave.getText();
                        conData[4] = txtBD.getText();
                        gestionarSockets.gestCon.setConData(conData);
                    }
                } finally {
                    setCursor(defaultCursor);
                    dispose();
                }
            }
        });
        btnGuardar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(selectorCursor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(defaultCursor);
            }
        });
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose();
            }
        });
        btnCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(selectorCursor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(defaultCursor);
            }
        });
    }

    // ----------------------------------------------------------------------------------------------------------------------------
    /**
     * Este metodo traduce el programa dependiendo del idioma recibido
     * 
     * @param idioma Idioma al cual se va a traducir el programa
     */
    @Deprecated
    private void traducirPrograma(String idioma) {
        try {
            File archivo = new File("./data/language/default.properties");
            FileInputStream is = new FileInputStream(archivo);
            properties.load(is);
            FileOutputStream osFile = new FileOutputStream("./data/language/default.properies");
            is.transferTo(osFile);
            properties.store(osFile, null);
            osFile.close();

            properties.load(is);
            String lang[] = String.valueOf(properties.get("LANG")).split("_");
            Locale.setDefault(new Locale(idioma));

            switch (lang[0]) {
                case "es":
                    helpURL = this.getClass().getResource("/data/help/help.hs");
                    break;
                case "gl":
                    helpURL = this.getClass().getResource("/data/help/help_gl_ES.hs");
                    break;
            }
            helpset = new HelpSet(null, helpURL);
            browser = helpset.createHelpBroker();
            browser.enableHelpKey(getContentPane(), "conexion", helpset);

            gestionarIdioma.traducirBotones(listButtons);
            gestionarIdioma.traducirEtiquetas(listLabels);
        } catch (IOException | HelpSetException e1) {
            e1.printStackTrace();
        }
    }
}
