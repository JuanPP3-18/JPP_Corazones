package ar.unlu.poo.Modelo;

import ar.unlu.poo.Observer.IObservable;
import ar.unlu.poo.Observer.IObservador;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class JuegoCorazones implements IObservable {
    private List<Jugador> jugadores;
    private List<IObservador> observadores;
    private Mazo mazo;
    private Mesa mesa;
    private int indiceJugadorActual;
    private boolean cartaCorazonesJugada;
    private Jugador ultimoJugadorQueJugo;
    private Carta ultimaCartaJugada;

    public JuegoCorazones(List<Jugador> jugadores){
        if(jugadores == null){
            throw new IllegalArgumentException("La lista de jugadores no puede estar vacia...");
        }
        this.jugadores = new ArrayList<>(jugadores);
        this.observadores = new ArrayList<>();
        this.mazo = new Mazo();
        this.mesa = new Mesa();
        this.indiceJugadorActual = 0;
        this.cartaCorazonesJugada = false;
        notificarObservadores(Evento.INICIO_PARTIDA);
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
        notificarObservadores(Evento.INICIO_RONDA);
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
                if (c.es2DeTreboles()) {
                    return i;
                }
            }
        }
        return 0;
    }

    public Jugador getUltimoJugadorQueJugo() {
        return this.ultimoJugadorQueJugo;
    }

    public Carta getUltimaCartaJugada() {
        return this.ultimaCartaJugada;
    }

    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }

    public void jugarTurno(Jugador jugador, Carta carta){
        jugador.jugarCarta(carta);
        mesa.agregarCartaJugada(jugador,carta);
        //veo si ya se jugo alguna carta de corazones
        if (carta.getPalo() == Palo.CORAZONES && !cartaCorazonesJugada){
            notificarObservadores(Evento.CORAZONES_JUGADOS);
            cartaCorazonesJugada = true;
        }

        //actualizo el indice del siguiente jugador
        indiceJugadorActual = (indiceJugadorActual + 1) % 4;
        ultimoJugadorQueJugo = jugador;
        ultimaCartaJugada = carta;
        notificarObservadores(Evento.CARTA_JUGADA);
        notificarObservadores(Evento.AVANCE_TURNO);
    }

    public boolean esFinDeMano(){
        return this.mesa.getCartasJugadasDeLaMesa().size() == 4;
    }

    public void procesarFinDeMano(){
        Jugador jugadorGanador = this.mesa.determinarGanadorDeLaMesa();
        int puntosMano = this.mesa.obtenerPuntosMesa();
        jugadorGanador.sumarPuntaje(puntosMano);
        jugadorGanador.agregarCartasGanadas(mesa.getCartasJugadasDeLaMesa());
        notificarObservadores(Evento.FIN_MANO);
        this.mesa.limpiarMesa();
        this.indiceJugadorActual = jugadorGanador.getIndiceJugador() - 1;
    }

    public boolean esFinDePartida(){
        return this.jugadores.get(0).getMano().isEmpty();
    }

    public void finalizarRonda(){
        List<Carta> cartasGanadasRonda = null;
        for (Jugador j : jugadores){
            j.getPuntaje().terminarRonda();

        }
        notificarObservadores(Evento.FIN_RONDA);
    }

    public boolean hayGanadorPartida(){
        for (Jugador j : jugadores){
            if (j.getPuntaje().getPuntajeTotal() >= 100){
                notificarObservadores(Evento.JUEGO_TERMINADO);
                return true;
            }
        }
        return false;
    }

    public Jugador getJugadorGanadorPartida(){
        Jugador jugadorConMenorPuntaje = jugadores.get(0);
        int puntajeJugadorConMenorPuntaje = jugadores.get(0).getPuntaje().getPuntajeTotal();
        for (Jugador j : jugadores){
            if (j.getPuntaje().getPuntajeTotal() < puntajeJugadorConMenorPuntaje){
                jugadorConMenorPuntaje = j;
                puntajeJugadorConMenorPuntaje = j.getPuntaje().getPuntajeTotal();
            }
        }
        return jugadorConMenorPuntaje;
    }

    public Jugador getJugadorPerdedorPartida(){
        Jugador jugadorConMenorPuntaje = jugadores.get(0);
        int puntajeJugadorConMenorPuntaje = jugadores.get(0).getPuntaje().getPuntajeTotal();
        for (Jugador j : jugadores){
            if (j.getPuntaje().getPuntajeTotal() > puntajeJugadorConMenorPuntaje){
                jugadorConMenorPuntaje = j;
                puntajeJugadorConMenorPuntaje = j.getPuntaje().getPuntajeTotal();
            }
        }
        return jugadorConMenorPuntaje;
    }

    public List<Jugador> tablaDeJugadoresGanadores() {
        //le paso la copia para evitar aliassing
        List<Jugador> jugadoresGanadores = new ArrayList<>(this.jugadores);
        //lo que hace esto es acomodar de menor a mayor la lista, mediante el Comparator (es una interfaz),
        // el comparingInt (metodo) compara especificamente solo valores numericos, con el j -> lo que hace es
        // ir de a dos, entonces va comparando y ordenando en base a eso.
        jugadoresGanadores.sort(Comparator.comparingInt(j -> j.getPuntaje().getPuntajeTotal()));
        return jugadoresGanadores;
    }

    public void setIndiceJugadorActual(int indice){
        if (indice < 0 || indice >= this.jugadores.size()) {
            throw new IllegalArgumentException("Indice de jugador fuera de rango: " + indice);
        }
        this.indiceJugadorActual = indice;
    }

    public Palo getPaloSalida(){
        return mesa.getPaloDeSalida();
    }

    public Mesa getMesa() {
        return this.mesa;
    }

    public List<Jugador> getJugadores(){
        return this.jugadores;
    }

    public String getMotivoJugadaInvalida(Jugador jugador, Carta carta) {
        // La mesa está vacía: el jugador abre la mano
        if (mesa.esPrimeraMano()) {
            if (jugador.getMano().size() == 13) {
                if (!carta.es2DeTreboles()) {
                    return "En la primera mano debés empezar con el 2 de Treboles.";
                }
                return null;
            }
            if (carta.getPalo() == Palo.CORAZONES && !cartaCorazonesJugada && !jugador.soloTieneCorazones()) {
                return "No podes abrir con Corazones hasta que se haya jugado alguno.";
            }
            return null;
        }

        Palo paloSalida = mesa.getPaloDeSalida();
        if (jugador.tienePalo(paloSalida)) {
            if (carta.getPalo() != paloSalida) {
                return "Tenes que tirar una carta de " + paloSalida + ".";
            }
            return null;
        }

        // No tiene el palo de salida: en la primera mano no puede tirar cartas con puntos
        if (jugador.getMano().size() == 13) {
            boolean esCartaDePuntos = carta.getPalo() == Palo.CORAZONES || carta.esReinaDePicas();
            if (esCartaDePuntos && !jugador.soloTieneCartasDePuntos()) {
                return "En la primera mano no se pueden tirar cartas con puntos (Corazones ni la Reina de Picas).";
            }
        }
        return null;
    }

    public boolean esJugadaValida(Jugador jugador, Carta carta) {
        return getMotivoJugadaInvalida(jugador, carta) == null;
    }

    @Override
    public void agregarObservador(IObservador observador) {
        observadores.add(observador);
    }

    @Override
    public void quitarObservador(IObservador observador) {
        observadores.remove(observador);
    }

    @Override
    public void notificarObservadores(Evento evento) {
        for (IObservador o : observadores){
            o.actualizar(evento);
        }
    }
}
