package GUI;

import Logica.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EightOffGameGUI extends BorderPane {
    private EightOffGame juego;
    private ColumnaGUI[] columnasGUI;
    private CeldaLibreGUI celdaLibreGUI;
    private FundacionGUI[] fundacionesGUI;
    private Label mensajeEstado;

    public EightOffGameGUI(EightOffGame juego) {
        this.juego = juego;
        setPadding(new Insets(15));
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Top: botones y mensaje
        Button btnDeshacer = new Button("Deshacer");
        Button btnPista = new Button("Pista");
        mensajeEstado = new Label("Bienvenido a Eight Off");
        mensajeEstado.setTextFill(Color.DARKBLUE);

        HBox barraSuperior = new HBox(20, btnDeshacer, btnPista, mensajeEstado);
        barraSuperior.setPadding(new Insets(10));
        setTop(barraSuperior);

        VBox centro = new VBox(20);
        centro.setPadding(new Insets(10));

        celdaLibreGUI = new CeldaLibreGUI(juego.getCeldas());
        HBox celdasBox = new HBox(10, celdaLibreGUI);
        celdasBox.setPadding(new Insets(10));

        // Centro: columnas
        columnasGUI = new ColumnaGUI[8];
        HBox columnasBox = new HBox(10);
        for (int i = 0; i < 8; i++) {
            columnasGUI[i] = new ColumnaGUI(juego.getColumnas()[i]);
            columnasBox.getChildren().add(columnasGUI[i]);
        }
        columnasBox.setPadding(new Insets(10));

        // Agregar ambos al centro
        centro.getChildren().addAll(celdasBox, columnasBox);
        setCenter(centro);

        fundacionesGUI = new FundacionGUI[4];
        HBox fundacionesBox = new HBox(20);
        for (int i = 0; i < 4; i++) {
            fundacionesGUI[i] = new FundacionGUI(juego.getFundaciones()[i]);
            fundacionesBox.getChildren().add(fundacionesGUI[i]);
        }
        fundacionesBox.setPadding(new Insets(10));
        setBottom(fundacionesBox);

        // Eventos
        btnDeshacer.setOnAction(e -> {
            juego.deshacerUltimoMovimiento();
            actualizarTodo();
            mensajeEstado.setText("Último movimiento deshecho");
        });

        btnPista.setOnAction(e -> {
            Carta pista = juego.darPista();
            if (pista != null) {
                mensajeEstado.setText("Puedes mover: " + pista);
            } else {
                mensajeEstado.setText("No hay movimientos válidos");
            }
        });
    }

    public void actualizarTodo() {
        for (ColumnaGUI colGUI : columnasGUI) colGUI.actualizar();
        for (FundacionGUI fundGUI : fundacionesGUI) fundGUI.actualizar();
        celdaLibreGUI.actualizar();

        if (juego.juegoTerminado()) {
            mensajeEstado.setText("¡Juego terminado! Has ganado.");
        }
    }
}
