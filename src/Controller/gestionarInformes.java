package Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

public class gestionarInformes {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public gestionarInformes(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Deprecated
    public void getColeccionInformes(String col) {
        try {
            out.writeObject("informeColeccion··" + col);
            int bytes = 0;
            byte[] buffer = new byte[4 * 1024];
            File file = new File("informe.pdf");
            FileOutputStream fos = new FileOutputStream("informe.pdf");
            long size;

            size = in.readLong();
            while (size > 0
                    && (bytes = in.read(
                            buffer, 0,
                            (int) Math.min(buffer.length, size))) != -1) {
                fos.write(buffer, 0, bytes);
                size -= bytes;
            }
            fos.close();

            try {
                Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + file.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "ERROR AL CARGAR EL INFORME");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void getNombresInformes(String name) {
        try {
            out.writeObject("informeNombre··" + name);
            int bytes = 0;
            byte[] buffer = new byte[4 * 1024];
            File file = new File("informe.pdf");
            FileOutputStream fos = new FileOutputStream("informe.pdf");
            long size;

            size = in.readLong();
            while (size > 0
                    && (bytes = in.read(
                            buffer, 0,
                            (int) Math.min(buffer.length, size))) != -1) {
                fos.write(buffer, 0, bytes);
                size -= bytes;
            }
            fos.close();
            try {
                Runtime.getRuntime().exec("rundll32 url.dll, FileProtocolHandler " + file.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "ERROR AL CARGAR EL INFORME");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}