package ar.unlu.poo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    public String nombre;
    private List<Carta> mano;
    private List<Carta> cartasGanadas;
    private Puntaje puntaje;
    private  int indiceJugador;


    public Jugador(String nombre){
        this.nombre = nombre;
        this.mano = new ArrayList<>();
        this.cartasGanadas = new ArrayList<>();
        this.puntaje = new Puntaje();
    }

    public List<Carta> getMano(){
        return this.mano;
    }

    public List<Carta> getCartasGanadas(){
        return this.cartasGanadas;
    }

    public Puntaje getPuntaje(){
        return this.puntaje;
    }

    public void recibirCarta(Carta carta){
        this.mano.add(carta);
    }

    public void jugarCarta(Carta carta){
        this.mano.remove(carta);
    }

    public void agregarCartasGanadas(List<Carta> cartasGanadas){
        this.cartasGanadas.addAll(cartasGanadas);           //el addAll sirve para colecciones
    }

    public boolean tienePalo(Palo palo){
        for (Carta c : mano){
            if (c.getPalo() == palo){
                return true;
            }
        }
        return false;
    }

    public void prepararRondaNueva(){
        this.mano.clear();
        this.cartasGanadas.clear();         //limpiamos todo para arrancar de 0
    }

    public void setIndiceJugador(int indiceJugador){
        this.indiceJugador = indiceJugador;
    }

    public boolean unCorazonGanado(){
        for(Carta c : cartasGanadas){
            if (c.getPalo() == Palo.CORAZONES){
                return true;
            }
        }
        return false;
    }

    public boolean soloTieneCorazones() {
        for (Carta c : mano){
            if (c.getPalo() != Palo.CORAZONES){
                return false;
            }
        }
        return true;
    }
}
