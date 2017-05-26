package me.urielsalis.projecttracker;

import org.beryx.textio.TextIO;

/**
 * Created by urielsalis on 5/25/17.
 */
public class SubProyecto {
    Proyecto proyecto;
    String nombre;
    String id;

    public SubProyecto(TextIO textIO, Proyecto proyecto) {
        this.proyecto = proyecto;
        nombre = textIO.newStringInputReader()
                .read("Nombre: ");
        id = textIO.newStringInputReader()
                .read("ID: ");

    }
}
