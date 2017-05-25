package me.urielsalis.projecttracker;

import org.beryx.textio.TextIO;

/**
 * Created by urielsalis on 5/25/17.
 */
public class Proyecto {
    String clienteName;
    String clienteDireccion;
    String clienteDirecion2;
    String contacto;
    String proyecto;
    String contactotel;
    String proyectoID;


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
