package main.Units.IO;

import main.ControllerMain;
import main.Main;
import main.Units.Security.Security;

import java.io.*;

public class settingsManager {
    public static boolean checkForConfigFile() {
        try {
            String currentUsersHomeDir = System.getProperty("user.home");

            FileInputStream a = new FileInputStream(currentUsersHomeDir + "/.blackout19/vsIns/main.config");
            BufferedReader br = new BufferedReader(new InputStreamReader(a));
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static void makeFile() {
        try {
            String currentUsersHomeDir = System.getProperty("user.home");

            String[] mkdir = {"/bin/bash", "-c", "mkdir '" + currentUsersHomeDir + "/.blackout19/'"};
            String[] mkDir2 = {"/bin/bash", "-c", "mkdir '" + currentUsersHomeDir + "/.blackout19/vsIns/'"};

            ProcessBuilder builder2 = new ProcessBuilder(mkdir);
            builder2.start();
            ProcessBuilder builder3 = new ProcessBuilder(mkDir2);
            builder3.start();
        } catch (IOException e) {
        }
    }

    public static void writeFile(String pass, boolean reset){
        try {
            String currentUsersHomeDir = System.getProperty("user.home");

            PrintWriter writer = new PrintWriter(currentUsersHomeDir + "/.blackout19/vsIns/main.config", "UTF-8");
            ControllerMain.main.statusField.setText("Ready to roll again!");
            ControllerMain.main.unlock();
            ControllerMain.main.passWrong.setVisible(false);
            ControllerMain.main.passOk.setVisible(true);

            if(!reset){
                PrintWriter writerLog = new PrintWriter(currentUsersHomeDir + "/.blackout19/vsIns/list.log", "UTF-8");
                writerLog.close();
            }
            writer.println(pass);
            writer.close();
        } catch (IOException e) {
        }
    }

    public static void readFile(){
        try {
            String currentUsersHomeDir = System.getProperty("user.home");

            FileInputStream a = new FileInputStream(currentUsersHomeDir + "/.blackout19/vsIns/main.config");
            BufferedReader br = new BufferedReader(new InputStreamReader(a));
            Security.pass = br.readLine();
        } catch (IOException e) {
        }
    }

    public static void reset(){
        try {
            String currentUsersHomeDir = System.getProperty("user.home");

            String[] deleteDir = {"/bin/bash", "-c", "echo " + Security.pass + "| sudo -S rm -r '" + currentUsersHomeDir + "/.blackout19/'"};

            ProcessBuilder builder2 = new ProcessBuilder(deleteDir);
            builder2.start();
        } catch (IOException e) {
        }

        Main.mainM.close();
    }

}

