package com.example.yugiohexplorer.vista;

import com.example.yugiohexplorer.controlador.ControladorVista;
import com.example.yugiohexplorer.modelo.Carta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
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

        VBox panelFiltros=crearPanelFiltros();

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

        lblNombre=new Label();
        lblNombre.setStyle("-fx-text-fill:white; -fx-font-size:22px; -fx-font-weight:bold;");
        lblInfo=new Label();
        lblInfo.setStyle("-fx-text-fill:#a0d8ef; -fx-font-size:15px;");
        lblContador=new Label();
        lblContador.setStyle("-fx-text-fill:white; -fx-font-size:16px;");

        zonaCartas=new HBox(25);
        zonaCartas.setAlignment(Pos.CENTER);

        VBox centro=new VBox(20,zonaCartas,lblNombre,lblInfo,lblContador);
        centro.setAlignment(Pos.CENTER);

        HBox botones=new HBox(20,btnAnterior,btnSiguiente);
        botones.setAlignment(Pos.CENTER);
        botones.setPadding(new Insets(0,0,20,0));

        this.setLeft(panelFiltros);
        this.setCenter(centro);
        this.setBottom(botones);

        actualizar();
    }

    private VBox crearPanelFiltros(){
        VBox panel=new VBox(15);
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color:#0a2f26;");
        panel.setAlignment(Pos.TOP_CENTER);

        Button btnTodas=new Button("Todas las cartas");
        btnTodas.setOnAction(e->{
            controlador.setListaActual(controlador.getListaTodas());
            actualizar();
        });

        ComboBox<String> comboNivel=new ComboBox<>();
        comboNivel.getItems().addAll("Menor a mayor","Mayor a menor");
        Button btnNivel=new Button("Por nivel");
        btnNivel.setOnAction(e->{
            String sel=comboNivel.getValue();
            if("Menor a mayor".equals(sel)){
                controlador.setListaActual(controlador.getListaNivelMenorMayor());
            }else{
                controlador.setListaActual(controlador.getListaNivelMayorMenor());
            }
            actualizar();
        });

        ComboBox<String> comboAtributo=new ComboBox<>();
        comboAtributo.getItems().addAll("DARK","LIGHT","EARTH","WATER","FIRE","WIND","SPELL","TRAP");
        Button btnAtributo=new Button("Por atributo");
        btnAtributo.setOnAction(e->{
            String sel=comboAtributo.getValue();
            if(sel!=null){
                switch(sel){
                    case "DARK": controlador.setListaActual(controlador.getListaDark()); break;
                    case "LIGHT": controlador.setListaActual(controlador.getListaLight()); break;
                    case "EARTH": controlador.setListaActual(controlador.getListaEarth()); break;
                    case "WATER": controlador.setListaActual(controlador.getListaWater()); break;
                    case "FIRE": controlador.setListaActual(controlador.getListaFire()); break;
                    case "WIND": controlador.setListaActual(controlador.getListaWind()); break;
                    case "SPELL": controlador.setListaActual(controlador.getListaSpell()); break;
                    case "TRAP": controlador.setListaActual(controlador.getListaTrap()); break;
                }
                actualizar();
            }
        });

        CheckBox chkAtaque=new CheckBox("Menor a mayor");
        Button btnAtaque=new Button("Por ataque");
        btnAtaque.setOnAction(e->{
            if(chkAtaque.isSelected()){
                controlador.setListaActual(controlador.getListaAtaqueMenorMayor());
            }else{
                controlador.setListaActual(controlador.getListaAtaqueMayorMenor());
            }
            actualizar();
        });

        CheckBox chkDefensa=new CheckBox("Menor a mayor");
        Button btnDefensa=new Button("Por defensa");
        btnDefensa.setOnAction(e->{
            if(chkDefensa.isSelected()){
                controlador.setListaActual(controlador.getListaDefensaMenorMayor());
            }else{
                controlador.setListaActual(controlador.getListaDefensaMayorMenor());
            }
            actualizar();
        });

        panel.getChildren().addAll(btnTodas,btnNivel,comboNivel,btnAtributo,comboAtributo,btnAtaque,chkAtaque,btnDefensa,chkDefensa);
        return panel;
    }

    public void actualizar(){
        if(controlador.getListaActual().estaVacia()) return;

        Carta actual=controlador.getListaActual().obtener(controlador.getIndiceActual());
        if(actual==null) return;

        zonaCartas.getChildren().clear();

        if(controlador.getIndiceActual()>0){
            Carta ant=controlador.getListaActual().obtener(controlador.getIndiceActual()-1);
            ImagenCarta imgAnt=new ImagenCarta(ant,130);
            zonaCartas.getChildren().add(imgAnt.getImagen());
        }

        ImagenCarta imgActual=new ImagenCarta(actual,240);
        zonaCartas.getChildren().add(imgActual.getImagen());

        if(controlador.getIndiceActual()<controlador.getListaActual().contar()-1){
            Carta sig=controlador.getListaActual().obtener(controlador.getIndiceActual()+1);
            ImagenCarta imgSig=new ImagenCarta(sig,130);
            zonaCartas.getChildren().add(imgSig.getImagen());
        }

        lblNombre.setText(actual.getNombre());
        String info=actual.getAtributo().name();
        if(actual.getAtributo()!=com.example.yugiohexplorer.modelo.TipoAtributo.SPELL &&
                actual.getAtributo()!=com.example.yugiohexplorer.modelo.TipoAtributo.TRAP){
            info+="  |  Nivel: "+actual.getNivel()+"  |  ATK: "+actual.getAtaque()+"  |  DEF: "+actual.getDefensa();
        }
        lblInfo.setText(info);
        lblContador.setText((controlador.getIndiceActual()+1)+" / "+controlador.getListaActual().contar());
    }
}