package main.Units.aptgetHandler;

import javafx.application.Platform;
import main.ControllerMain;
import main.Units.Security.Security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class aptAutoRemove implements Runnable {

    public aptAutoRemove() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.statusField.setText("Uninstalling unused dispensaries");
            }
        });

        String[] update = {"/bin/bash", "-c", "echo "+ Security.pass +"| sudo -S apt-get -y autoremove"};

        try {
            ProcessBuilder builder2 = new ProcessBuilder(update);
            builder2.redirectErrorStream(true);
            Process process2 = builder2.start();

            InputStream is2 = process2.getInputStream();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));

            String line2 = null;
            while ((line2 = reader2.readLine()) != null) {
                String outUpdate = line2 + "\n";
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ControllerMain.main.outputLog.appendText(outUpdate);
                    }
                });
            }
            ControllerMain.main.unlock();
        } catch (IOException f) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ControllerMain.main.outputLog.setText("IO Error Occured!");
                }
            });
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.statusField.setText("Completely uninstalled the package!");
            }
        });
    }
}
