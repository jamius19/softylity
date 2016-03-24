package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerPromptDisplay implements Initializable{

    public static ControllerPromptDisplay main;

    @FXML
    public Button passNextButton;
    @FXML
    public PasswordField passField;
    @FXML
    public Label wrongPass;
    @FXML
    public Label rightPass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        main = this;
    }
}
