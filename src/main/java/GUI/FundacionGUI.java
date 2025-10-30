package GUI;

import Logica.Carta;
import Logica.Fundacion;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;
import javafx.scene.shape.Rectangle;

public class FundacionGUI extends StackPane {

    private Fundacion fundacion;
    private ArrayList<CartaGUI> cartasGraficas;
    private int indiceFundacion;
    private EightOffGameGUI controlador;

    public FundacionGUI(Fundacion fundacion, int indiceFundacion, EightOffGameGUI controlador) {
        this.fundacion = fundacion;
        this.indiceFundacion = indiceFundacion;
        this.controlador = controlador;
        this.cartasGraficas = new ArrayList<>();

        setOnMouseClicked(e -> controlador.intentarMoverA(indiceFundacion + 16));
        actualizar();
    }

    public void actualizar() {
        getChildren().clear();
        cartasGraficas.clear();

        Carta[] cartas = obtenerCartas();
        for (Carta carta : cartas) {
            CartaGUI cartaGUI = new CartaGUI(carta, indiceFundacion + 16, controlador);
            cartasGraficas.add(cartaGUI);
        }

        if (!cartasGraficas.isEmpty()) {
            getChildren().add(cartasGraficas.get(cartasGraficas.size() - 1)); // solo mostrar la última
        } else {
            getChildren().add(crearEspacioVacioVisual());
        }
    }

    private Carta[] obtenerCartas() {
        ArrayList<Carta> lista = new ArrayList<>();
        var actual = fundacion.getCartas().getInicio();
        while (actual != null) {
            lista.add(actual.getInfo());
            actual = actual.getSig();
        }
        return lista.toArray(new Carta[0]);
    }

    private StackPane crearEspacioVacioVisual() {
        Rectangle fondo = new Rectangle(90, 120);
        fondo.setArcWidth(10);
        fondo.setArcHeight(10);
        fondo.setFill(javafx.scene.paint.Color.LIGHTGRAY.deriveColor(0, 1, 1, 0.3));
        fondo.setStroke(javafx.scene.paint.Color.GRAY);
        fondo.setStrokeWidth(1.5);

        StackPane espacio = new StackPane(fondo);
        espacio.setMouseTransparent(true);
        return espacio;
    }

    public Fundacion getFundacion() {
        return fundacion;
    }

    public CartaGUI getTopeGUI() {
        if (cartasGraficas.isEmpty()) {
            return null;
        }
        return cartasGraficas.get(cartasGraficas.size() - 1);
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
