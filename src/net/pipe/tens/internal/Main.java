package net.pipe.tens.internal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/net/pipe/tens/files/fxml/tens.fxml"));
        primaryStage.setTitle("Tens");
        primaryStage.setScene(new Scene(root, 750, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.show();//fixme below
        primaryStage.getIcons().add(new Image("/net/pipe/tens/files/css/icon.png"));
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            try {
                stop();
            } catch (Exception ignored) {
            }
        });
        primaryStage.centerOnScreen();
    }


    public static void main(String[] args) {launch(args);}
}
