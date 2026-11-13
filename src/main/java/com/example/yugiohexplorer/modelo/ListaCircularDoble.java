package com.example.yugiohexplorer.modelo;

/**
 * Lista doblemente enlazada circular.
 */
public class ListaCircularDoble<T>{

    private NodoDoble<T> inicio;
    private NodoDoble<T> fin;

    public ListaCircularDoble(){
        inicio=null;
        fin=null;
    }

    //Inserta al inicio
    public void insertaInicio(T dato){
        NodoDoble<T> n=new NodoDoble<>(dato);
        if(inicio==null){
            inicio=fin=n;
            n.setSiguiente(inicio);
            n.setAnterior(inicio);
        }else{
            n.setSiguiente(inicio);
            inicio.setAnterior(n);
            inicio=n;
            fin.setSiguiente(inicio);
            n.setAnterior(fin);
        }
    }

    //Inserta al final
    public void insertaFinal(T dato){
        NodoDoble<T> n=new NodoDoble<>(dato);
        if(inicio==null){
            inicio=fin=n;
            n.setSiguiente(inicio);
            n.setAnterior(inicio);
        }else{
            n.setSiguiente(inicio);
            inicio.setAnterior(n);
            fin.setSiguiente(n);
            n.setAnterior(fin);
            fin=n;
        }
    }

    //Elimina al inicio
    public T eliminaInicio(){
        if(inicio==null){
            System.out.println("Lista vacía");
            return null;
        }
        T dato=inicio.getDato();
        if(inicio==fin){
            inicio=fin=null;
        }else{
            fin.setSiguiente(inicio.getSiguiente());
            inicio=inicio.getSiguiente();
            inicio.setAnterior(fin);
        }
        return dato;
    }

    //Elimina al final
    public T eliminaFinal(){
        if(inicio==null){
            System.out.println("Lista vacía");
            return null;
        }
        T dato=fin.getDato();
        if(inicio==fin){
            inicio=fin=null;
        }else{
            NodoDoble<T> r=fin.getAnterior();
            r.setSiguiente(inicio);
            inicio.setAnterior(r);
            fin=r;
        }
        return dato;
    }

    //Mostrar
    public String mostrar(){
        if(inicio==null){
            return "Lista vacía";
        }
        StringBuilder sb=new StringBuilder();
        NodoDoble<T> r=inicio;
        do{
            sb.append(r.getDato()).append(" ");
            r=r.getSiguiente();
        }while(r!=inicio);
        return sb.toString().trim();
    }

    //Buscar
    public int buscar(T x){
        if(inicio==null) return -1;
        int pos=0;
        NodoDoble<T> r=inicio;
        do{
            if(r.getDato().equals(x)){
                return pos;
            }
            r=r.getSiguiente();
            pos++;
        }while(r!=inicio);
        return -1;
    }

    //Está vacía
    public boolean estaVacia(){
        return inicio==null;
    }

    //Obtener por posición
    public T obtener(int posicion){
        if(inicio==null||posicion<0) return null;
        NodoDoble<T> actual=inicio;
        int i=0;
        do{
            if(i==posicion) return actual.getDato();
            actual=actual.getSiguiente();
            i++;
        }while(actual!=inicio);
        return null;
    }

    //Contar elementos
    public int contar(){
        if(inicio==null) return 0;
        int cont=0;
        NodoDoble<T> r=inicio;
        do{
            cont++;
            r=r.getSiguiente();
        }while(r!=inicio);
        return cont;
    }
}