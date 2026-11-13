package com.example.yugiohexplorer.modelo;

/**
 * Lista doblemente enlazada circular.
 * Permite moverse hacia adelante y hacia atrás.
 */
public class ListaCircularDoble<T>{

    private NodoCircular<T> inicio;
    private NodoCircular<T> fin;
    private int tamano;

    public ListaCircularDoble(){
        inicio=null;
        fin=null;
        tamano=0;
    }

    //Inserta al final
    public void insertarFinal(T dato){
        NodoCircular<T> nuevo=new NodoCircular<>(dato);
        if(inicio==null){
            inicio=nuevo;
            fin=nuevo;
            nuevo.setSiguiente(inicio);
            nuevo.setAnterior(inicio);
        }else{
            nuevo.setAnterior(fin);
            nuevo.setSiguiente(inicio);
            fin.setSiguiente(nuevo);
            inicio.setAnterior(nuevo);
            fin=nuevo;
        }
        tamano++;
    }

    //Obtiene el elemento en una posición
    public T obtener(int posicion){
        if(posicion<0||posicion>=tamano) return null;
        NodoCircular<T> actual=inicio;
        for(int i=0;i<posicion;i++){
            actual=actual.getSiguiente();
        }
        return actual.getDato();
    }

    public int getTamano(){
        return tamano;
    }

    public boolean estaVacia(){
        return tamano==0;
    }
}