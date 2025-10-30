package GUI;

import Logica.Columna;
import Logica.Carta;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import javafx.scene.layout.StackPane;

public class ColumnaGUI extends VBox {

    private Columna columna;
    private ArrayList<CartaGUI> cartasGraficas;
    private int indiceColumna;
    private EightOffGameGUI controlador;

    public ColumnaGUI(Columna columna, int indiceColumna, EightOffGameGUI controlador) {
        this.columna = columna;
        this.indiceColumna = indiceColumna;
        this.controlador = controlador;
        this.cartasGraficas = new ArrayList<>();

        setSpacing(-80);
        setOnMouseClicked(e -> controlador.intentarMoverA(indiceColumna));
        actualizar();
    }

    public void actualizar() {
        getChildren().clear();
        cartasGraficas.clear();

        Carta[] cartas = obtenerCartas();
        if (cartas.length == 0) {
            // Si la columna está vacía, mostrar espacio visual
            getChildren().add(crearEspacioVacioVisual());
        } else {
            for (Carta carta : cartas) {
                CartaGUI cartaGUI = new CartaGUI(carta, indiceColumna, controlador);
                cartasGraficas.add(cartaGUI);
                getChildren().add(cartaGUI);
            }
        }
    }

    private Carta[] obtenerCartas() {
        ArrayList<Carta> lista = new ArrayList<>();
        var actual = columna.getCartas().getInicio();
        while (actual != null) {
            lista.add(actual.getInfo());
            actual = actual.getSig();
        }
        return lista.toArray(new Carta[0]);
    }

    public Columna getColumna() {
        return columna;
    }

    public CartaGUI getUltimaCartaGUI() {
        if (cartasGraficas.isEmpty()) {
            return null;
        }
        return cartasGraficas.get(cartasGraficas.size() - 1);
    }

    public void seleccionarUltimaCarta() {
        CartaGUI ultima = getUltimaCartaGUI();
        if (ultima != null) {
            ultima.seleccionar(true);
        }
    }

    public void deseleccionarUltimaCarta() {
        CartaGUI ultima = getUltimaCartaGUI();
        if (ultima != null) {
            ultima.seleccionar(false);
        }
    }

    private StackPane crearEspacioVacioVisual() {
        javafx.scene.shape.Rectangle fondo = new javafx.scene.shape.Rectangle(90, 120);
        fondo.setArcWidth(10);
        fondo.setArcHeight(10);
        fondo.setFill(javafx.scene.paint.Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
        fondo.setStroke(javafx.scene.paint.Color.GRAY);
        fondo.setStrokeWidth(1.5);

        StackPane espacio = new StackPane(fondo);
        espacio.setMouseTransparent(true);
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
