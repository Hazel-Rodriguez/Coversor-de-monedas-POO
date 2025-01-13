package com.aluracursos.conversordemonedas;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MenuPrincipal {
    private int seleccion;

    public void setSeleccion(int seleccion) {
        this.seleccion = seleccion;
    }

    //Declaracion de opciones iniciales
    public static List<String[]> conversionOptions = List.of(
            new String[]{"USD", "ARS"},
            new String[]{"ARS", "USD"},
            new String[]{"USD", "BRL"},
            new String[]{"BRL", "USD"},
            new String[]{"USD", "MXN"},
            new String[]{"MXN", "USD"}
    );

    public void mostrarMenu() {
        System.out.println("""
        --------------- MENU ---------------
        Conversor de monedas:
            1. Dollar USD a Peso Argentino
            2. Peso Argentino a Dolar USD
            3. Dolar USD a Real Brasileño
            4. Real Brasileño a Dolar USD
            5. Dolar USD a Peso Mexicano
            6. Peso Mexicano a Dolar USD""");
    }

    // Obtiene una opción válida del usuario
    public void obtenerSeleccionValida(Scanner scanner) {
        while (this.seleccion < 1 || this.seleccion > 6) {
            try {
                this.seleccion = scanner.nextInt();
                if (this.seleccion < 1 || this.seleccion > 6) {
                    System.out.println("Elije 1 y 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Número inválido");
                scanner.next();
            }
        }

    }

    public void convirtiendo (){
        if (this.seleccion >= 1 && this.seleccion <= 6) {
            String[] monedas = conversionOptions.get(this.seleccion -1);

            try {
                Conversion conversion = new Conversion();
                conversion.setMoneda(monedas[0]);
                conversion.setCambio(monedas[1]);

                conversion.obtenerEquivalencia();
                conversion.obtenerCantidad();
                conversion.obtenerConversion();
                System.out.println(conversion);
            } catch (Exception e) {
                System.out.println("Error al convertir: " + e.getMessage());
            }
        } else {
            System.out.println("Inválido");
        }
    }
}
