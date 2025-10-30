package GUI;

import Logica.*;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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
    private Carta cartaSeleccionada = null;
    private int origenSeleccionado = -1; // índice entre 0 y 19
    private CartaGUI cartaGUISeleccionada;

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

        celdaLibreGUI = new CeldaLibreGUI(juego.getCeldas(), this);
        HBox celdasBox = new HBox(10, celdaLibreGUI);
        celdasBox.setPadding(new Insets(10));

        // Centro: columnas
        columnasGUI = new ColumnaGUI[8];
        HBox columnasBox = new HBox(10);
        for (int i = 0; i < 8; i++) {
            columnasGUI[i] = new ColumnaGUI(juego.getColumnas()[i], i, this);
            columnasBox.getChildren().add(columnasGUI[i]);
        }
        columnasBox.setPadding(new Insets(10));

        // Agregar ambos al centro
        centro.getChildren().addAll(celdasBox, columnasBox);
        setCenter(centro);

        fundacionesGUI = new FundacionGUI[4];
        HBox fundacionesBox = new HBox(20);
        for (int i = 0; i < 4; i++) {
            fundacionesGUI[i] = new FundacionGUI(juego.getFundaciones()[i], i, this);
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
            limpiarPistasVisuales();

            Carta pista = juego.darPista();
            if (pista != null) {
                mensajeEstado.setText("Puedes mover: " + pista);
                marcarCartaVisualmente(pista);
            } else {
                mensajeEstado.setText("No hay movimientos válidos");
            }
        });
    }

    public void actualizarTodo() {
        for (ColumnaGUI colGUI : columnasGUI) {
            colGUI.actualizar();
        }
        for (FundacionGUI fundGUI : fundacionesGUI) {
            fundGUI.actualizar();
        }
        celdaLibreGUI.actualizar();

        if (juego.juegoTerminado()) {
            mensajeEstado.setText("¡Juego terminado! Has ganado.");
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("¡Juego terminado!");
            alerta.setHeaderText(null);
            alerta.setContentText("¡Felicidades! Has ganado la partida.");
            alerta.showAndWait();
        } else if (juego.darPista() == null) {
            mensajeEstado.setText("No hay movimientos válidos.");
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Sin movimientos");
            alerta.setHeaderText(null);
            alerta.setContentText("No hay movimientos válidos disponibles.");
            alerta.showAndWait();
        }
    }

    public void seleccionarCarta(Carta carta, int origen, CartaGUI cartaGUI) {
        // Deseleccionar la anterior
        if (cartaGUISeleccionada != null) {
            cartaGUISeleccionada.seleccionar(false);
        }

        // Guardar nueva
        this.cartaSeleccionada = carta;
        this.origenSeleccionado = origen;
        this.cartaGUISeleccionada = cartaGUI;

        cartaGUISeleccionada.seleccionar(true);
        cartaGUISeleccionada.animarSeleccion();
        mensajeEstado.setText("Carta seleccionada: " + carta);
    }

    public void intentarMoverA(int destino) {
        if (cartaSeleccionada == null || cartaGUISeleccionada == null) {
            return;
        }

        if (destino == origenSeleccionado) {
            mensajeEstado.setText("Mover la carta.");
            return;
        }

        boolean exito = false;

        if (origenSeleccionado >= 0 && origenSeleccionado < 8) {
            exito = juego.moverCartaDesdeColumna(destino, cartaSeleccionada);
        } else if (origenSeleccionado >= 8 && origenSeleccionado < 16) {
            exito = juego.moverCartaDesdeCelda(destino, cartaSeleccionada);
        } else if (origenSeleccionado >= 16 && origenSeleccionado < 20) {
            exito = juego.moverCartaDesdeFundacion(destino, cartaSeleccionada);
        }

        if (exito) {
            mensajeEstado.setText("Movimiento realizado: " + cartaSeleccionada);
            actualizarTodo();
        } else {
            mensajeEstado.setText("Movimiento inválido");
            cartaGUISeleccionada.temblar();
        }

        cartaGUISeleccionada.seleccionar(false);
        cartaSeleccionada = null;
        cartaGUISeleccionada = null;
        origenSeleccionado = -1;
    }

    public boolean hayCartaSeleccionada() {
        return cartaSeleccionada != null && cartaGUISeleccionada != null;
    }

    private void marcarCartaVisualmente(Carta carta) {
        for (ColumnaGUI colGUI : columnasGUI) {
            colGUI.marcarSiContiene(carta);
        }
        celdaLibreGUI.marcarSiContiene(carta);
        for (FundacionGUI fundGUI : fundacionesGUI) {
            fundGUI.marcarSiContiene(carta);
        }
    }

    private void limpiarPistasVisuales() {
        for (ColumnaGUI colGUI : columnasGUI) {
            colGUI.limpiarPistas();
        }
        celdaLibreGUI.limpiarPistas();
        for (FundacionGUI fundGUI : fundacionesGUI) {
            fundGUI.limpiarPistas();
        }
    }
}
