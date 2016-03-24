package main.Units.aptgetHandler;

import javafx.application.Platform;
import main.ControllerMain;
import main.Units.IO.writeLog;
import main.Units.Security.Security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class aptInstall implements Runnable {
    String packageName;
    public Thread t;
    boolean successfull = true, unmet, dpkg;

    public aptInstall(String packageName) {
        this.packageName = packageName;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.statusField.setText("Installing the package.");
            }
        });

        String[] addRepo = {"/bin/bash", "-c", "echo "+ Security.pass +"| sudo -S apt-get install -y " + packageName};

        try {
            ProcessBuilder builder = new ProcessBuilder(addRepo);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = reader.readLine()) != null) {
                String outS = line + "\n";
                if(line.contains("Unable to locate"))
                    successfull = false;
                else if (line.contains("unmet dependencies")) {
                    successfull = false;
                    unmet = true;
                } else if(line.contains("dpkg was interrupted"))
                    dpkg = true;


                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ControllerMain.main.outputLog.appendText(outS);
                    }
                });
            }

            if(dpkg){
                new dpkgConfig(packageName, true);
                return;
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
                ControllerMain.main.statusField.setText((successfull)? "Successfully installed the package!" :
                        (unmet) ? "Failed to install the package! (Unmet Dependencies!)" :
                                "Failed to install the package! (Added the repository?)");
            }
        });

        if(successfull) {
            String outLog = "Package name: " + packageName + "   -->   Operation: Installed";
            writeLog.writeFile(outLog);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ControllerMain.main.out.add(outLog);
                    ControllerMain.main.logHistoryP.setItems(ControllerMain.main.out);
                }
            });
        }
    }
}
