package View;

//-------- IMPORTS ------------------------------------------------------------
//-------- PAQUETES -----------------------------------------------------------
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;

import javax.help.*;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.*;
import javax.swing.GroupLayout.*;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

//-------- CLASES -------------------------------------------------------------
import Model.*;
import Controller.*;

public class menuComics extends JDialog {

    // ----------- Panel --------------------------------------------------------
    private final JPanel contentPanel = new JPanel();
    // ----------- Botones ------------------------------------------------------
    private JButton btnCancelar;
    private JButton btnGuardar;
    // ----------- Campos de Texto ----------------------------------------------
    private JTextField txtTitulo;
    private JTextField txtPortada;
    private JSpinner spCantidad;
    private JSpinner spFecha;
    // ----------- Cursores -----------------------------------------------------
    private Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
    private Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    private Cursor selectorCursor = new Cursor(Cursor.HAND_CURSOR);
    // ----------- Etiquetas -----------------------------------------------------
    private JLabel lblFecha;
    private JLabel lblColección;
    private JLabel lblTitulo;
    private JLabel lblStock;
    private JLabel lblPrecio;
    private JLabel lblEstado;
    private JLabel lblPortadaComic;
    // ----------- Listas -------------------------------------------------------
    private static ArrayList<JLabel> listLabels = new ArrayList<>();
    private static ArrayList<JButton> listButtons = new ArrayList<>();
    // ----------- Otros --------------------------------------------------------
    static String titulo;
    static String imgPath;
    static Comics comic;

    private HelpSet helpset = null;
    private HelpBroker browser = null;
    private static URL helpURL;
    public static Locale language;
    private static Properties properties = new Properties();
    FileNameExtensionFilter pngFilter = new FileNameExtensionFilter("*.png", "png");
    FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter("*.jpg", "jpg");
    FileNameExtensionFilter jpegFilter = new FileNameExtensionFilter("*.jpeg", "jpeg");

    /**
     * Launch the application.
     */
    public static void iniciar(Comics comicc, String tituloo) {
        try {
            comic = comicc;
            titulo = tituloo;
            menuComics dialog = new menuComics();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public menuComics() {
        setMinimumSize(new Dimension(540, 600));
        setModal(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(menuComics.class.getResource("/images/icons/pencil.png")));
        setTitle(titulo);
        setBounds(100, 100, 540, 600);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        lblTitulo = new JLabel("Titulo del Cómic");
        lblTitulo.setName("lblTitulo");

        lblFecha = new JLabel("Fecha de Adquisición");
        lblFecha.setName("lblFecha");

        lblColección = new JLabel("Colección del cómic");
        lblColección.setName("lblColeccion");

        lblStock = new JLabel("Cantidad en stock");
        lblStock.setName("lblStock");

        txtTitulo = new JTextField();
        txtTitulo.setColumns(10);

        JComboBox<Colecciones> cmbColeccion = new JComboBox<Colecciones>();

        spCantidad = new JSpinner();
        spCantidad.setModel(new SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));

        spFecha = new JSpinner();
        spFecha.setModel(new SpinnerDateModel(new Date(1665698400000L), null, null, Calendar.DAY_OF_YEAR));

        JLabel lblTituloPestanha = new JLabel(titulo);
        lblTituloPestanha.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloPestanha.setFont(new Font("Tahoma", Font.PLAIN, 30));

        Image image = new ImageIcon(
                menuComics.class.getResource("/images/icons/pencil.png")).getImage().getScaledInstance(
                        50,
                        50,
                        java.awt.Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(image);
        lblTituloPestanha.setIcon(imageIcon);
        lblTituloPestanha.setText(titulo);

        lblPrecio = new JLabel("Precio del Cómic");
        lblPrecio.setName("lblPrecio");

        lblEstado = new JLabel("Estado del Cómic");
        lblEstado.setName("lblEstado");

        JComboBox<String> cmbEstado = new JComboBox<String>();
        cmbEstado.setModel(new DefaultComboBoxModel<String>(new String[] { "Buen Estado", "Seminuevo", "Roto" }));

        JSpinner spPrecio = new JSpinner();
        spPrecio.setModel(new SpinnerNumberModel(Float.valueOf(0), null, null, Float.valueOf(1)));

        JLabel lblPortada = new JLabel("Portada del Cómic");
        lblPortada.setName("lblEstado");

        txtPortada = new JTextField();
        txtPortada.setText("placeholder.png");
        txtPortada.setEditable(false);
        txtPortada.setColumns(10);

        JButton btnPortada = new JButton("...");

        GroupLayout gl_contentPanel;

        lblPortadaComic = new JLabel("");
        lblPortadaComic.setHorizontalAlignment(SwingConstants.CENTER);
        lblPortadaComic.setIcon(new ImageIcon(menuComics.class.getResource("/images/icons/no_image.png")));
        {
            gl_contentPanel = new GroupLayout(contentPanel);
            gl_contentPanel.setHorizontalGroup(
                    gl_contentPanel.createParallelGroup(Alignment.TRAILING)
                            .addGroup(gl_contentPanel.createSequentialGroup()
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                            .addGroup(gl_contentPanel.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(lblTituloPestanha, GroupLayout.DEFAULT_SIZE, 494,
                                                            Short.MAX_VALUE))
                                            .addGroup(gl_contentPanel.createSequentialGroup()
                                                    .addGap(6)
                                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                                            .addGroup(gl_contentPanel.createSequentialGroup()
                                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                                    .addGroup(gl_contentPanel
                                                                            .createParallelGroup(Alignment.TRAILING,
                                                                                    false)
                                                                            .addComponent(lblFecha, Alignment.LEADING,
                                                                                    GroupLayout.DEFAULT_SIZE,
                                                                                    GroupLayout.DEFAULT_SIZE,
                                                                                    Short.MAX_VALUE)
                                                                            .addComponent(lblTitulo, Alignment.LEADING,
                                                                                    GroupLayout.DEFAULT_SIZE, 208,
                                                                                    Short.MAX_VALUE)
                                                                            .addComponent(lblStock,
                                                                                    GroupLayout.PREFERRED_SIZE, 216,
                                                                                    GroupLayout.PREFERRED_SIZE)))
                                                            .addGroup(gl_contentPanel.createSequentialGroup()
                                                                    .addGap(6)
                                                                    .addGroup(gl_contentPanel
                                                                            .createParallelGroup(Alignment.TRAILING)
                                                                            .addComponent(lblPrecio,
                                                                                    GroupLayout.DEFAULT_SIZE, 220,
                                                                                    Short.MAX_VALUE)
                                                                            .addComponent(lblEstado,
                                                                                    GroupLayout.DEFAULT_SIZE, 220,
                                                                                    Short.MAX_VALUE)
                                                                            .addGroup(gl_contentPanel
                                                                                    .createSequentialGroup()
                                                                                    .addPreferredGap(
                                                                                            ComponentPlacement.RELATED)
                                                                                    .addComponent(lblColección,
                                                                                            GroupLayout.DEFAULT_SIZE,
                                                                                            220, Short.MAX_VALUE))
                                                                            .addComponent(lblPortada, Alignment.LEADING,
                                                                                    GroupLayout.PREFERRED_SIZE, 220,
                                                                                    GroupLayout.PREFERRED_SIZE))))
                                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
                                                            .addComponent(lblPortadaComic, GroupLayout.DEFAULT_SIZE,
                                                                    262, Short.MAX_VALUE)
                                                            .addComponent(cmbColeccion, 0, 262, Short.MAX_VALUE)
                                                            .addComponent(spCantidad, GroupLayout.DEFAULT_SIZE, 262,
                                                                    Short.MAX_VALUE)
                                                            .addComponent(spFecha, GroupLayout.DEFAULT_SIZE, 262,
                                                                    Short.MAX_VALUE)
                                                            .addComponent(txtTitulo, GroupLayout.DEFAULT_SIZE, 262,
                                                                    Short.MAX_VALUE)
                                                            .addComponent(cmbEstado, 0, 262, Short.MAX_VALUE)
                                                            .addComponent(spPrecio, GroupLayout.DEFAULT_SIZE, 262,
                                                                    Short.MAX_VALUE)
                                                            .addGroup(gl_contentPanel.createSequentialGroup()
                                                                    .addComponent(txtPortada, GroupLayout.DEFAULT_SIZE,
                                                                            207, Short.MAX_VALUE)
                                                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                                                    .addComponent(btnPortada)))))
                                    .addContainerGap()));
            gl_contentPanel.setVerticalGroup(
                    gl_contentPanel.createParallelGroup(Alignment.TRAILING)
                            .addGroup(gl_contentPanel.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(lblTituloPestanha, GroupLayout.PREFERRED_SIZE, 89,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(lblTitulo)
                                            .addComponent(txtTitulo, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
                                            .addComponent(lblFecha)
                                            .addComponent(spFecha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                                    GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(spCantidad, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblStock))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(cmbColeccion, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblColección))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(lblPrecio)
                                            .addComponent(spPrecio, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(lblEstado)
                                            .addComponent(cmbEstado, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
                                            .addComponent(lblPortada)
                                            .addComponent(txtPortada, GroupLayout.PREFERRED_SIZE,
                                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnPortada))
                                    .addGap(18)
                                    .addComponent(lblPortadaComic, GroupLayout.PREFERRED_SIZE, 205,
                                            GroupLayout.PREFERRED_SIZE)));
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
                listLabels.add(lblColección);
                listLabels.add(lblEstado);
                listLabels.add(lblFecha);
                listLabels.add(lblPrecio);
                listLabels.add(lblStock);
                listLabels.add(lblTitulo);

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
                    browser.enableHelpKey(getContentPane(), "crear_modificar", helpset);
                } catch (HelpSetException | IOException ex1) {
                    ex1.printStackTrace();
                }
                // --------------------------------------------------------------------
                // Aquí añado los datos a los campos de texto
                // --------------------------------------------------------------------
                if (comic != null) {
                    try {
                        gestionarSockets.gestCon.startConnection();
                        ArrayList<Colecciones> listaColecciones = gestionarSockets.gestCol.listarColecciones();

                        Colecciones col = gestionarSockets.gestCol.obtenerColeccion(comic);
                        gestionarSockets.gestCon.endConnection();

                        for (Colecciones c : listaColecciones) {
                            cmbColeccion.addItem(c);
                        }

                        for (int i = 0; i < cmbColeccion.getItemCount(); i++) {
                            cmbColeccion.setSelectedIndex(i);
                            Colecciones c = (Colecciones) cmbColeccion.getSelectedItem();
                            if (c.getNum_coleccion() == comic.getNum_col()) {
                                break;
                            }
                        }
                        for (int i = 0; i < cmbEstado.getItemCount(); i++) {
                            cmbEstado.setSelectedIndex(i);
                            String estado = cmbEstado.getSelectedItem().toString();
                            if (estado.length() == comic.getEstado().length()) {
                                break;
                            }
                        }

                        txtTitulo.setText(comic.getTitulo());
                        spCantidad.setValue(comic.getCantidad());
                        spFecha.setValue(comic.getFecha());
                        spPrecio.setValue(comic.getPrecio());
                        Blob blob = comic.getImagen();

                        ImageIcon imageIcon = blobToImgIcon(blob);

                        lblPortadaComic.setIcon(imageIcon);
                        lblColección.setText(col.getTitulo());

                        txtTitulo.setEditable(false);
                        cmbColeccion.setEnabled(false);
                    } catch (Exception ex2) {
                        ex2.printStackTrace();
                    } finally {
                        setCursor(defaultCursor);
                    }
                } else {
                    try {
                        comic = new Comics();
                        gestionarSockets.gestCon.startConnection();
                        ArrayList<Colecciones> listaColecciones = gestionarSockets.gestCol.listarColecciones();
                        gestionarSockets.gestCon.endConnection();
                        for (Colecciones c : listaColecciones) {
                            cmbColeccion.addItem(c);
                        }
                    } catch (Exception ex2) {
                        ex2.printStackTrace();
                    } finally {
                        setCursor(defaultCursor);
                    }
                }
            }
        });
        // ----------------------------------------------------------------------------------------------------------------------------
        // ----------- OTROS
        btnPortada.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String userDir = System.getProperty("user.home");
                    JFileChooser fChser = new JFileChooser(userDir + "/Desktop");
                    fChser.setAcceptAllFileFilterUsed(false);
                    fChser.setFileFilter(pngFilter);
                    fChser.setFileFilter(jpgFilter);
                    fChser.setFileFilter(jpegFilter);
                    fChser.setCurrentDirectory(new File(""));
                    int opcion = fChser.showOpenDialog(contentPanel);
                    if (opcion == JFileChooser.APPROVE_OPTION) {
                        File archivo = fChser.getSelectedFile();
                        imgPath = archivo.getAbsolutePath();
                        byte[] byteBlob = Files.readAllBytes(archivo.toPath());
                        Blob img = new SerialBlob(byteBlob);
                        comic.setImagen(img);

                        ImageIcon imageIcon = blobToImgIcon(img);

                        lblPortadaComic.setIcon(imageIcon);
                        txtPortada.setText(archivo.getName());
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (SQLException | IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (txtTitulo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No has escrito un titulo", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                } else if (comic.getImagen() == null) {
                    JOptionPane.showMessageDialog(null, "No has seleccionado una portada", "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    if (txtTitulo.getText().contains("··")) {
                        JOptionPane.showMessageDialog(null,
                                "No puedes utilizar [··] en el titulo",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        setCursor(waitCursor);

                        comic.setTitulo(txtTitulo.getText());
                        comic.setCantidad((int) spCantidad.getValue());
                        comic.setPrecio((Float) spPrecio.getValue());
                        comic.setEstado(cmbEstado.getSelectedItem().toString());
                        java.util.Date fechaU = (java.util.Date) spFecha.getValue();
                        java.sql.Date fechaS = new java.sql.Date(fechaU.getTime());
                        comic.setFecha(fechaS);
                        Colecciones col = (Colecciones) cmbColeccion.getSelectedItem();
                        comic.setNum_col(col.getNum_coleccion());

                        if (titulo == "Crear Cómic") {
                            gestionarSockets.gestCon.startConnection();
                            boolean existe = gestionarSockets.gestCom.getComic(comic.getTitulo());
                            gestionarSockets.gestCon.endConnection();
                            if (existe) {
                                JOptionPane.showMessageDialog(null,
                                        "Ya existe un comic con el titulo " + comic.getTitulo(), "ERROR", 0);
                            } else {
                                File imagen = new File(imgPath);

                                gestionarSockets.gestCon.startConnection();
                                gestionarSockets.gestCom.addComic(comic, imagen);
                                gestionarSockets.gestCon.endConnection();
                            }
                        } else {
                            gestionarSockets.gestCon.startConnection();
                            if (imgPath == null) {
                                gestionarSockets.gestCom.updateComicNoImage(comic);
                            } else {
                                File imagen = new File(imgPath);
                                gestionarSockets.gestCom.updateComic(comic, imagen);
                            }
                            gestionarSockets.gestCon.endConnection();
                        }
                        setCursor(defaultCursor);
                        dispose();

                    }
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
    };

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
            properties.setProperty("LANG", String.valueOf(idioma));
            FileOutputStream osFile = new FileOutputStream("./data/language/default.properties");
            properties.store(osFile, null);
            osFile.close();

            properties.load(is);
            String lang[] = String.valueOf(properties.get("LANG")).split("_");
            Locale.setDefault(new Locale(idioma));
            language = new Locale(lang[0], lang[1]);

            Locale.setDefault(language);

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
            browser.enableHelpKey(getContentPane(), "crear_modificar", helpset);

            gestionarIdioma.traducirBotones(listButtons);
            gestionarIdioma.traducirEtiquetas(listLabels);
        } catch (IOException | HelpSetException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 
     * @param img El blob de la BD que contiene el icono a convertir
     * @return Devuelve la imagen como icono para las etiquetas
     */
    private ImageIcon blobToImgIcon(Blob img) {
        ImageIcon imageIcon = null;
        try {
            int blobLength = (int) img.length();
            byte[] blobAsBytes = img.getBytes(1, blobLength);
            final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(blobAsBytes));
            imageIcon = new ImageIcon(bufferedImage);
            Image image = imageIcon.getImage();
            Image newimg = image.getScaledInstance(lblPortadaComic.getWidth(),
                    lblPortadaComic.getHeight(),
                    java.awt.Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newimg);
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
        return imageIcon;
    }
}
