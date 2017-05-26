package me.urielsalis.projecttracker;

import com.google.gson.Gson;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * Created by urielsalis on 5/25/17.
 */
public class CrabbitsApp implements BiConsumer<TextIO, String> {
    @Override
    public void accept(TextIO textIO, String initData) {
        TextTerminal terminal = textIO.getTextTerminal();
        printGsonMessage(terminal, initData);

        boolean correctPassword = false;

        while(!correctPassword) {
            String user = textIO.newStringInputReader()
                    .read("Username");

            String password = textIO.newStringInputReader()
                    .withMinLength(6)
                    .withInputMasking(true)
                    .read("Password");

            if (user.equals("admin") && password.equals("crabbits")) {
                correctPassword = true;
            } else {
                terminal.println("Contrasena incorrecta");
            }
        }

        Menu1 menu1 = textIO.newEnumInputReader(Menu1.class)
                .read("Opcion:");
        switch (menu1) {
            case NuevoPago:{
                Proyecto proyecto = getProyecto(textIO);
                SubProyecto subProyecto = getSubProyecto(proyecto, textIO);
                Main.pagos.add(new Pago(textIO, subProyecto));}
                break;
            case NuevoProyecto:{
                Main.proyectos.add(new Proyecto(textIO));}
                break;
            case NuevoSubProyecto:{
                Proyecto proyecto = getProyecto(textIO);
                Main.subproyectos.add(new SubProyecto(textIO, proyecto));}
                break;
            case GenerarPresupuesto:{
                generarPresupuesto(textIO);}
                break;
            case GenerarReciboComision:{
                generarRecibo(textIO);}
                break;
        }

        textIO.newStringInputReader().withMinLength(0).read("\nPress enter to terminate...");
        textIO.dispose("Sesion finalizada");

    }

    private SubProyecto getSubProyecto(Proyecto proyecto, TextIO textIO) {
        List<String> strings = new ArrayList<>();
        for(SubProyecto subProyecto: Main.subproyectos) {
            if (subProyecto.proyecto.equals(proyecto)) {
                strings.add(subProyecto.nombre + "(" + subProyecto.id + ")");
            }
        }
        String selected = textIO.newStringInputReader()
                .withNumberedPossibleValues(strings)
                .read("Proyecto: ");
        for(SubProyecto subProyecto: Main.subproyectos) {
            if(selected.equals(subProyecto.nombre + "("+subProyecto.id +")")) {
                return subProyecto;
            }
        }
        return null;
    }

    private Proyecto getProyecto(TextIO textIO) {
        List<String> strings = new ArrayList<>();
        for(Proyecto proyecto: Main.proyectos) {
            strings.add(proyecto.proyecto + "("+proyecto.proyectoID+")");
        }
        String selected = textIO.newStringInputReader()
                .withNumberedPossibleValues(strings)
                .read("Proyecto: ");
        for(Proyecto proyecto: Main.proyectos) {
            if(selected.equals(proyecto.proyecto + "("+proyecto.proyectoID+")")) {
                return proyecto;
            }
        }
        return null;
    }

    private void generarPresupuesto(TextIO textIO) {
        Proyecto proyecto = getProyecto(textIO);
        List<SubProyecto> subProyectos = getSubProyectos(proyecto);
        List<Pagos> pagos = getPagos(subProyectos, textIO);
        //TODO
    }

    private List<SubProyecto> getSubProyectos(Proyecto proyecto) {
        List<SubProyecto> subProyectos = new ArrayList<>();
        for(SubProyecto subProyecto: Main.subproyectos) {
            if(subProyecto.equals(proyecto)) {
                subProyectos.add(subProyecto);
            }
        }
        return subProyectos;
    }

    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();
        new CrabbitsApp().accept(textIO, null);
    }

    public static void printGsonMessage(TextTerminal terminal, String initData) {
        if(initData != null && !initData.isEmpty()) {
            String message = new Gson().fromJson(initData, String.class);
            if(message != null && !message.isEmpty()) {
                terminal.println(message);
            }
        }
    }


}
