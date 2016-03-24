package main.Units.Security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Security {
    public static String pass = "";

    public static String handler(String input){
        String out = "";

        for(int i = 0; i < input.length(); i++){
            char c = input.charAt(i);
            if( c >= 'a' && c <='m') c += 13;
            else if( c >= 'A' && c <='M') c += 13;
            else if( c >= 'n' && c <='z') c -= 13;
            else if( c >= 'N' && c <='Z') c -= 13;
            out+= Character.toString(c);
        }
        return out;
    }

    public static boolean checkPass(String pass){
        boolean passOk = true;
        String[] update = {"/bin/bash", "-c", "echo "+ pass +"| sudo -S ls"};

        try {
            ProcessBuilder builder2 = new ProcessBuilder(update);
            builder2.redirectErrorStream(true);
            Process process2 = builder2.start();

            InputStream is2 = process2.getInputStream();
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));

            String line2 = null;
            while ((line2 = reader2.readLine()) != null) {
                String outUpdate = line2 + "\n";

                if(line2.contains("incorrect password attempt"))
                    passOk = false;
            }
        } catch (IOException f) {
        }

        return passOk;
    }
}
