package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import View.menuPrincipal;

public class gestionarSockets {
    public static gestionarComics gestCom;
    public static gestionarConexion gestCon;
    public static gestionarColecciones gestCol;
    public static gestionarInformes gestInf;
    static Socket socket;
    static ObjectInputStream in = null;
    static ObjectOutputStream out = null;

    public static void main(String[] args) {

        try {
            socket = new Socket("localhost", 5000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            gestCom = new gestionarComics(in, out);
            gestCol = new gestionarColecciones(in, out);

            gestCon = new gestionarConexion(in, out);

            gestInf = new gestionarInformes(out);
            menuPrincipal frame = new menuPrincipal();
            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void cerrarServidor() {
        try {
            out.writeObject("fin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
