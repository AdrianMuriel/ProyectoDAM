package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Model.Comics;

public class gestionarComics {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public gestionarComics(ObjectInputStream in, ObjectOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Comics> listarComics() {
        try {
            out.writeObject("ListaComics");
            ArrayList<Comics> listaComics = (ArrayList<Comics>) in.readObject();
            return listaComics;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean getComic(String titulo) {
        try {
            out.writeObject("ObtenerComic··" + titulo);
            int existe = (int) in.readObject();
            return (existe > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public int removeComic(Comics c) {
        try {
            out.writeObject("EliminarComic··" + c.getTitulo() + "··" + c.getNum_col());
            int result = (int) in.readObject();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int addComic(Comics c, File imagen) {
        try {
            out.writeObject(
                    "InsertarComic··" +
                            c.getTitulo() + "··" +
                            c.getNum_col() + "··" +
                            c.getPrecio() + "··" +
                            c.getCantidad() + "··" +
                            c.getFecha() + "··" +
                            c.getEstado() + "··");
            int bytes = 0;
            FileInputStream fis = new FileInputStream(imagen);
            out.writeLong(imagen.length());
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
                out.flush();
            }

            fis.close();
            int result = (int) in.readObject();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateComic(Comics c, File imagen) {
        try {
            out.writeObject(
                    "ModificarComic··" +
                            c.getTitulo() + "··" +
                            c.getNum_col() + "··" +
                            c.getPrecio() + "··" +
                            c.getCantidad() + "··" +
                            c.getFecha() + "··" +
                            c.getEstado());
            int bytes = 0;
            FileInputStream fis = new FileInputStream(imagen);
            out.writeLong(imagen.length());
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytes);
                out.flush();
            }

            fis.close();
            int result = (int) in.readObject();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int updateComicNoImage(Comics c) {
        try {
            out.writeObject(
                    "ModificarComicNoImage··" +
                            c.getTitulo() + "··" +
                            c.getNum_col() + "··" +
                            c.getPrecio() + "··" +
                            c.getCantidad() + "··" +
                            c.getFecha() + "··" +
                            c.getEstado());
            int result = (int) in.readObject();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Comics searchComic(String titulo) {
        try {
            out.writeObject("BuscarComic··" + titulo);
            Comics result = (Comics) in.readObject();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
