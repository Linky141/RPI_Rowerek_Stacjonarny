package Main;

import GUI.MainWindow;
import com.pi4j.io.i2c.I2CFactory;

import javax.swing.*;
import java.io.IOException;

public class main {

    public static void main(String[] args) throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {
        MainWindow mainWindow = new MainWindow();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setUndecorated(true);
        mainWindow.setVisible(true);
//        mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainWindow.setExtendedState(mainWindow.getExtendedState());
    }
}
