package Controller;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class gestionarInformes {

    //private ObjectInputStream in;
    private ObjectOutputStream out;

    public gestionarInformes(ObjectOutputStream out) {
        //this.in = in;
        this.out = out;
    }

    public void getColeccionInformes(String col) {
        try {
            System.out.println(col);
            out.writeObject("informeColeccion··" + col);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getNombresInformes(String name) {
        try {
            out.writeObject("informeColeccion··" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}