import com.aluracursos.conversordemonedas.MenuPrincipal;

import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var opciones = new MenuPrincipal();

        while (true) {
            opciones.mostrarMenu();
            opciones.obtenerSeleccionValida(scanner);
            opciones.convirtiendo();

            System.out.println("Salir '0', Continuar '1']");
            int utilizar = scanner.nextInt();

            if (utilizar != 1){
            break;
            } else {
                opciones.setSeleccion(0);
            }
        }

    }

}