package me.urielsalis.projecttracker;

import com.google.gson.Gson;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.time.Month;
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
            case NuevoPago:
                Main.pagos.add(new Pago(textIO));
                break;
            case BorrarProyecto:
                //TODO
                break;
            case NuevoProyecto:
                Main.proyectos.add(new Proyecto(textIO));
                break;
            case NuevoSubProyecto:
                Proyecto proyecto = getProyecto();
                Main.subproyectos.add(new SubProyecto(textIO));
                break;
            case BorrarSubproyecto:
                //TODO
                break;
            case GenerarPresupuesto:
                generarPresupuesto(textIO);
                break;
            case GenerarReciboComision:
                generarRecibo(textIO);
                break;
        }

        int age = textIO.newIntInputReader()
                .withMinVal(13)
                .read("Age");

        Month month = textIO.newEnumInputReader(Month.class)
                .read("What month were you born in?");

        terminal.printf("\nUser %s is %d years old, was born in %s and has the password %s.\n", user, age, month, password);

        textIO.newStringInputReader().withMinLength(0).read("\nPress enter to terminate...");
        textIO.dispose("User '" + user + "' has left the building.");

    }

    private void generarPresupuesto(TextIO textIO) {
        Proyecto proyecto = getProyecto();
        List<SubProyectos> getSubProyectos(proyecto);
        List<Pagos> getPagos(proyecto);
        //TODO
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
