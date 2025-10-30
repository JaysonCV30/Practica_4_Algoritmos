package GUI;

import Logica.Carta;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class CartaGUI extends StackPane {

    private Carta carta;
    private ImageView imagen;
    private Rectangle borde;
    private int origenIndex;
    private EightOffGameGUI controlador;
    private boolean seleccionada;
    private boolean esPista = false;

    public CartaGUI(Carta carta, int origenIndex, EightOffGameGUI controlador) {
        this.carta = carta;

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

        imagen.setFitWidth(90);
        imagen.setFitHeight(120);

        getChildren().addAll(imagen);
        setOnMouseClicked(e -> {
            if (controlador.hayCartaSeleccionada()) {
                controlador.intentarMoverA(origenIndex);
            } else {
                controlador.seleccionarCarta(carta, origenIndex, this);
            }
        });
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

    public void seleccionar(boolean sel) {
        seleccionada = sel;
        if (seleccionada) {
            setEffect(new DropShadow(20, Color.BLUE));
        } else {
            setEffect(null);
        }
    }

    public void animarSeleccion() {
        ScaleTransition resaltar = new ScaleTransition(Duration.millis(150), this);
        resaltar.setFromX(1.0);
        resaltar.setToX(1.1);
        resaltar.setFromY(1.0);
        resaltar.setToY(1.1);
        resaltar.setAutoReverse(true);
        resaltar.setCycleCount(2);
        resaltar.play();
    }

    public void temblar() {
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), this);
        shake.setFromX(-5);
        shake.setToX(5);
        shake.setCycleCount(4);
        shake.setAutoReverse(true);
        shake.play();
    }

    public void marcarComoPista() {
        esPista = true;
        setStyle("-fx-border-color: gold; -fx-border-width: 3; -fx-border-radius: 5;");
    }

    public void limpiarPista() {
        esPista = false;
        setStyle(""); 
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
            imagen.setOpacity(1.0); // restaurar opacidad 
        } else {
            System.out.println("Imagen no encontrada al actualizar: " + nombreArchivo);
            imagen.setImage(null);
            imagen.setOpacity(0.2); // mostrar como espacio vacío
        }
    }
}
