package GUI;

import Logica.Carta;
import Logica.CeldaLibre;
import javafx.scene.layout.HBox;
import java.util.ArrayList;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class CeldaLibreGUI extends HBox {

    private CeldaLibre celdas;
    private ArrayList<CartaGUI> cartasGraficas;

    public CeldaLibreGUI(CeldaLibre celdas) {
        this.celdas = celdas;
        this.cartasGraficas = new ArrayList<>();
        setSpacing(10); // separación entre cartas
        actualizar();
    }

    public void actualizar() {
        getChildren().clear();
        cartasGraficas.clear();

        Carta[] cartas = obtenerCartas();
        for (Carta carta : cartas) {
            CartaGUI cartaGUI = new CartaGUI(carta);
            cartasGraficas.add(cartaGUI);
            getChildren().add(cartaGUI);
        }

        // Si hay menos de 8 cartas, mostrar espacios vacíos
        int espaciosRestantes = 8 - cartas.length;
        for (int i = 0; i < espaciosRestantes; i++) {
            StackPane espacioVacio = crearEspacioVacioVisual();
            getChildren().add(espacioVacio);
        }
    }

    private Carta[] obtenerCartas() {
        ArrayList<Carta> lista = new ArrayList<>();
        var actual = celdas.getCeldas().getInicio();
        while (actual != null) {
            lista.add(actual.getInfo());
            actual = actual.getSig();
        }
        return lista.toArray(new Carta[0]);
    }

    private CartaGUI crearEspacioVacio() {
        CartaGUI vacio = new CartaGUI(new Carta(0, Carta.Palo.TREBOL)); // carta dummy
        vacio.setOpacity(0.2); // visualmente tenue
        vacio.setMouseTransparent(true); // no interactuable
        return vacio;
    }

    public CeldaLibre getCeldaLibre() {
        return celdas;
    }

    public CartaGUI getCartaGUI(int index) {
        if (index >= 0 && index < cartasGraficas.size()) {
            return cartasGraficas.get(index);
        }
        return null;
    }

    private StackPane crearEspacioVacioVisual() {
        Rectangle fondo = new Rectangle(100, 130);
        fondo.setArcWidth(10);
        fondo.setArcHeight(10);
        fondo.setFill(javafx.scene.paint.Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
        fondo.setStroke(javafx.scene.paint.Color.GRAY);
        fondo.setStrokeWidth(1.5);

        StackPane espacio = new StackPane(fondo);
        espacio.setMouseTransparent(true);
        return espacio;
    }
}
