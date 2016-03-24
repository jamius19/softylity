package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.Units.Security.Security;

public class Main extends Application {
    Stage window;
    public static Main mainM;
    boolean passOK;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainM = this;
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("layouts/main_windows.fxml"));
        primaryStage.setTitle("Softylity");
        Scene mainWindowsScene = new Scene(root, 670, 555);
        primaryStage.getIcons().addAll(new Image("/resources/picture/icon.png"),
                new Image("/resources/picture/icon128.png"),
                new Image("/resources/picture/icon64.png"),
                new Image("/resources/picture/icon32.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(mainWindowsScene);
        primaryStage.show();
        new DisplayStart();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(Security.checkPass(Security.pass)) {
                    passOK = true;
                    ControllerMain.main.passWrong.setVisible(false);
                } else{
                    ControllerMain.main.lock();
                    ControllerMain.main.passOk.setVisible(false);
                    ControllerMain.main.statusField.setText("Password ERROR! Set Password in the Settings tab!");
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void close(){
        window.close();
    }
}
