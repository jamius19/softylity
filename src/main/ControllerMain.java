package main;

import com.sun.deploy.uitoolkit.impl.fx.HostServicesFactory;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import main.Units.IO.readLog;
import main.Units.IO.settingsManager;
import main.Units.IO.writeLog;
import main.Units.aptgetHandler.*;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

//Singleton class
public class ControllerMain implements Initializable {
    public static ControllerMain main;
    public ObservableList<String> out = FXCollections.observableArrayList();

    @FXML
    public Button repoButton;
    @FXML
    public TextField repoInput;
    @FXML
    public TextArea outputLog;
    @FXML
    public TextField statusField;
    @FXML
    public TextField keyInput;
    @FXML
    public Button keyButton;
    @FXML
    public TextField installInput;
    @FXML
    public Button installButton;
    @FXML
    public Button uninstallButton;
    @FXML
    public Button fullInstallButton;
    @FXML
    public Button detailButton;
    @FXML
    public Button hideButton;
    @FXML
    public ListView logHistoryP;
    @FXML
    public Button logResetButton;
    @FXML
    public Button resetPassButton;
    @FXML
    public Button allReset;
    @FXML
    public Label softylityTag;
    @FXML
    public Label passStatusText;
    @FXML
    public Label passOk;
    @FXML
    public Label passWrong;
    @FXML
    public ImageView forkMe;
    @FXML
    public CheckBox withDis;
    boolean isLocked;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        main = this;
        outputLog.setVisible(false);
        hideButton.setVisible(false);
        Thread t = new readLog().t;

        Main.mainM.window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(isLocked)
                    event.consume();
                else
                    Main.mainM.window.close();
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.statusField.setText("Ready to roll!");
            }
        });

        repoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!repoInput.getText().equals("")) {
                    new aptAddRepo(repoInput.getText());
                    lock();
                } else{
                    lock();
                    new aptUpdate();
                }
            }
        });

        keyButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!keyInput.getText().equals("")) {
                    new aptAddKey(keyInput.getText());
                    lock();
                }
            }
        });

        installButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!installInput.getText().equals("")) {
                    new aptInstall(installInput.getText());
                    lock();
                }
            }
        });

        forkMe.addEventHandler(MouseEvent.MOUSE_CLICKED ,new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                HostServicesDelegate hostServices = HostServicesFactory.getInstance(Main.mainM);
                hostServices.showDocument("https://github.com/jamius19/softylity");
                event.consume();
            }
        });



        uninstallButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!installInput.getText().equals("")){
                    new aptRemove(installInput.getText());
                    lock();
                }
            }
        });

        fullInstallButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!installInput.getText().equals("")) {
                    new wholeInstallHandler();
                    lock();
                }
            }
        });

        detailButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                outputLog.setVisible(true);
                hideButton.setVisible(true);
                detailButton.setDisable(true);
                softylityTag.setVisible(false);
                passStatusText.setVisible(false);
                if (Main.mainM.passOK)
                    passOk.setVisible(false);
                else
                    passWrong.setVisible(false);
            }
        });

        allReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                settingsManager.reset();
            }
        });

        hideButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                softylityTag.setVisible(true);
                outputLog.setVisible(false);
                detailButton.setDisable(false);
                hideButton.setVisible(false);
                passStatusText.setVisible(true);
                if (Main.mainM.passOK)
                    passOk.setVisible(true);
                else
                    passWrong.setVisible(true);
            }
        });

        logResetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                writeLog.resetLog();
                ObservableList<String> clear = FXCollections.emptyObservableList();
                logHistoryP.setItems(clear);
            }
        });

        resetPassButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                promptDisplay prompt = new promptDisplay();
                prompt.isReset = true;
                prompt.display();
                Main.mainM.passOK = true;
            }
        });



        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                logHistoryP.setItems(out);
            }
        });
    }

    public void lock() {
        isLocked = true;
        repoButton.setDisable(true);
        keyButton.setDisable(true);
        installButton.setDisable(true);
        fullInstallButton.setDisable(true);
        uninstallButton.setDisable(true);
    }

    public void unlock() {
        isLocked = false;
        repoButton.setDisable(false);
        keyButton.setDisable(false);
        installButton.setDisable(false);
        fullInstallButton.setDisable(false);
        uninstallButton.setDisable(false);
    }
}
