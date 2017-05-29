package me.urielsalis.projecttracker;

import org.beryx.textio.TextIO;

import java.io.Serializable;

/**
 * Created by urielsalis on 5/25/17.
 */
public class Pago implements Serializable {
    String fecha;
    String descripcion;
    int precio;
    SubProyecto subProyecto;
    public Pago(TextIO textIO, SubProyecto subProyecto) {
        this.subProyecto = subProyecto;
        fecha = textIO.newStringInputReader()
                .read("Fecha: ");
        descripcion = textIO.newStringInputReader()
                .read("Descripcion: ");
        precio = textIO.newIntInputReader()
                .read("Precio: ");

    }
}
