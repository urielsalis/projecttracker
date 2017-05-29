package me.urielsalis.projecttracker;

import org.beryx.textio.TextIO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by urielsalis on 5/25/17.
 */
public class SubProyecto implements Serializable {
    Proyecto proyecto;
    String nombre;
    String id;
    List<Pago> pagos = new ArrayList<>();

    public SubProyecto(TextIO textIO, Proyecto proyecto) {
        this.proyecto = proyecto;
        nombre = textIO.newStringInputReader()
                .read("Nombre: ");
        id = textIO.newStringInputReader()
                .read("ID: ");

    }
}
