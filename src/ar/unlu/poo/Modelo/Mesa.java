package ar.unlu.poo.Modelo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Mesa {
    private List<Jugada> jugadas;
    private final Map<Jugador, Carta> cartasEnMesa = new LinkedHashMap<>();

    public Mesa(){
        this.jugadas = new ArrayList<>();
    }

    public Palo getPaloDeSalida(){
        if (this.jugadas.isEmpty()){
            return null;        //no hay palo de salida por el momento
        }
        return jugadas.get(0).getCarta().getPalo();
    }

    public void agregarCartaJugada(Jugador jugador, Carta carta){
        this.jugadas.add(new Jugada(jugador,carta));
        this.cartasEnMesa.put(jugador,carta);
    }

    public Map<Jugador, Carta> getCartasEnMesa() {
        return new LinkedHashMap<>(cartasEnMesa);
    }

    public void limpiarMesa() {
        cartasEnMesa.clear();
        this.jugadas.clear();
    }

    public Jugador determinarGanadorDeLaMesa(){
        Jugador jugadorGanadorMesa = null;
        int mayorCartaRonda = 0;
        Palo paloDeSalidaMesa = getPaloDeSalida();
        for (Jugada j : jugadas){
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
        for (Jugada j : jugadas){
            if (j.getCarta().esReinaDePicas()){
                sumaTotal += 13;
            }
            else if (j.getCarta().esCorazones()){
                sumaTotal += 1;
            }
        }
        return sumaTotal;
    }

    public List<Carta> getCartasJugadasDeLaMesa(){
        List<Carta> cartasJugadas = new ArrayList<>();
        for (Jugada j : jugadas){
            cartasJugadas.add(j.getCarta());
        }
        return cartasJugadas;
    }

    public boolean esPrimeraMano(){
        if (jugadas.isEmpty()){
            return true;
        }
        else {
            return false;
        }
    }

}
