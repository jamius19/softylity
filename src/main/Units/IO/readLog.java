package main.Units.IO;

import main.ControllerMain;

import java.io.*;

/**
 * Created by jamius19 on 3/23/16.
 */
public class readLog implements Runnable {

    public Thread t;

    public readLog(){
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        readFile();
    }

    public static void readFile() {
        String out = "";
        try {
            String currentUsersHomeDir = System.getProperty("user.home");

            FileInputStream a = new FileInputStream(currentUsersHomeDir + "/.blackout19/vsIns/list.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(a));
            String line = null;
            while ((line = br.readLine()) != null) {
                ControllerMain.main.out.add(line);
            }
            //return  out;
        } catch (IOException e) {
        }
        //return  out;
    }
}
