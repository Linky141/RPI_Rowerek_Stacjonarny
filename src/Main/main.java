package Main;

import GUI.MainWindow;

import javax.swing.*;
import java.io.IOException;

public class main {

    public static void main(String[] args) throws IOException {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setUndecorated(true);
        mainWindow.setVisible(true);
//        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setExtendedState(mainWindow.getExtendedState());
    }
}
