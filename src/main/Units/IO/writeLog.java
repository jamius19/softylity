package main.Units.IO;

import java.io.*;

/**
 * Created by jamius19 on 3/23/16.
 */
public class writeLog {
    public static void writeFile(String name) {
        String currentUsersHomeDir = System.getProperty("user.home");
        try (PrintWriter out = new PrintWriter(new BufferedWriter(
                new FileWriter(currentUsersHomeDir + "/.blackout19/vsIns/list.log", true)))) {
            out.println(name);
        } catch (IOException e) {
        }
    }

    public static void resetLog(){
        String currentUsersHomeDir = System.getProperty("user.home");
        try {
            PrintWriter out = new PrintWriter(currentUsersHomeDir + "/.blackout19/vsIns/list.log");
        } catch (FileNotFoundException e) {
        }
    }
}
