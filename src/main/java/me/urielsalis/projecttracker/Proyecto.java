package me.urielsalis.projecttracker;

import org.beryx.textio.TextIO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by urielsalis on 5/25/17.
 */
public class Proyecto implements Serializable {
    String clienteName;
    String clienteDireccion;
    String clienteDirecion2;
    String contacto;
    String proyecto;
    String contactotel;
    String proyectoID;
    List<SubProyecto> subProyectos = new ArrayList<>();


    public Proyecto(TextIO textIO) {
        clienteName = textIO.newStringInputReader()
                .read("Nombre: ");
        clienteDireccion = textIO.newStringInputReader()
                .read("Direccion: ");
        clienteDirecion2 = textIO.newStringInputReader()
                .read("Direccion(2): ");
        contacto = textIO.newStringInputReader()
                .read("Contacto: ");
        contactotel = textIO.newStringInputReader()
                .read("Contacto Tel: ");
        proyecto = textIO.newStringInputReader()
                .read("Proyecto: ");
        proyectoID = textIO.newStringInputReader()
                .read("Proyecto ID");

    }
}
