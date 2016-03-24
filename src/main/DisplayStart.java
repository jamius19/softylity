package main;

import javafx.application.Platform;
import main.Units.IO.settingsManager;

public class DisplayStart implements Runnable {

    public DisplayStart() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            if (!settingsManager.checkForConfigFile()) {
                settingsManager.makeFile();
                Thread.sleep(300);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        new promptDisplay().display();
                    }
                });
            } else {
                settingsManager.readFile();
            }
        } catch (InterruptedException e) {

        }
    }
}
