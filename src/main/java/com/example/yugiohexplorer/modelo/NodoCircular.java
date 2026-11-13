package com.example.yugiohexplorer.modelo;

/**
 * Nodo para la lista circular doble.
 * Tiene referencia al anterior y al siguiente.
 */
public class NodoCircular<T>{

    private T dato;
    private NodoCircular<T> anterior;
    private NodoCircular<T> siguiente;

    public NodoCircular(T dato){
        this.dato=dato;
    }

    public T getDato(){
        return dato;
    }

    public void setDato(T dato){
        this.dato=dato;
    }

    public NodoCircular<T> getAnterior(){
        return anterior;
    }

    public void setAnterior(NodoCircular<T> anterior){
        this.anterior=anterior;
    }

    public NodoCircular<T> getSiguiente(){
        return siguiente;
    }

    public void setSiguiente(NodoCircular<T> siguiente){
        this.siguiente=siguiente;
    }
}