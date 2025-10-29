package Main;

import GUI.EightOffGameGUI;
import Logica.EightOffGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Juego extends Application {
    @Override
    public void start(Stage primaryStage) {
        EightOffGame juego = new EightOffGame();
        EightOffGameGUI vista = new EightOffGameGUI(juego);

        Scene scene = new Scene(vista, 920, 950);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Eight Off - Práctica 4");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
