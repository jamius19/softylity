package main.Units.aptgetHandler;

import javafx.application.Platform;
import main.ControllerMain;
import main.Units.Security.Security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class aptAddRepo implements Runnable {
    String repoName;
    public Thread t;
    public Thread childUpdate;

    public aptAddRepo(String repoName) {
        this.repoName = repoName;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.statusField.setText("Adding the repository.");
            }
        });

        String[] addRepo = {"/bin/bash", "-c", "echo "+ Security.pass +"| sudo -S add-apt-repository " + repoName};

        try {
            ProcessBuilder builder = new ProcessBuilder(addRepo);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = reader.readLine()) != null) {
                String outS = line + "\n";
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ControllerMain.main.outputLog.appendText(outS);
                    }
                });
            }
            childUpdate = new aptUpdate().t;
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
                ControllerMain.main.statusField.setText("Successfully added the repository!");
            }
        });
    }
}
