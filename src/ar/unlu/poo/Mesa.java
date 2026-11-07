package ar.unlu.poo;

import java.util.ArrayList;
import java.util.List;

public class Mesa {
    private List<Jugada> jugada;
    public Mesa(){
        this.jugada = new ArrayList<>();
    }

    public Palo getPaloDeSalida(){
        if (this.jugada.isEmpty()){
            return null;        //no hay palo de salida por el momento
        }
        return jugada.get(0).getCarta().getPalo();
    }

    public void agregarCartaJugada(Jugador jugador, Carta carta){
        this.jugada.add(new Jugada(jugador,carta));
    }

    public Jugador determinarGanadorDeLaMesa(){
        Jugador jugadorGanadorMesa = null;
        int mayorCartaRonda = 0;
        Palo paloDeSalidaMesa = getPaloDeSalida();
        for (Jugada j : jugada){
            Carta c = j.getCarta();
            if ((c.getPalo().equals(paloDeSalidaMesa)) && (c.getValoresNumericos() > mayorCartaRonda)){
                mayorCartaRonda = c.getValoresNumericos();
                jugadorGanadorMesa = j.getJugador();
            }
        }
        return jugadorGanadorMesa;
    }

    public int obtenerPuntosMesa(){
        int sumaTotal = 0;
        for (Jugada j : jugada){
            if (j.getCarta().esReinaDePicas()){
                sumaTotal += 13;
            }
            else if (j.getCarta().esCorazones()){
                sumaTotal += 1;
            }
        }
        return sumaTotal;
    }

    public void limpiarMesa(){
        this.jugada.clear();
    }

    public List<Carta> getCartasJugadasDeLaMesa(){
        List<Carta> cartasJugadas = new ArrayList<>();
        for (Jugada j : jugada){
            cartasJugadas.add(j.getCarta());
        }
        return cartasJugadas;
    }

    public boolean esPrimeraMano(){
        if (jugada.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

}
