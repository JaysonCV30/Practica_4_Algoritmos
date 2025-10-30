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
    private EightOffGameGUI controlador;

    public CeldaLibreGUI(CeldaLibre celdas, EightOffGameGUI controlador) {
        this.celdas = celdas;
        this.controlador = controlador;
        this.cartasGraficas = new ArrayList<>();
        setSpacing(10); // separación entre cartas
        actualizar();
    }

    public void actualizar() {
        getChildren().clear();
        cartasGraficas.clear();

        Carta[] cartas = obtenerCartas();
        for (int i = 0; i < cartas.length; i++) {
            Carta carta = cartas[i];
            CartaGUI cartaGUI = new CartaGUI(carta, i + 8, controlador);
            cartasGraficas.add(cartaGUI);
            getChildren().add(cartaGUI);
        }

        // Si hay menos de 8 cartas, mostrar espacios vacíos
        for (int i = cartas.length; i < 8; i++) {
            StackPane espacioVacio = crearEspacioVacioVisual();
            final int destino = i + 8;
            espacioVacio.setOnMouseClicked(e -> controlador.intentarMoverA(destino));
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
        Rectangle fondo = new Rectangle(90, 120);
        fondo.setArcWidth(10);
        fondo.setArcHeight(10);
        fondo.setFill(javafx.scene.paint.Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
        fondo.setStroke(javafx.scene.paint.Color.GRAY);
        fondo.setStrokeWidth(1.5);

        StackPane espacio = new StackPane(fondo);
        return espacio;
    }

    public void marcarSiContiene(Carta carta) {
        for (CartaGUI cartaGUI : cartasGraficas) {
            if (cartaGUI.getCarta().equals(carta)) {
                cartaGUI.marcarComoPista();
            }
        }
    }

    public void limpiarPistas() {
        for (CartaGUI cartaGUI : cartasGraficas) {
            cartaGUI.limpiarPista();
        }
    }
}
