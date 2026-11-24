package ar.unlu.poo.Vista;

import ar.unlu.poo.Modelo.Carta;
import ar.unlu.poo.Modelo.Jugada;
import ar.unlu.poo.Modelo.Jugador;
import ar.unlu.poo.Modelo.Palo;
import com.sun.security.jgss.GSSUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VistaConsola {
    private final Scanner sc;

    public VistaConsola() {
        this.sc = new Scanner(System.in);
    }

    public List<Jugador> pedirDatosJugadores(){
        int cantidadJugadores = 4;
        System.out.println("--------BIENVENIDO AL JUEGO 'CORAZONES'---------");

        List<Jugador> jugadores = new ArrayList<>();
        for (int i = 1; i <= cantidadJugadores; i++){
            String nombre = pedirNombreJugador(i);
            jugadores.add(new Jugador(nombre));
        }
        return jugadores;
    }

    public String pedirNombreJugador(int numeroJugador){
        System.out.print("Ingrese el nombre del jugador N°" + numeroJugador + ": ");
        String nombre = sc.nextLine().trim();
        while (nombre.isEmpty()) {
            System.out.print("El nombre no puede estar vacío. Ingrese nuevamente: ");
            nombre = sc.nextLine().trim();
        }
        return nombre;
    }

    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);
    }

    public void mostrarJugadores(List<Jugador> jugadores){
        System.out.println("\nLos jugadores actuales de la partida son:");
        int i = 1;
        for (Jugador j : jugadores){
            System.out.println("Jugador N°" + i + ": " + j.getNombre());
            i++;
        }
    }

    public void mostrarMano(Jugador jugador){
        System.out.println("");
        System.out.println("Las cartas del jugador " + jugador.getNombre() + " son las siguientes: \n");
        List<Carta> manoJugador = jugador.getMano();
        int i = 1;
        for (Carta c : manoJugador){
            System.out.println(i + ". " + c.getValor() + "(" + c.getValoresNumericos() + ")" + " de " + c.getPalo());
            i++;
        }
    }

    public void mostrarCartasGanadas(Jugador jugador){
        System.out.println("Las cartas ganadas del jugador " + jugador.getNombre() + " son las siguientes: \n");
        List<Carta> cartasJugadorGanadas = jugador.getCartasGanadas();
        for (Carta c : cartasJugadorGanadas){
            System.out.println(c.getValor() + "(" + c.getValoresNumericos() + ")" + " de " + c.getPalo());
        }
    }

    public void mostrarPaloDeSalida(Palo paloSalida){
        if (paloSalida == null){
            System.out.println("Todavia no hay palo de salida");
        }
        else {
            System.out.println("El palo de salida es: " + paloSalida);
        }
    }

    public void mostrarTurnoDe(Jugador jugador){
        System.out.println("Es el turno de " + jugador.getNombre() + "...");
    }

    public void mostrarPuntajeDeJugadorTotal(Jugador jugador, int puntos){
        System.out.println("El puntaje total del jugador " + jugador.getNombre() + "es de " + puntos + " puntos");
        System.out.println("|       " + jugador.getNombre() + "       |        " + puntos + "            |");
    }

    public void mostrarGanadorDePartida(Jugador jugador){
        System.out.println("El ganador de la partida es el jugador" + jugador.getNombre());
    }

    public void mostrarPerdedorDePartida(Jugador jugador){
        System.out.println("El perdedor de la partida es el jugador " + jugador.getNombre());
    }

    public void mostrarTablaJugadores(List<Jugador> tablaPosiciones){
        int i = 1;
        for (Jugador j : tablaPosiciones){
            System.out.println("Pos. " + i + ": " + j.getNombre());
            i++;
        }
    }

    public int pedirIndiceCarta(Jugador jugador, int cantidadCartas){
        int opcion = -1;
        boolean valido = false;

        while (!valido) {
            System.out.println("Turno de " + jugador.getNombre() + " - seleccione un número de carta para tirar: ");
            String entrada = sc.nextLine().trim();

            try {
                opcion = Integer.parseInt(entrada);
                if (opcion < 1 || opcion > cantidadCartas) {
                    System.out.println("Número fuera de rango. Intente nuevamente.");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número.");
            }
        }
        return opcion-1;
    }

    public void mostrarCartasEnMesa(Map<Jugador, Carta> cartasEnMesa) {
        if (cartasEnMesa == null || cartasEnMesa.isEmpty()) {
            System.out.println("No hay cartas en la mesa.");
            return;
        }

        System.out.println("--- Cartas en la mesa ---");
        for (Map.Entry<Jugador, Carta> entrada : cartasEnMesa.entrySet()) {
            Jugador jugador = entrada.getKey();
            Carta carta = entrada.getValue();
            System.out.println(jugador.getNombre() + " jugo: " + carta);
        }
    }

    public void mostrarPuntosRonda(Jugador jugador, int puntos){
        System.out.println("|       " + jugador.getNombre() + "       |        " + puntos + "       |");
    }

    public String limpiarConsola(String mensaje){
        System.out.print(mensaje);
        String entrada = sc.nextLine();
        // Si querés limpiar solo cuando ingresaron algo (no vacío):
        if (!entrada.trim().isEmpty()) {
            for (int i = 0; i < 40; i++) {
                System.out.println();
            }
        }
        return entrada;
    }

    public void mostrarCartaJugada(Jugador jugador, Carta carta) {
        System.out.println("El jugador " + jugador.getNombre() + " jugo la carta: " + carta.getValoresNumericos() + " de " + carta.getPalo());
    }
}

