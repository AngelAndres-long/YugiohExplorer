package com.example.yugiohexplorer.controlador;

import com.example.yugiohexplorer.modelo.Carta;
import com.example.yugiohexplorer.modelo.ListaCircularDoble;
import com.example.yugiohexplorer.modelo.TipoAtributo;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    private ListaCircularDoble<Carta> listaBase;
    private int indiceActual=0;
    private MediaPlayer reproductor;

    //Filtros activos
    private Integer filtroNivel=null;
    private String filtroAtributo=null;

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

        cargarCSV("src/main/java/com/example/yugiohexplorer/modelo/yugioh.csv");
        ordenarListas();
        listaActual=listaTodas;
        listaBase=listaTodas;
        iniciarMusica();
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

    //Ordena las listas de nivel, ataque y defensa
    private void ordenarListas(){
        ordenarLista(listaNivelMenorMayor,Carta.POR_NIVEL);
        ordenarLista(listaNivelMayorMenor,Carta.POR_NIVEL.reversed());
        ordenarLista(listaAtaqueMenorMayor,Carta.POR_ATAQUE);
        ordenarLista(listaAtaqueMayorMenor,Carta.POR_ATAQUE.reversed());
        ordenarLista(listaDefensaMenorMayor,Carta.POR_DEFENSA);
        ordenarLista(listaDefensaMayorMenor,Carta.POR_DEFENSA.reversed());
    }

    //Ordena una lista circular usando un ArrayList temporal
    private void ordenarLista(ListaCircularDoble<Carta> lista,Comparator<Carta> comparador){
        if(lista.estaVacia()) return;

        ArrayList<Carta> temp=new ArrayList<>();
        int n=lista.contar();
        for(int i=0;i<n;i++){
            temp.add(lista.obtener(i));
        }

        Collections.sort(temp,comparador);

        while(!lista.estaVacia()){
            lista.eliminaInicio();
        }

        for(Carta c:temp){
            lista.insertaFinal(c);
        }
    }

    //Inicia la música en loop
    private void iniciarMusica(){
        try{
            Media media=new Media(getClass().getResource("/audio/passionate_duelist_hd.mp3").toExternalForm());
            reproductor=new MediaPlayer(media);
            reproductor.setCycleCount(MediaPlayer.INDEFINITE);
            reproductor.play();
        }catch(Exception e){
            System.err.println("No se pudo cargar la música");
        }
    }

    public void silenciar(){
        if(reproductor!=null){
            reproductor.pause();
        }
    }

    public void activarSonido(){
        if(reproductor!=null){
            reproductor.play();
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
        this.listaBase=lista;
        this.indiceActual=0;
        this.filtroNivel=null;
        this.filtroAtributo=null;
    }

    public void siguiente(){
        int total=listaActual.contar();
        if(total>0){
            indiceActual=(indiceActual+1)%total;
        }
    }

    public void anterior(){
        int total=listaActual.contar();
        if(total>0){
            indiceActual=(indiceActual-1+total)%total;
        }
    }

    //Aplica los filtros actuales de nivel y atributo
    private void aplicarFiltros(){
        ListaCircularDoble<Carta> nueva=new ListaCircularDoble<>();
        int total=listaBase.contar();

        for(int i=0;i<total;i++){
            Carta c=listaBase.obtener(i);

            //Si hay filtro de nivel, excluir nivel 0 y las que no coincidan
            if(filtroNivel!=null){
                if(c.getNivel()==0 || c.getNivel()!=filtroNivel){
                    continue;
                }
            }

            //Filtro de atributo
            if(filtroAtributo!=null && !c.getAtributo().name().equals(filtroAtributo)){
                continue;
            }

            nueva.insertaFinal(c);
        }

        listaActual=nueva;
        indiceActual=0;
    }

    //Filtra por atributo
    public void filtrarPorAtributo(String atributo){
        this.filtroAtributo=atributo;
        aplicarFiltros();
    }

    //Filtra por nivel específico
    public void filtrarPorNivel(int nivel){
        ListaCircularDoble<Carta> nueva=new ListaCircularDoble<>();
        int total=listaBase.contar();

        for(int i=0;i<total;i++){
            Carta c=listaBase.obtener(i);

            //Solo agregar si es el nivel correcto Y no es Spell ni Trap
            if(c.getNivel()==nivel &&
                    c.getAtributo()!=TipoAtributo.SPELL &&
                    c.getAtributo()!=TipoAtributo.TRAP){
                nueva.insertaFinal(c);
            }
        }

        listaActual=nueva;
        indiceActual=0;
        this.filtroNivel=nivel;
    }

    //Ordena todas las cartas del atributo actual por nivel (quita el filtro de nivel)
    public void ordenarPorNivelSinFiltroNivel(boolean menorAMayor){
        this.filtroNivel=null;
        aplicarFiltros();
        ordenarListaActual(menorAMayor?Carta.POR_NIVEL:Carta.POR_NIVEL.reversed());
    }

    //Reinicia todos los filtros
    public void reiniciarFiltros(){
        this.filtroNivel=null;
        this.filtroAtributo=null;
        listaActual=listaBase;
        indiceActual=0;
    }

    public boolean hayResultados(){
        return !listaActual.estaVacia();
    }

    //Ordena la lista actual por nivel
    public void ordenarListaActualPorNivel(boolean menorAMayor){
        ordenarListaActual(menorAMayor?Carta.POR_NIVEL:Carta.POR_NIVEL.reversed());
    }

    //Ordena la lista actual por ataque
    public void ordenarListaActualPorAtaque(boolean menorAMayor){
        ordenarListaActual(menorAMayor?Carta.POR_ATAQUE:Carta.POR_ATAQUE.reversed());
    }

    //Ordena la lista actual por defensa
    public void ordenarListaActualPorDefensa(boolean menorAMayor){
        ordenarListaActual(menorAMayor?Carta.POR_DEFENSA:Carta.POR_DEFENSA.reversed());
    }

    //Ordena la lista actual usando un comparador
    private void ordenarListaActual(Comparator<Carta> comparador){
        if(listaActual.estaVacia()) return;

        ArrayList<Carta> temp=new ArrayList<>();
        int n=listaActual.contar();
        for(int i=0;i<n;i++){
            temp.add(listaActual.obtener(i));
        }

        Collections.sort(temp,comparador);

        ListaCircularDoble<Carta> nueva=new ListaCircularDoble<>();
        for(Carta c:temp){
            nueva.insertaFinal(c);
        }
        listaActual=nueva;
        indiceActual=0;
    }

    public ListaCircularDoble<Carta> getListaTodas(){
        return listaTodas;
    }

    public ListaCircularDoble<Carta> getListaNivelMenorMayor(){
        return listaNivelMenorMayor;
    }

    public ListaCircularDoble<Carta> getListaNivelMayorMenor(){
        return listaNivelMayorMenor;
    }

    public ListaCircularDoble<Carta> getListaAtaqueMenorMayor(){
        return listaAtaqueMenorMayor;
    }

    public ListaCircularDoble<Carta> getListaAtaqueMayorMenor(){
        return listaAtaqueMayorMenor;
    }

    public ListaCircularDoble<Carta> getListaDefensaMenorMayor(){
        return listaDefensaMenorMayor;
    }

    public ListaCircularDoble<Carta> getListaDefensaMayorMenor(){
        return listaDefensaMayorMenor;
    }

    public ListaCircularDoble<Carta> getListaDark(){
        return listaDark;
    }

    public ListaCircularDoble<Carta> getListaLight(){
        return listaLight;
    }

    public ListaCircularDoble<Carta> getListaEarth(){
        return listaEarth;
    }

    public ListaCircularDoble<Carta> getListaWater(){
        return listaWater;
    }

    public ListaCircularDoble<Carta> getListaFire(){
        return listaFire;
    }

    public ListaCircularDoble<Carta> getListaWind(){
        return listaWind;
    }

    public ListaCircularDoble<Carta> getListaSpell(){
        return listaSpell;
    }

    public ListaCircularDoble<Carta> getListaTrap(){
        return listaTrap;
    }
}