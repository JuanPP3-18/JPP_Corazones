package ar.unlu.poo;

import ar.unlu.poo.Controlador.ControladorConsola;
import ar.unlu.poo.Modelo.JuegoCorazones;
import ar.unlu.poo.Modelo.Jugador;
import ar.unlu.poo.Vista.VistaConsola;

import java.util.List;

public class MainConsola {
    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();
        List<Jugador> jugadores = vista.pedirDatosJugadores();
        JuegoCorazones juego = new JuegoCorazones(jugadores);
        juego.asignarIndicesJugadores();
        ControladorConsola controlador = new ControladorConsola(juego, vista);
        controlador.iniciarJuego();
    }
}
