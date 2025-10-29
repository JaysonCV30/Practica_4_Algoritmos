package GUI;

import Logica.Carta;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class CartaGUI extends StackPane {

    private Carta carta;
    private ImageView imagen;
    private Rectangle borde;

    public CartaGUI(Carta carta) {
        this.carta = carta;

        // Cargar imagen según nombre
        String nombreArchivo = generarNombreImagen(carta);
        var url = getClass().getResource("/cartas/" + nombreArchivo + ".png");

        if (url != null) {
            Image img = new Image(url.toExternalForm());
            imagen = new ImageView(img);
        } else {
            System.out.println("Imagen no encontrada: " + nombreArchivo);
            imagen = new ImageView(); // imagen vacía
            imagen.setOpacity(0.2);   // visualmente tenue
        }

        imagen.setFitWidth(100);
        imagen.setFitHeight(130);

        // Borde para selección visual
        borde = new Rectangle(90, 120);
        borde.setArcWidth(10);
        borde.setArcHeight(10);
        borde.setFill(null);
        borde.setStrokeWidth(2);
        borde.setStroke(null); // sin borde por defecto

        getChildren().addAll(imagen, borde);
    }

    private String generarNombreImagen(Carta carta) {
        String valor = switch (carta.getValor()) {
            case 1 ->
                "As";
            case 11 ->
                "J";
            case 12 ->
                "Q";
            case 13 ->
                "K";
            default ->
                String.valueOf(carta.getValor());
        };

        String palo = switch (carta.getPalo()) {
            case CORAZON ->
                "corazon_rojo";
            case DIAMANTE ->
                "diamante_rojo";
            case PICA ->
                "pica_negro";
            case TREBOL ->
                "trebol_negro";
        };

        return valor + "_" + palo;
    }

    public Carta getCarta() {
        return carta;
    }

    public void seleccionar() {
        borde.setStroke(javafx.scene.paint.Color.BLUE);
    }

    public void deseleccionar() {
        borde.setStroke(null);
    }

    public void actualizarImagen() {
        String nombreArchivo = generarNombreImagen(carta);
        var url = getClass().getResource("/cartas/" + nombreArchivo + ".png");

        if (url != null) {
            Image img = new Image(url.toExternalForm());
            imagen.setImage(img);
            imagen.setOpacity(1.0); // restaurar opacidad si antes estaba tenue
        } else {
            System.out.println("Imagen no encontrada al actualizar: " + nombreArchivo);
            imagen.setImage(null);
            imagen.setOpacity(0.2); // mostrar como espacio vacío
        }
    }
}
