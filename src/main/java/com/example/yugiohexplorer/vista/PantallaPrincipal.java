package com.example.yugiohexplorer.vista;

import com.example.yugiohexplorer.controlador.ControladorVista;
import com.example.yugiohexplorer.modelo.Carta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

/**
 * Pantalla principal donde se muestran las cartas y los controles.
 */
public class PantallaPrincipal extends BorderPane{

    private ControladorVista controlador;
    private Label lblNombre;
    private Label lblInfo;
    private Label lblContador;
    private Label lblGuia;
    private HBox zonaCartas;
    private boolean silenciado=false;

    public PantallaPrincipal(ControladorVista controlador){
        this.controlador=controlador;

        //Fondo
        try{
            Image fondo=new Image(getClass().getResource("/background/yugioh.jpg").toExternalForm());
            BackgroundImage bg=new BackgroundImage(fondo,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,new BackgroundSize(BackgroundSize.AUTO,BackgroundSize.AUTO,false,false,true,true));
            this.setBackground(new Background(bg));
        }catch(Exception e){
            this.setStyle("-fx-background-color:#1a1a1a;");
        }

        VBox panelFiltros=crearPanelFiltros();

        //Contenedor con margen para que el panel no toque los bordes
        StackPane contenedorPanel=new StackPane(panelFiltros);
        contenedorPanel.setPadding(new Insets(25,0,25,25));
        contenedorPanel.setAlignment(Pos.TOP_LEFT);

        //Botón de sonido
        Button btnSonido=new Button("🔊");
        btnSonido.setStyle("-fx-background-color:#111111; -fx-text-fill:#f5e6c8; -fx-font-size:18px; -fx-background-radius:25; -fx-padding:10 14; -fx-border-color:#c9a227; -fx-border-width:2;");
        btnSonido.setOnAction(e->{
            silenciado=!silenciado;
            if(silenciado){
                controlador.silenciar();
                btnSonido.setText("🔇");
            }else{
                controlador.activarSonido();
                btnSonido.setText("🔊");
            }
        });

        lblGuia=new Label("Dale clic a las cartas laterales para avanzar o retroceder");
        lblGuia.setStyle("-fx-text-fill:#f5e6c8; -fx-font-size:16px; -fx-font-weight:bold; -fx-effect:dropshadow(gaussian,black,3,0,0,1);");

        lblNombre=new Label();
        lblNombre.setStyle("-fx-text-fill:#f5e6c8; -fx-font-size:28px; -fx-font-weight:bold; -fx-effect:dropshadow(gaussian,black,6,0,0,3);");
        lblInfo=new Label();
        lblInfo.setStyle("-fx-text-fill:#ffd700; -fx-font-size:18px; -fx-font-weight:bold;");
        lblContador=new Label();
        lblContador.setStyle("-fx-text-fill:#f5e6c8; -fx-font-size:20px;");

        zonaCartas=new HBox(40);
        zonaCartas.setAlignment(Pos.CENTER);

        VBox centro=new VBox(20,lblGuia,zonaCartas,lblNombre,lblInfo,lblContador);
        centro.setAlignment(Pos.CENTER);

        HBox top=new HBox(btnSonido);
        top.setAlignment(Pos.TOP_RIGHT);
        top.setPadding(new Insets(20,25,0,0));

        this.setLeft(contenedorPanel);
        this.setCenter(centro);
        this.setTop(top);

        actualizar();
    }

    private void estiloBoton(Button btn){
        btn.setStyle("-fx-background-color:#111111; -fx-text-fill:#f5e6c8; -fx-font-size:15px; -fx-font-weight:bold; -fx-padding:10 22; -fx-background-radius:8; -fx-border-color:#c9a227; -fx-border-width:2;");
    }

    private void estiloCombo(ComboBox<String> combo){
        combo.setStyle("-fx-background-color:#1a1a1a; -fx-border-color:#c9a227; -fx-border-radius:6; -fx-background-radius:6;");
        combo.setButtonCell(new javafx.scene.control.ListCell<String>(){
            @Override
            protected void updateItem(String item,boolean empty){
                super.updateItem(item,empty);
                if(empty||item==null){
                    setText(combo.getPromptText());
                    setTextFill(javafx.scene.paint.Color.web("#c9a227"));
                }else{
                    setText(item);
                    setTextFill(javafx.scene.paint.Color.web("#c9a227"));
                }
                setAlignment(Pos.CENTER);
                setStyle("-fx-background-color:transparent; -fx-font-size:14px;");
            }
        });
    }

    private VBox crearPanelFiltros(){
        VBox panel=new VBox(18);
        panel.setPadding(new Insets(25));
        panel.setStyle("-fx-background-color:#111111; -fx-background-radius:15; -fx-border-color:#c9a227; -fx-border-width:3; -fx-border-radius:15;");
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setPrefWidth(320);
        panel.setMaxWidth(320);

        Button btnTodas=new Button("Todas las cartas");
        estiloBoton(btnTodas);
        btnTodas.setPrefWidth(250);
        btnTodas.setOnAction(e->{
            controlador.setListaActual(controlador.getListaTodas());
            actualizar();
        });

        ComboBox<String> comboNivel=new ComboBox<>();
        comboNivel.getItems().addAll("Nivel 1","Nivel 2","Nivel 3","Nivel 4","Nivel 5","Nivel 6","Nivel 7","Nivel 8","Nivel 9","Nivel 10","Nivel 11","Nivel 12","Menor a mayor","Mayor a menor");
        comboNivel.setPromptText("Nivel o orden");
        comboNivel.setPrefWidth(250);
        estiloCombo(comboNivel);
        Button btnNivel=new Button("Filtrar / Ordenar nivel");
        estiloBoton(btnNivel);
        btnNivel.setPrefWidth(250);
        btnNivel.setOnAction(e->{
            String sel=comboNivel.getValue();
            if(sel!=null){
                if(sel.startsWith("Nivel")){
                    int nivel=Integer.parseInt(sel.replace("Nivel ",""));
                    controlador.filtrarPorNivel(nivel);
                }else if(sel.equals("Menor a mayor")){
                    controlador.ordenarListaActualPorNivel(true);
                }else if(sel.equals("Mayor a menor")){
                    controlador.ordenarListaActualPorNivel(false);
                }
                actualizar();
            }
        });

        ComboBox<String> comboAtributo=new ComboBox<>();
        comboAtributo.getItems().addAll("DARK","LIGHT","EARTH","WATER","FIRE","WIND","SPELL","TRAP");
        comboAtributo.setPromptText("Atributo");
        comboAtributo.setPrefWidth(250);
        estiloCombo(comboAtributo);
        Button btnAtributo=new Button("Filtrar por atributo");
        estiloBoton(btnAtributo);
        btnAtributo.setPrefWidth(250);
        btnAtributo.setOnAction(e->{
            String sel=comboAtributo.getValue();
            if(sel!=null){
                controlador.filtrarPorAtributo(sel);
                actualizar();
            }
        });

        CheckBox chkAtaque=new CheckBox("Menor a mayor");
        chkAtaque.setStyle("-fx-text-fill:#f5e6c8; -fx-font-size:14px;");
        Button btnAtaque=new Button("Ordenar por ataque");
        estiloBoton(btnAtaque);
        btnAtaque.setPrefWidth(250);
        btnAtaque.setOnAction(e->{
            controlador.ordenarListaActualPorAtaque(chkAtaque.isSelected());
            actualizar();
        });

        CheckBox chkDefensa=new CheckBox("Menor a mayor");
        chkDefensa.setStyle("-fx-text-fill:#f5e6c8; -fx-font-size:14px;");
        Button btnDefensa=new Button("Ordenar por defensa");
        estiloBoton(btnDefensa);
        btnDefensa.setPrefWidth(250);
        btnDefensa.setOnAction(e->{
            controlador.ordenarListaActualPorDefensa(chkDefensa.isSelected());
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

        int total=controlador.getListaActual().contar();
        int indice=controlador.getIndiceActual();

        //Carta anterior (clicable)
        int indiceAnt=(indice-1+total)%total;
        Carta ant=controlador.getListaActual().obtener(indiceAnt);
        if(ant!=null){
            ImagenCarta imgAnt=new ImagenCarta(ant,180);
            ImageView viewAnt=imgAnt.getImagen();
            viewAnt.setOnMouseClicked(e->{
                controlador.anterior();
                actualizar();
            });
            viewAnt.setStyle("-fx-cursor:hand;");
            zonaCartas.getChildren().add(viewAnt);
        }

        //Carta actual
        ImagenCarta imgActual=new ImagenCarta(actual,320);
        zonaCartas.getChildren().add(imgActual.getImagen());

        //Carta siguiente (clicable)
        int indiceSig=(indice+1)%total;
        Carta sig=controlador.getListaActual().obtener(indiceSig);
        if(sig!=null){
            ImagenCarta imgSig=new ImagenCarta(sig,180);
            ImageView viewSig=imgSig.getImagen();
            viewSig.setOnMouseClicked(e->{
                controlador.siguiente();
                actualizar();
            });
            viewSig.setStyle("-fx-cursor:hand;");
            zonaCartas.getChildren().add(viewSig);
        }

        lblNombre.setText(actual.getNombre());
        String info=actual.getAtributo().name();
        if(actual.getAtributo()!=com.example.yugiohexplorer.modelo.TipoAtributo.SPELL &&
                actual.getAtributo()!=com.example.yugiohexplorer.modelo.TipoAtributo.TRAP){
            info+="   •   Nivel "+actual.getNivel()+"   •   ATK "+actual.getAtaque()+"   •   DEF "+actual.getDefensa();
        }
        lblInfo.setText(info);
        lblContador.setText((indice+1)+" / "+total);
    }
}