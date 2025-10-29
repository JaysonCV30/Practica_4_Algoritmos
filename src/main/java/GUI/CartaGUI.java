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
        Image img = new Image("/resources/cartas/" + nombreArchivo + ".png");
        imagen = new ImageView(img);
        imagen.setFitWidth(70);
        imagen.setFitHeight(100);

        // Borde para selección visual
        borde = new Rectangle(70, 100);
        borde.setArcWidth(10);
        borde.setArcHeight(10);
        borde.setFill(null);
        borde.setStrokeWidth(2);
        borde.setStroke(null); // sin borde por defecto

        getChildren().addAll(imagen, borde);
    }

    private String generarNombreImagen(Carta carta) {
        String valor = switch (carta.getValor()) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> String.valueOf(carta.getValor());
        };

        String palo = switch (carta.getPalo()) {
            case CORAZON -> "corazon_rojo";
            case DIAMANTE -> "diamante_rojo";
            case ESPADA -> "pica_negro";
            case TREBOL -> "trebol_negro";
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
        imagen.setImage(new Image("/resources/cartas/" + nombreArchivo + ".png"));
    }
}
