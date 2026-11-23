package ar.unlu.poo.Modelo;

public class Jugada {
    private Carta carta;
    private Jugador jugador;

    public Jugada(Jugador jugador, Carta carta){
        this.carta = carta;
        this.jugador = jugador;
    }

    public Jugador getJugador(){
        return this.jugador;
    }

    public Carta getCarta(){
        return this.carta;
    }


}
