package com.example.yugiohexplorer.controlador;

import com.example.yugiohexplorer.modelo.Carta;
import com.example.yugiohexplorer.modelo.ListaCircularDoble;
import com.example.yugiohexplorer.modelo.TipoAtributo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Controla la carga de las cartas y el manejo de las listas circulares.
 */
public class ControladorVista{

    private ListaCircularDoble<Carta> listaTodas;
    private ListaCircularDoble<Carta> listaNivelMenorMayor;
    private ListaCircularDoble<Carta> listaNivelMayorMenor;
    private ListaCircularDoble<Carta> listaAtaqueMenorMayor;
    private ListaCircularDoble<Carta> listaAtaqueMayorMenor;
    private ListaCircularDoble<Carta> listaDefensaMenorMayor;
    private ListaCircularDoble<Carta> listaDefensaMayorMenor;
    private ListaCircularDoble<Carta> listaDark;
    private ListaCircularDoble<Carta> listaLight;
    private ListaCircularDoble<Carta> listaEarth;
    private ListaCircularDoble<Carta> listaWater;
    private ListaCircularDoble<Carta> listaFire;
    private ListaCircularDoble<Carta> listaWind;
    private ListaCircularDoble<Carta> listaSpell;
    private ListaCircularDoble<Carta> listaTrap;

    private ListaCircularDoble<Carta> listaActual;
    private int indiceActual=0;

    public ControladorVista(){
        listaTodas=new ListaCircularDoble<>();
        listaNivelMenorMayor=new ListaCircularDoble<>();
        listaNivelMayorMenor=new ListaCircularDoble<>();
        listaAtaqueMenorMayor=new ListaCircularDoble<>();
        listaAtaqueMayorMenor=new ListaCircularDoble<>();
        listaDefensaMenorMayor=new ListaCircularDoble<>();
        listaDefensaMayorMenor=new ListaCircularDoble<>();
        listaDark=new ListaCircularDoble<>();
        listaLight=new ListaCircularDoble<>();
        listaEarth=new ListaCircularDoble<>();
        listaWater=new ListaCircularDoble<>();
        listaFire=new ListaCircularDoble<>();
        listaWind=new ListaCircularDoble<>();
        listaSpell=new ListaCircularDoble<>();
        listaTrap=new ListaCircularDoble<>();

        cargarCSV("src/main/resources/imagenes/yugioh_cards.csv");
        listaActual=listaTodas;
    }

    //Carga las cartas desde el CSV
    private void cargarCSV(String ruta){
        try(BufferedReader br=new BufferedReader(new FileReader(ruta))){
            String linea;
            br.readLine();
            int numero=1;
            while((linea=br.readLine())!=null){
                String[] columnas=linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if(columnas.length>=6){
                    try{
                        String nombre=columnas[0].trim();
                        TipoAtributo atributo=TipoAtributo.valueOf(columnas[1].trim().toUpperCase());
                        int nivel=columnas[2].trim().isEmpty()?0:(int)Double.parseDouble(columnas[2].trim());
                        int ataque=columnas[3].trim().isEmpty()?0:(int)Double.parseDouble(columnas[3].trim());
                        int defensa=columnas[4].trim().isEmpty()?0:(int)Double.parseDouble(columnas[4].trim());
                        String descripcion=columnas[5].trim();
                        if(descripcion.startsWith("\"")&&descripcion.endsWith("\"")){
                            descripcion=descripcion.substring(1,descripcion.length()-1);
                        }

                        Carta carta=new Carta(nombre,atributo,nivel,ataque,defensa,descripcion,numero);
                        listaTodas.insertaFinal(carta);
                        listaNivelMenorMayor.insertaFinal(carta);
                        listaNivelMayorMenor.insertaFinal(carta);
                        listaAtaqueMenorMayor.insertaFinal(carta);
                        listaAtaqueMayorMenor.insertaFinal(carta);
                        listaDefensaMenorMayor.insertaFinal(carta);
                        listaDefensaMayorMenor.insertaFinal(carta);

                        switch(atributo){
                            case DARK: listaDark.insertaFinal(carta); break;
                            case LIGHT: listaLight.insertaFinal(carta); break;
                            case EARTH: listaEarth.insertaFinal(carta); break;
                            case WATER: listaWater.insertaFinal(carta); break;
                            case FIRE: listaFire.insertaFinal(carta); break;
                            case WIND: listaWind.insertaFinal(carta); break;
                            case SPELL: listaSpell.insertaFinal(carta); break;
                            case TRAP: listaTrap.insertaFinal(carta); break;
                        }
                        numero++;
                    }catch(IllegalArgumentException e){
                        System.err.println("Fila omitida: "+columnas[0]);
                    }
                }
            }
        }catch(IOException e){
            System.err.println("Error al leer el CSV: "+e.getMessage());
        }
    }

    public ListaCircularDoble<Carta> getListaActual(){
        return listaActual;
    }

    public int getIndiceActual(){
        return indiceActual;
    }

    public void setListaActual(ListaCircularDoble<Carta> lista){
        this.listaActual=lista;
        this.indiceActual=0;
    }

    public void siguiente(){
        if(indiceActual<listaActual.contar()-1){
            indiceActual++;
        }
    }

    public void anterior(){
        if(indiceActual>0){
            indiceActual--;
        }
    }
}