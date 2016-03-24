package main.Units.aptgetHandler;

import javafx.application.Platform;
import main.ControllerMain;
import main.Units.Security.Security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class dpkgConfig implements Runnable {
    public Thread t;
    String packageN;
    boolean isInstall;

    //have to update dpkg installer
    public dpkgConfig(String packageName, boolean installing) {
        isInstall = installing;
        packageN = packageName;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.statusField.setText("Configuring the dpkg");
            }
        });

        String[] update = {"/bin/bash", "-c", "echo "+ Security.pass +"| sudo -S dpkg --configure -a"};

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

            if(isInstall)
                new aptInstall(packageN);
            else
                new aptRemove(packageN);

            ControllerMain.main.unlock();
            return;
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
                ControllerMain.main.statusField.setText("dpkg successfully configured!");
            }
        });
    }
}
