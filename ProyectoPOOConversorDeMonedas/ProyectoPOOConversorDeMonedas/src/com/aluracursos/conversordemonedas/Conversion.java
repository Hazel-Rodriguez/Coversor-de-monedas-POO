package com.aluracursos.conversordemonedas;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Conversion {
    private String moneda;
    private String cambio;
    private Double equivale;
    private Double cantidad;
    private Double resultado;


    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public void obtenerEquivalencia() {

        String url = "https://v6.exchangerate-api.com/v6/d769618c8dc8861db000ff99/pair/" + this.moneda;

        try {
            if (this.moneda == null || this.cambio == null) {
                throw new IllegalArgumentException("Datos no deben ser nulos.");
            }

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error al conectar con la API. Estado: " + response.statusCode());
            }

            var json = response.body();
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            if (!jsonObject.has("conversion_rates")) {
                throw new RuntimeException("La respuesta de la API no contiene 'conversion_rates'.");
            }

            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

            if (!conversionRates.has(this.cambio)) {
                throw new RuntimeException("Tasa de cambio '" + this.cambio + "' no disponible");
            }

            this.equivale = conversionRates.get(this.cambio).getAsDouble();

        } catch (IllegalArgumentException e) {
            System.out.println("Error de validación: " + e.getMessage());
            throw e;

        } catch (RuntimeException e) {
            System.out.println("Error al procesar los datos de la API: " + e.getMessage());
            throw e;

        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void obtenerCantidad() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Ingrese la cantidad de [" + this.moneda + "] a convertir en [" + this.cambio + "]: ");
                this.cantidad = scanner.nextDouble();


                if (this.cantidad <= 0) {
                    System.out.println("Error: La cantidad debe ser mayor a cero.");
                    continue;
                }

                break;

            } catch (InputMismatchException e) {
                System.out.println("Error: Ingrese un número válido.");
                scanner.next();
            }
        }
    }


    public void obtenerConversion() {
        if (this.cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        if (this.equivale <= 0) {
            throw new IllegalArgumentException("La equivale debe ser mayor a cero.");
        }

        this.resultado = Math.round(this.cantidad * this.equivale * 100.0) / 100.0;
    }


    @Override
    public String toString() {
        return this.cantidad + " [" + this.moneda + "] " + "convertido a [" + this.cambio + "]" + " es igual a: " + this.resultado + "[" + this.cambio + "]";
    }
}
