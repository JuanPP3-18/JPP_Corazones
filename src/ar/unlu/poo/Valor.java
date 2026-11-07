package ar.unlu.poo;
//-------------------ANOTACIONES-----------------
// La idea es que una carta no cambie de valor, ni siquiera mediante getters, entonces se define una variable
// de tipo private final, para que nadie pueda modificarla una vez creada
// Por lo general las variables de un ENUM se escriben en mayusculas, y es obligatorio por cuestiones de sintaxis
// que arranquen con letras, por numeros no pueden arrancar


public enum Valores{
    DOS(2),
    TRES(3),
    CUATRO(4),
    CINCO(5),
    SEIS(6),
    SIETE(7),
    OCHO(8),
    NUEVE(9),
    DIEZ(10),
    JOTA(11),
    REINA(12),
    REY(13),
    AS(14);

    private final int valorNumerico;

    // Constructor del enum
    Valores(int valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    public int getValorNumerico() {
        return this.valorNumerico;
    }
}

