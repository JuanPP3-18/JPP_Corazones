package ar.unlu.poo.Observer;


import ar.unlu.poo.Modelo.Evento;

public interface IObservable {
    void agregarObservador(IObservador iObservador);
    void quitarObservador(IObservador iObservador);
    void notificarObservadores(Evento evento);
}
