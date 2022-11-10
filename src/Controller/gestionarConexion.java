package Controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;

public class gestionarConexion implements Serializable {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public gestionarConexion(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void startConnection() {
        try {
            out.writeObject("startConnection");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endConnection() {
        try {
            out.writeObject("endConnection");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            out.writeObject("getConnection");
            Connection con = (Connection) in.readObject();
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
