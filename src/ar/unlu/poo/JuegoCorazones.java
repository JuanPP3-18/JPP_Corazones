package ar.unlu.poo;

import java.util.ArrayList;
import java.util.List;

public class JuegoCorazones {
    private List<Jugador> jugadores;
    private Mazo mazo;
    private Mesa mesa;
    private int indiceJugadorActual;
    private boolean cartaCorazonesJugada;

    public JuegoCorazones(List<String> nombres){
        this.jugadores = new ArrayList<>();
        for (String s : nombres){
            this.jugadores.add(new Jugador(s));
        }
        this.mazo = new Mazo();
        this.mesa = new Mesa();
        this.indiceJugadorActual = 0;
        this.cartaCorazonesJugada = false;
    }

    public void iniciarNuevaRonda(){        //para repartir
        for (Jugador j : jugadores){
            j.prepararRondaNueva();
        }
        this.mesa = new Mesa();
        this.cartaCorazonesJugada = false;
        this.mazo.inicializarMazo();
        this.mazo.mezclar();

        for (Jugador j : jugadores){
            int i;
            for (i = 0; i < 13; i++){
                Carta carta = mazo.extraerCartas();
                j.recibirCarta(carta);
            }
        }
    }

    public void asignarIndicesJugadores() {
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador j = jugadores.get(i);
            j.setIndiceJugador(i + 1);
        }
    }

    public int determinarIndiceJugadorInicialRonda() {
        for (int i = 0; i < jugadores.size(); i++) {
            Jugador j = jugadores.get(i);

            for (Carta c : j.getMano()) {
                if (c.es2DePicas()) {
                    return i;
                }
            }
        }
        return 0;
    }

    public boolean esJugadaValida(Jugador jugador, Carta carta){
        List<Carta> cartas =  jugador.getMano();

        if (mesa.esPrimeraMano()){
            if (jugador.getMano().size() == 13) {
                return (carta.getPalo() == Palo.TREBOLES && carta.getValoresNumericos() == 2);
            }
            else {
                if (carta.getPalo() == Palo.CORAZONES) {
                    if (this.cartaCorazonesJugada == true || jugador.soloTieneCorazones()) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }
        else {
            if (jugador.tienePalo(mesa.getPaloDeSalida())) {        //si el jugador tiene palo del mismo de salida
                if (carta.getPalo() != mesa.getPaloDeSalida()){     //pero tira una distinta = falso
                    return false;
                }
                else {          //si tira una igual, esta bien.
                    return true;

                }
            }
            else {
                return true;
            }
        }

    }

    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }


}

