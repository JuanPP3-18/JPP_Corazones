package ar.unlu.poo.Controlador;

import ar.unlu.poo.Modelo.*;
import ar.unlu.poo.Observer.IObservable;
import ar.unlu.poo.Observer.IObservador;
import ar.unlu.poo.Vista.VistaConsola;

import javax.naming.ldap.Control;
import java.util.List;
import java.util.Map;

public class ControladorConsola implements IObservador {
    private VistaConsola vista;
    private JuegoCorazones juego;

    public ControladorConsola(JuegoCorazones juego, VistaConsola vista){
        this.vista = vista;
        this.juego = juego;
        this.juego.agregarObservador(this);
    }

    public void iniciarJuego(){
        while (!juego.hayGanadorPartida()){
            juego.iniciarNuevaRonda();
            int indiceJugador = juego.determinarIndiceJugadorInicialRonda();
            juego.setIndiceJugadorActual(indiceJugador);
            vista.mostrarJugadores(juego.getJugadores());
            while (!juego.esFinDePartida()){
                Jugador jugador = juego.getJugadorActual();
                Carta carta;
                do {
                    vista.mostrarMano(jugador);
                    int indice = vista.pedirIndiceCarta(jugador, jugador.getMano().size());
                    carta = jugador.getCarta(indice);
                    if (!juego.esJugadaValida(jugador, carta)) {
                        vista.mostrarMensaje("La carta jugada no es valida");
                    }
                } while (!juego.esJugadaValida(jugador, carta));
                juego.jugarTurno(jugador,carta);

                if (juego.esFinDeMano()){
                    juego.procesarFinDeMano();
                }
            }
            juego.finalizarRonda();
        }
        //--------LIMPIO CONSOLA----------
        String respuesta = vista.limpiarConsola("Ingrese una tecla para limpiar la consola...");

    }

    public void manejarCartaJugada(){
        Jugador jugador = juego.getUltimoJugadorQueJugo();
        Carta carta = juego.getUltimaCartaJugada();
        if (jugador != null && carta != null) {
            vista.mostrarCartaJugada(jugador, carta);
        }
    }

    public void manejarAvanceTurno(){
        Jugador actual = juego.getJugadorActual();
        if (actual != null) {
            vista.mostrarTurnoDe(actual);
            //muestro el palo de salida
            Palo paloSalida = juego.getPaloSalida();
            vista.mostrarPaloDeSalida(paloSalida);

        }
    }

    public void manejarFinMano(){

        //muestro las cartas que jugo cada jugador
        Mesa mesa = juego.getMesa();
        Map<Jugador, Carta> cartasJugadasEnMesa = mesa.getCartasEnMesa();
        vista.mostrarCartasEnMesa(cartasJugadasEnMesa);

        //muestro el puntaje de cada jugador obtenido en la ronda
        vista.mostrarMensaje(" _____________________________________");
        vista.mostrarMensaje("|     JUGADORES     |     PUNTAJE     |");
        for(Jugador j : juego.getJugadores()){
            int puntosPorMano = j.getPuntaje().getPuntajeRonda();
            vista.mostrarPuntosRonda(j, puntosPorMano);
        }
        vista.mostrarMensaje("|_____________________________________|");
    }

    public void manejarFinRonda(){
        vista.mostrarMensaje("--- Fin de la ronda ---");
        vista.mostrarMensaje(" _____________________________________");
        vista.mostrarMensaje("|     JUGADORES     |     PUNTAJE TOTAL     |");
        for (Jugador jugador : juego.getJugadores()) {
            int total = jugador.getPuntaje().getPuntajeTotal();
            vista.mostrarPuntajeDeJugadorTotal(jugador, total);
        }
        for (Jugador j : juego.getJugadores()) {
            vista.mostrarCartasGanadas(j);
        }

    }

    public void manejarJuegoTerminado(){
        //--------SE MUESTRA EL GANADOR--------------
        vista.mostrarMensaje("HAY UN GANADOR DE LA PARTIDA!!!");
        Jugador ganador = juego.getJugadorGanadorPartida();
        vista.mostrarGanadorDePartida(ganador);

        //---------SE MUESTRA EL PERDEDOR-------------
        vista.mostrarMensaje("\n");
        Jugador perdedor = juego.getJugadorPerdedorPartida();
        vista.mostrarPerdedorDePartida(perdedor);

        //--------SE MUESTRA EL LISTADO DE JUGADORES
        List<Jugador> tablaPosicionesJugadores = juego.tablaDeJugadoresGanadores();
        vista.mostrarTablaJugadores(tablaPosicionesJugadores);

    }


    @Override
    public void actualizar(Evento evento) {
        switch (evento){
            case CARTA_JUGADA -> manejarCartaJugada();
            case CORAZONES_JUGADOS -> vista.mostrarMensaje("¡Se jugaron corazones!");
            case AVANCE_TURNO -> manejarAvanceTurno();
            case FIN_MANO -> manejarFinMano();
            case FIN_RONDA -> manejarFinRonda();
            case JUEGO_TERMINADO -> manejarJuegoTerminado();
            default -> {}
        }
    }
}
