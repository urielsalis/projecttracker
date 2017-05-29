package me.urielsalis.projecttracker;

import com.google.gson.Gson;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
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
        boolean running = true;
        while(running) {
            Menu1 menu1 = textIO.newEnumInputReader(Menu1.class)
                    .read("Opcion:");
            switch (menu1) {
                case NuevoPago:{
                    Proyecto proyecto = getProyecto(textIO);
                    SubProyecto subProyecto = getSubProyecto(proyecto, textIO);
                    subProyecto.pagos.add(new Pago(textIO, subProyecto));}
                break;
                case NuevoProyecto:{
                    Main.proyectos.add(new Proyecto(textIO));}
                break;
                case NuevoSubProyecto:{
                    Proyecto proyecto = getProyecto(textIO);
                    proyecto.subProyectos.add(new SubProyecto(textIO, proyecto));}
                break;
                case GenerarPresupuesto:{
                    generarPresupuesto(textIO);}
                break;
                case GenerarReciboComision:{
                    generarRecibo(textIO);}
                break;
                case Salir:{
                    guardar();
                    running = false;}
                break;
            }
        }


        textIO.newStringInputReader().withMinLength(0).read("\nPress enter to terminate...");
        textIO.dispose("Sesion finalizada");

    }

    private void guardar() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("crabbits.dat"));
            out.writeObject(Main.proyectos);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generarRecibo(TextIO textIO) {
        //TODO
    }

    private SubProyecto getSubProyecto(Proyecto proyecto, TextIO textIO) {
        List<String> strings = new ArrayList<>();
        for(SubProyecto subProyecto: proyecto.subProyectos) {
            strings.add(subProyecto.nombre + "(" + subProyecto.id + ")");
        }
        String selected = textIO.newStringInputReader()
                .withNumberedPossibleValues(strings)
                .read("Proyecto: ");
        for(SubProyecto subProyecto: proyecto.subProyectos) {
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
        HashMap<SubProyecto, List<Pago>> pagos = getPagos(subProyectos);

        //TODO
    }

    private HashMap<SubProyecto, List<Pago>> getPagos(List<SubProyecto> subProyectos) {
        HashMap<SubProyecto, List<Pago>> pagos = new HashMap<>();
        for(SubProyecto subProyecto: subProyectos) {
                pagos.put(subProyecto, getPagos(subProyecto));
        }
        return pagos;
    }

    private List<Pago> getPagos(SubProyecto subProyecto) {
        List<Pago> pagos = new ArrayList<>();
        for(Pago pago: subProyecto.pagos) {
            if(pago.subProyecto.equals(subProyecto)) {
                pagos.add(pago);
            }
        }
        return pagos;

    }

    private List<SubProyecto> getSubProyectos(Proyecto proyecto) {
        List<SubProyecto> subProyectos = new ArrayList<>();
        for(SubProyecto subProyecto: proyecto.subProyectos) {
            if(subProyecto.proyecto.equals(proyecto)) {
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
