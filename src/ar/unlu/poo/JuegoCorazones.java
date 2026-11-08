package ar.unlu.poo;

import java.util.ArrayList;
import java.util.Comparator;
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

        //si es la primer mano de la partida
        if (mesa.esPrimeraMano()){
            //el jugador obligatoriamente debe jugar el 2 de treboles
            if (jugador.getMano().size() == 13) {
                return (carta.getPalo() == Palo.TREBOLES && carta.getValoresNumericos() == 2);
            }
        }

        //en caso de que no sea la primera mano de la ronda
        if(mesa.esPrimeraMano() && jugador.getMano().size() < 13){
            //si la carta es Corazones
            if (carta.getPalo() == Palo.CORAZONES) {
                //pero nunca se habia ganado un corazon y el jugador tenia otro palo para jugar, entonces no puede
                if (!this.cartaCorazonesJugada && !jugador.soloTieneCorazones()) {
                    return false;
                }
            }
        }

        //ya hay cartas en la mesa
        if (!mesa.esPrimeraMano()){
            //si el jugador tiene cartas con ese palo y jugo otra que no era
            if (jugador.tienePalo(mesa.getPaloDeSalida()) && carta.getPalo() != mesa.getPaloDeSalida()) {        //si el jugador tiene palo del mismo de salida
                return false;
            }
            //es la primer mano de la mesa y no tiene el palo de salida
            if (!jugador.tienePalo(mesa.getPaloDeSalida()) && jugador.getMano().size() == 13){
                //si el jugador tira una carta de puntos, retorna falso
                if (carta.getPalo() == Palo.CORAZONES || (carta.getPalo() == Palo.PICAS && carta.getValoresNumericos() == 12)){
                    return false;
                }
            }
        }
        return true;
    }

    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }

    public void jugarTurno(Jugador jugador, Carta carta){
        jugador.jugarCarta(carta);
        mesa.agregarCartaJugada(jugador,carta);
        //veo si ya se jugo alguna carta de corazones
        if (carta.getPalo() == Palo.CORAZONES && !cartaCorazonesJugada){
            cartaCorazonesJugada = true;
        }

        //actualizo el indice del siguiente jugador
        indiceJugadorActual = (indiceJugadorActual + 1) % 4;
    }

    public boolean esFinDeRonda(){
        return this.mesa.getCartasJugadasDeLaMesa().size() == 4;
    }

    public void procesarFinDeRonda(){
        Jugador jugadorGanador = this.mesa.determinarGanadorDeLaMesa();
        jugadorGanador.agregarCartasGanadas(mesa.getCartasJugadasDeLaMesa());
        this.mesa.limpiarMesa();
        this.indiceJugadorActual = jugadorGanador.getIndiceJugador() - 1;
    }

    public boolean esFinDePartida(){
        return this.jugadores.get(0).getMano().isEmpty();
    }

    public void finalizarRonda(){
        List<Carta> cartasGanadasRonda = null;
        for (Jugador j : jugadores){
            int contadorPuntosRonda = 0;
            cartasGanadasRonda = (j.getCartasGanadas());
            for (Carta c : cartasGanadasRonda){
                if (c.getPalo() == Palo.CORAZONES){
                    contadorPuntosRonda++;
                }
                if (c.getPalo() == Palo.PICAS && c.getValoresNumericos() == 12){
                    contadorPuntosRonda += 13;
                }
            }
            j.sumarPuntaje(contadorPuntosRonda);
        }
    }

    public boolean hayGanadorPartida(){
        for (Jugador j : jugadores){
            if (j.getPuntaje().getPuntajeTotal() >= 100){
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

    public List<Jugador> tablaDeJugadoresGanadores() {
        List<Jugador> jugadoresGanadores = new ArrayList<>(this.jugadores);
        //lo que hace esto es acomodar de menor a mayor la lista, mediante el Comparator (es una interfaz),
        // el comparingInt (metodo) compara especificamente solo valores numericos, con el j -> lo que hace es
        // ir de a dos, entonces va comparando y ordenando en base a eso.
        jugadoresGanadores.sort(Comparator.comparingInt(j -> j.getPuntaje().getPuntajeTotal()));
        return jugadoresGanadores;
    }
}
