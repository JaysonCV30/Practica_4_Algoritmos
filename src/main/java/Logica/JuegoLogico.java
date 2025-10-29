package Logica;

import java.util.Scanner;

public class JuegoLogico {

    private EightOffGame juego;
    private Scanner scanner;

    public JuegoLogico() {
        juego = new EightOffGame();
        scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("Bienvenido a Eight Off (versión consola)");
        while (!juego.juegoTerminado()) {
            mostrarEstado();
            mostrarOpciones();
            procesarEntrada();
        }
        System.out.println("¡Felicidades! Has ganado el juego.");
    }

    private void mostrarEstado() {
        System.out.println("\n--- Estado actual del juego ---");

        // Mostrar celdas libres
        System.out.print("Celdas libres: ");
        for (int i = 0; i < 8; i++) {
            Carta c = juego.getCeldas().ver(i); 
            System.out.print((c != null ? c : "[ ]") + " ");
        }
        System.out.println();

        // Mostrar fundaciones
        System.out.print("Fundaciones: ");
        for (int i = 0; i < 4; i++) {
            Carta cima = juego.getFundaciones()[i].verTope();
            System.out.print((cima != null ? cima : "[ ]") + " ");
        }
        System.out.println();

        // Mostrar columnas
        for (int i = 0; i < 8; i++) {
            System.out.print("Columna " + i + ": ");
            var nodo = juego.getColumnas()[i].getCartas().getInicio();
            while (nodo != null) {
                System.out.print(nodo.getInfo() + " ");
                nodo = nodo.getSig();
            }
            System.out.println();
        }
    }

    private void mostrarOpciones() {
        System.out.println("\nOpciones:");
        System.out.println("1. Mover carta");
        System.out.println("2. Deshacer ultimo movimiento");
        System.out.println("3. Pedir pista");
        System.out.println("4. Salir");
        System.out.print("Selecciona una opcion: ");
    }

    private void procesarEntrada() {
        int opcion = scanner.nextInt();
        switch (opcion) {
            case 1:
                moverCarta();
                break;
            case 2:
                juego.deshacerUltimoMovimiento();
                System.out.println("Movimiento deshecho.");
                break;
            case 3:
                Carta pista = juego.darPista();
                if (pista != null) {
                    System.out.println("Puedes mover: " + pista);
                } else {
                    System.out.println("No hay movimientos validos.");
                }
                break;
            case 4:
                System.out.println("Gracias por jugar.");
                System.exit(0);
                break;
            default:
                System.out.println("Opcion invalida.");
        }
    }

    private void moverCarta() {
        System.out.println("Movimiento de carta");
        System.out.print("Origen (columna 0-7, celda 8-15, fundacion 16-19): ");
        int origen = scanner.nextInt();
        System.out.print("Destino (columna 0-7, celda 8-15, fundacion 16-19): ");
        int destino = scanner.nextInt();

        boolean exito = false;

        if (origen >= 0 && origen < 8) {
            Carta carta = juego.getColumnas()[origen].verUltimaCarta();
            if (carta != null) {
                exito = juego.moverCartaDesdeColumna(destino, carta);
            }
        } else if (origen >= 8 && origen < 16) {
            Carta carta = juego.getCeldas().ver(origen - 8);
            if (carta != null) {
                exito = juego.moverCartaDesdeCelda(destino, carta);
            }
        } else if (origen >= 16 && origen < 20) {
            Carta carta = juego.getFundaciones()[origen - 16].verTope();
            if (carta != null) {
                exito = juego.moverCartaDesdeFundacion(destino, carta);
            }
        }

        if (exito) {
            System.out.println("Movimiento realizado.");
        } else {
            System.out.println("Movimiento invalido.");
        }
    }
}
