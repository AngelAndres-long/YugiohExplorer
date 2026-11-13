package com.example.yugiohexplorer.vista;

import com.example.yugiohexplorer.modelo.Carta;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Carga y muestra la imagen de una carta.
 */
public class ImagenCarta{

    private ImageView imagen;

    public ImagenCarta(Carta carta,double ancho){
        String ruta="/imagenes/"+carta.getNumero()+".png";
        Image img=new Image(getClass().getResourceAsStream(ruta));
        imagen=new ImageView(img);
        imagen.setFitWidth(ancho);
        imagen.setPreserveRatio(true);
    }

    public ImageView getImagen(){
        return imagen;
    }
}