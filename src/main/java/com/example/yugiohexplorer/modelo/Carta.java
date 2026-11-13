package com.example.yugiohexplorer.modelo;

import java.util.Comparator;

/**
 * Representa una carta de Yu-Gi-Oh con sus datos principales.
 */
public class Carta{

    private String nombre;
    private TipoAtributo atributo;
    private int nivel;
    private int ataque;
    private int defensa;
    private String descripcion;
    private int numero;

    public Carta(String nombre,TipoAtributo atributo,int nivel,int ataque,int defensa,String descripcion,int numero){
        this.nombre=nombre;
        this.atributo=atributo;
        this.nivel=nivel;
        this.ataque=ataque;
        this.defensa=defensa;
        this.descripcion=descripcion;
        this.numero=numero;
    }

    public String getNombre(){
        return nombre;
    }

    public TipoAtributo getAtributo(){
        return atributo;
    }

    public int getNivel(){
        return nivel;
    }

    public int getAtaque(){
        return ataque;
    }

    public int getDefensa(){
        return defensa;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public int getNumero(){
        return numero;
    }

    //Comparadores para ordenar las listas
    public static final Comparator<Carta> POR_NIVEL=Comparator.comparingInt(Carta::getNivel);
    public static final Comparator<Carta> POR_ATAQUE=Comparator.comparingInt(Carta::getAtaque);
    public static final Comparator<Carta> POR_DEFENSA=Comparator.comparingInt(Carta::getDefensa);
}