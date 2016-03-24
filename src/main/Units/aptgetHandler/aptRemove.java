package main.Units.aptgetHandler;

import javafx.application.Platform;
import main.ControllerMain;
import main.Units.IO.writeLog;
import main.Units.Security.Security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class aptRemove implements Runnable {
    String packageName;
    boolean successful = true, dpkg;


    public aptRemove(String packageName) {
        this.packageName = packageName;
        new Thread(this).start();
    }

    @Override
    public void run() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ControllerMain.main.statusField.setText("Uninstalling the package.");
            }
        });

        String[] addRepo = {"/bin/bash", "-c", "echo " + Security.pass + "| sudo -S apt-get remove -y " + packageName};

        try {
            ProcessBuilder builder = new ProcessBuilder(addRepo);
            builder.redirectErrorStream(true);
            Process process = builder.start();

            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = null;
            while ((line = reader.readLine()) != null) {
                String outS = line + "\n";
                if (line.contains("Unable to locate") || line.contains("Unable to lock") || line.contains("Could not get lock") )
                    successful = false;
                else if(line.contains("dpkg was interrupted"))
                    dpkg = true;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ControllerMain.main.outputLog.appendText(outS);
                    }
                });
            }

            if(dpkg){
                new dpkgConfig(packageName, false);
                return;
            }


            if (successful) {
                if(ControllerMain.main.withDis.isSelected())
                    new aptAutoRemove();

                String outLog = "Package name: " + packageName + "   -->   Operation: Uninstalled";
                writeLog.writeFile(outLog);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ControllerMain.main.out.add(outLog);
                        ControllerMain.main.logHistoryP.setItems(ControllerMain.main.out);
                    }
                });
            }
            if(!ControllerMain.main.withDis.isSelected())
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
    }
}
