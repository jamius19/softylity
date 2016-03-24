package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Units.IO.settingsManager;
import main.Units.Security.Security;

import java.io.IOException;

public class promptDisplay {
    boolean firstRun = true;
    public boolean isReset;

    public void display() {
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("/resources/picture/info.png"));
        Parent root = null;
        Main.mainM.passOK = true;
        try {
            root = FXMLLoader.load(getClass().getResource("layouts/prompt_pass.fxml"));
        } catch (IOException e) {
        }
        primaryStage.setTitle("Setup Wizard");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });

        ControllerPromptDisplay.main.passNextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (Security.checkPass(ControllerPromptDisplay.main.passField.getText())) {
                    ControllerPromptDisplay.main.rightPass.setVisible(true);
                    ControllerPromptDisplay.main.wrongPass.setVisible(false);

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    }

                    settingsManager.writeFile(Security.handler(ControllerPromptDisplay.main.passField.getText()), isReset);
                    settingsManager.readFile();
                    primaryStage.close();
                } else {
                    firstRun = false;
                    ControllerPromptDisplay.main.wrongPass.setVisible(true);
                }
            }
        });

        Scene mainWindowsScene = new Scene(root, 670, 270);
        primaryStage.setResizable(false);
        primaryStage.setScene(mainWindowsScene);
        primaryStage.show();
    }
}