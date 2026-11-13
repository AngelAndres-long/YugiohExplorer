package com.example.yugiohexplorer;

import com.example.yugiohexplorer.controlador.ControladorVista;
import com.example.yugiohexplorer.vista.PantallaPrincipal;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal que inicia la aplicación.
 */
public class Aplicacion extends Application{

    @Override
    public void start(Stage stage){
        ControladorVista controlador=new ControladorVista();
        PantallaPrincipal pantalla=new PantallaPrincipal(controlador);

        Scene scene=new Scene(pantalla,1200,750);
        stage.setTitle("Yugioh Explorer");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}