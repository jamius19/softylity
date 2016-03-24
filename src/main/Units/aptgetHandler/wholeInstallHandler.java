package main.Units.aptgetHandler;

import main.ControllerMain;


public class wholeInstallHandler implements Runnable {

    public wholeInstallHandler() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        aptAddRepo addRepo = null;
        if (!ControllerMain.main.repoInput.getText().equals(""))
            addRepo = new aptAddRepo(ControllerMain.main.repoInput.getText());
        if (addRepo != null)
            try {
                addRepo.t.join();
                addRepo.childUpdate.join();
            } catch (InterruptedException e) {
            }

        aptAddKey addKey = null;
        if (!ControllerMain.main.keyInput.getText().equals(""))
            addKey = new aptAddKey(ControllerMain.main.keyInput.getText());
        if (addKey != null)
            try {
                addKey.t.join();
            } catch (InterruptedException e) {
            }

        aptInstall aptIn = null;
        if (!ControllerMain.main.installInput.getText().equals(""))
            aptIn = new aptInstall(ControllerMain.main.installInput.getText());
        if (aptIn != null)
            try {
                aptIn.t.join();
            } catch (InterruptedException e) {
            }
    }
}
