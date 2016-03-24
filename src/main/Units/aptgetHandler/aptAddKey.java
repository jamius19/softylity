package main.Units.aptgetHandler;

import javafx.application.Platform;
import main.ControllerMain;
import main.Main;
import main.Units.Security.Security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class aptAddKey implements Runnable {
    String keyAddr;
    String outS;
    public Thread t;

    public aptAddKey(String keyAddr) {
        this.keyAddr = keyAddr;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Runnable outputR = new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.outputLog.appendText(outS);
            }
        };

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.statusField.setText("Adding the key file");
            }
        });
        String currentUsersHomeDir = System.getProperty("user.home");

        String[] getKeyCommand = {"/bin/bash", "-c", "echo "+ Security.pass +" | sudo -S wget " + keyAddr + " -O " + currentUsersHomeDir + "/temp.key"};
        String[] addKeyCommand = {"/bin/bash", "-c", "echo "+ Security.pass +" | sudo -S apt-key add '" + currentUsersHomeDir + "/temp.key'"};
        String[] removeKeyCommand = {"/bin/bash", "-c", "echo "+ Security.pass +" | sudo -S rm '" + currentUsersHomeDir + "/temp.key'"};

        try {
            ProcessBuilder getKey = new ProcessBuilder(getKeyCommand);
            getKey.start();
            t.sleep(2000);
            ProcessBuilder addkey = new ProcessBuilder(addKeyCommand);
            addkey.redirectErrorStream(true);
            Process process = addkey.start();

            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = reader.readLine()) != null) {
                outS = line + "\n";
                Platform.runLater(outputR);
            }
            outS = null;
            ProcessBuilder removeKey = new ProcessBuilder(removeKeyCommand);
            removeKey.start();
            ControllerMain.main.unlock();
        } catch (IOException | InterruptedException f) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ControllerMain.main.outputLog.setText("An Error Occured!");
                }
            });
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.statusField.setText("Successfully added the key!");
            }
        });
    }
}
