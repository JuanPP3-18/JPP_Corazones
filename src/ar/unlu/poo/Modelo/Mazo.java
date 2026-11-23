package ar.unlu.poo.Modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazo {
    private List<Carta> cartas;

    public Mazo(){
        this.cartas = new ArrayList<>();
        inicializarMazo();
        mezclar();
    }

    public void inicializarMazo(){
        this.cartas.clear();        //limpio el mazo anterior
        for (Palo p : Palo.values()){         //el .values lo que hace es generar de forma automatica un array de los enum, el cual sirve para recorrer todo lo que esta declarado dentro.
            for (Valor v : Valor.values()){
                this.cartas.add(new Carta(p,v));
            }
        }
    }

    public void mezclar(){
        if (this.cartas.isEmpty()){
            throw new IllegalArgumentException("No podes mezclar el mazo porque esta vacio...");
        }
        Collections.shuffle(this.cartas);       //es un metodo propio de java que permite mezclar una colecion de valores
    }

    public Carta extraerCartas(){
        if (this.cartas.isEmpty()){
            throw new IllegalArgumentException("No podes extraer ninguna carta porque el mazo esta vacio...");
        }
        return this.cartas.remove(0);      //saco la primer carta del mazo
    }

    public int cantidadCartas(){
        return this.cartas.size();
    }

}
