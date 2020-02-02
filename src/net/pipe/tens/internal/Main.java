package net.pipe.tens.internal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static int numWins  = 0;
    public static int numPlays = 0;
    /**
     * - Restart game at any time option.
     * - Exit game at any time option.
     * - 13 cards on the board, 2 rows of 5 and one row of 3.
     * - Pairs that add up to 10 can be removed and cards replace their spot (EX: A & 9 or 2 & 8)
     * - 10s, Jacks, Queens and Kings can only be eliminated if you have all 4 on the board.
     * - Tracks your wins and total games played and always displays both.
     */

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/net/pipe/tens/files/fxml/sample.fxml"));
        primaryStage.setTitle("Tens");
        primaryStage.setScene(new Scene(root, 750, 400));
        primaryStage.show();
        primaryStage.centerOnScreen();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
