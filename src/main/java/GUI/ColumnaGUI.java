package GUI;

import Logica.Columna;
import Logica.Carta;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class ColumnaGUI extends VBox {
    private Columna columna;
    private ArrayList<CartaGUI> cartasGraficas;

    public ColumnaGUI(Columna columna) {
        this.columna = columna;
        this.cartasGraficas = new ArrayList<>();
        setSpacing(-80); // superposición vertical para efecto de pila
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
        if (cartasGraficas.isEmpty()) return null;
        return cartasGraficas.get(cartasGraficas.size() - 1);
    }

    public void seleccionarUltimaCarta() {
        CartaGUI ultima = getUltimaCartaGUI();
        if (ultima != null) ultima.seleccionar();
    }

    public void deseleccionarUltimaCarta() {
        CartaGUI ultima = getUltimaCartaGUI();
        if (ultima != null) ultima.deseleccionar();
    }
}
