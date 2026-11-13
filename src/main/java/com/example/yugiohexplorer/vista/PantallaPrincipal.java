package com.example.yugiohexplorer.vista;

import com.example.yugiohexplorer.controlador.ControladorVista;
import com.example.yugiohexplorer.modelo.Carta;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Pantalla principal donde se muestran las cartas y los controles.
 */
public class PantallaPrincipal extends BorderPane{

    private ControladorVista controlador;
    private Label lblNombre;
    private Label lblInfo;
    private Label lblContador;
    private HBox zonaCartas;

    public PantallaPrincipal(ControladorVista controlador){
        this.controlador=controlador;
        this.setStyle("-fx-background-color:#0d3b2e;");

        //Botones de navegación
        Button btnAnterior=new Button("Anterior");
        Button btnSiguiente=new Button("Siguiente");

        btnAnterior.setOnAction(e->{
            controlador.anterior();
            actualizar();
        });

        btnSiguiente.setOnAction(e->{
            controlador.siguiente();
            actualizar();
        });

        //Etiquetas
        lblNombre=new Label();
        lblNombre.setStyle("-fx-text-fill:white; -fx-font-size:20px; -fx-font-weight:bold;");
        lblInfo=new Label();
        lblInfo.setStyle("-fx-text-fill:#a0d8ef; -fx-font-size:14px;");
        lblContador=new Label();
        lblContador.setStyle("-fx-text-fill:white; -fx-font-size:16px;");

        zonaCartas=new HBox(20);
        zonaCartas.setAlignment(Pos.CENTER);

        VBox centro=new VBox(15,zonaCartas,lblNombre,lblInfo,lblContador);
        centro.setAlignment(Pos.CENTER);

        HBox botones=new HBox(20,btnAnterior,btnSiguiente);
        botones.setAlignment(Pos.CENTER);

        this.setCenter(centro);
        this.setBottom(botones);

        actualizar();
    }

    //Actualiza la vista con la carta actual
    public void actualizar(){
        if(controlador.getListaActual().estaVacia()) return;

        Carta actual=controlador.getListaActual().obtener(controlador.getIndiceActual());
        if(actual==null) return;

        zonaCartas.getChildren().clear();

        //Carta anterior (más pequeña)
        if(controlador.getIndiceActual()>0){
            Carta ant=controlador.getListaActual().obtener(controlador.getIndiceActual()-1);
            ImagenCarta imgAnt=new ImagenCarta(ant,120);
            zonaCartas.getChildren().add(imgAnt.getImagen());
        }

        //Carta actual (más grande)
        ImagenCarta imgActual=new ImagenCarta(actual,220);
        zonaCartas.getChildren().add(imgActual.getImagen());

        //Carta siguiente (más pequeña)
        if(controlador.getIndiceActual()<controlador.getListaActual().contar()-1){
            Carta sig=controlador.getListaActual().obtener(controlador.getIndiceActual()+1);
            ImagenCarta imgSig=new ImagenCarta(sig,120);
            zonaCartas.getChildren().add(imgSig.getImagen());
        }

        lblNombre.setText(actual.getNombre());
        lblInfo.setText(actual.getAtributo()+"  |  Nivel: "+actual.getNivel()+"  |  ATK: "+actual.getAtaque()+"  |  DEF: "+actual.getDefensa());
        lblContador.setText((controlador.getIndiceActual()+1)+" / "+controlador.getListaActual().contar());
    }
}