package ar.unlu.poo;

public class Puntaje {
    private int puntajeRonda;
    private int puntajeTotal;

    public Puntaje(){
        this.puntajeRonda = 0;
        this.puntajeTotal = 0;
    }

    public int getPuntajeRonda(){
        return this.puntajeRonda;
    }

    public int getPuntajeTotal(){
        return this.puntajeTotal;
    }

    public void sumarPuntosRonda(int puntos){
        this.puntajeRonda += puntos;
    }

    public void terminarRonda(){
        this.puntajeTotal += this.puntajeRonda;
        this.puntajeRonda = 0;
    }

    public void reinciarPartida(){
        this.puntajeTotal = 0;
        this.puntajeRonda = 0;
    }
}

