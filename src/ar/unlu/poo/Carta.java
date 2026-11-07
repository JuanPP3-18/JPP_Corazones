package ar.unlu.poo;

//-------------------ANOTACIONES-----------------
//la idea de asignar los atributos privados es para que no se puedan modificar de forma externa los distintos
//valores de las cartas, obligatoriamente se debe usar los getters o setters si se quiere ver los estados de los atributos
//


public class Carta {
    private Palo palo;
    private Valor valor;

    public Carta(Palo palo, Valor valor){
        this.palo = palo;
        this.valor = valor;
    }

    public Palo getPalo() {
        return this.palo;
    }

    public Valor getValor(){
        return this.valor;      //esta solo devuelve el valor (ya sea DOS,REINA,ETC)
    }

    public int getValoresNumericos(){
        return this.valor.getValorNumerico();       //Este devuelve el valor numerico 2, 12, etc.
    }

    public boolean esCorazones(){
        return this.palo == Palo.CORAZONES;
    }

    public boolean esReinaDePicas(){
        return (this.palo == Palo.PICAS && this.valor == Valor.REINA);
    }

    public boolean es2DePicas(){
        return (this.palo == Palo.PICAS && this.valor == Valor.DOS);
    }

    @Override
    public String toString() {
        return this.valor + " de " + this.palo;     //para saber la carta que es, con numero y palo
    }

}
