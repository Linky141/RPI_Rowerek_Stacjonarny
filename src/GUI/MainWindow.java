package GUI;

import Engine.MainThread;
import Engine.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class MainWindow  extends JFrame implements ActionListener {




    //region VARIABLES
UIFactory uiFactory = new UIFactory();
MainThread mainThread= null;
    JLabel BackgroundImage;
    //region Window Controlls

    //region Buttons
    JButton btnStartStopProgram;
    JButton btnLoadAdd;
    JButton btnLoadLess;
    JButton btnChangeUi1;
    JButton btnChangeUi2;
    JButton btnChangeUi3;
    //endregion

    //region Lables
    JLabel lblSVoltageBat;
    JLabel lblSVoltageGen;
    JLabel lblSCurrentCharge;
    JLabel lblVoltageBat;
    JLabel lblVoltageGen;
    JLabel lblCurrentCharge;
    JLabel lblSMode;
    JLabel lblMode;
    JLabel lblMyCredit;
    //endregion

    //region ProgressBar
    JProgressBar pbarVoltageBat;
    JProgressBar pbarVoltageGen;
    JProgressBar pbarCurrentCharge;
    JProgressBar pbarLoad;
    //endregion

    //endregion
boolean customGui = true;


    //endregion

    public MainWindow() throws IOException {


        //region Window
        int windowWidth = 800;
        int windowHeight = 480;
        setSize(windowWidth, windowHeight);
        setTitle("ROWEREK");

        setLayout(null);
        //endregion

        BufferedImage myPicture = ImageIO.read(new File("src/GUI/Assets/Schema2_WindowBG.png"));
        BackgroundImage = new JLabel(new ImageIcon(myPicture));
        BackgroundImage.setBounds(0,0,800,480);

        LoadAllControls("DarkUi");

        add(BackgroundImage);
    }

    void SetMainWindowTheme(String uiTheme) throws IOException {
        if(uiTheme.equals("DarkUi")) {
            getContentPane().setBackground(Color.darkGray);
            BufferedImage myPicture = ImageIO.read(new File("src/GUI/Assets/Schema1_WindowBG.png"));
            BackgroundImage.setIcon(new ImageIcon(myPicture));
        }
        else if(uiTheme.equals("LightUi")) {
            BufferedImage myPicture = ImageIO.read(new File("src/GUI/Assets/Schema2_WindowBG.png"));
          BackgroundImage.setIcon(new ImageIcon(myPicture));
        }
        else if(uiTheme.equals("ColorUi")) {
            BufferedImage myPicture = ImageIO.read(new File("src/GUI/Assets/Schema3_WindowBG.png"));
            BackgroundImage.setIcon(new ImageIcon(myPicture));
        }
    }




    void ReloadAllControls(String uiTheme) throws IOException {
        SetMainWindowTheme(uiTheme);

        //region JButton
        btnStartStopProgram = uiFactory.ReloadJbuttonUISchema(btnStartStopProgram, uiTheme);
        btnChangeUi1 = uiFactory.ReloadJbuttonUISchema(btnChangeUi1, uiTheme);
        btnChangeUi2 = uiFactory.ReloadJbuttonUISchema(btnChangeUi2, uiTheme);
        btnChangeUi3 = uiFactory.ReloadJbuttonUISchema(btnChangeUi3, uiTheme);
        btnLoadAdd = uiFactory.ReloadJbuttonUISchema(btnLoadAdd, uiTheme);
        btnLoadLess = uiFactory.ReloadJbuttonUISchema(btnLoadLess, uiTheme);
        //endregion

        //region JLabel
        lblSVoltageBat = uiFactory.ReloadJLabelUISchema(lblSVoltageBat, uiTheme);
        lblSVoltageGen = uiFactory.ReloadJLabelUISchema(lblSVoltageGen, uiTheme);
        lblSCurrentCharge = uiFactory.ReloadJLabelUISchema(lblSCurrentCharge, uiTheme);
        lblVoltageBat = uiFactory.ReloadJLabelUISchema(lblVoltageBat, uiTheme);
        lblVoltageGen = uiFactory.ReloadJLabelUISchema(lblVoltageGen, uiTheme);
        lblCurrentCharge = uiFactory.ReloadJLabelUISchema(lblCurrentCharge, uiTheme);
        lblSMode = uiFactory.ReloadJLabelUISchema(lblSMode, uiTheme);
        lblMode = uiFactory.ReloadJLabelUISchema(lblMode, uiTheme);
        lblMyCredit= uiFactory.ReloadJLabelUISchema(lblMyCredit, uiTheme);
        //endregion

        //region JProgressBar
        pbarVoltageBat = uiFactory.ReloadJProgressBarUISchema(pbarVoltageBat, uiTheme);
        pbarVoltageGen = uiFactory.ReloadJProgressBarUISchema(pbarVoltageGen, uiTheme);
        pbarCurrentCharge = uiFactory.ReloadJProgressBarUISchema(pbarCurrentCharge, uiTheme);
        pbarLoad = uiFactory.ReloadJProgressBarUISchema(pbarLoad, uiTheme);
        //endregion
    }

    void LoadAllControls(String uiTheme) throws IOException {
    SetMainWindowTheme(uiTheme);

    //region JButton
    btnStartStopProgram = uiFactory.NewJButton("On", 10, 200, 200, 30, uiTheme);
    btnStartStopProgram.addActionListener(this);
    add(btnStartStopProgram);
    btnChangeUi1 = uiFactory.NewJButton("UI 1", 730, 390, 70, 30, uiTheme);
    btnChangeUi1.addActionListener(this);
    add(btnChangeUi1);
    btnChangeUi2 = uiFactory.NewJButton("UI 2", 730, 420, 70, 30, uiTheme);
    btnChangeUi2.addActionListener(this);
    add(btnChangeUi2);
    btnChangeUi3 = uiFactory.NewJButton("UI 3", 730, 450, 70, 30, uiTheme);
    btnChangeUi3.addActionListener(this);
    add(btnChangeUi3);
    btnLoadAdd = uiFactory.NewJButton("+", 600, 360, 120, 120, uiTheme);
    btnLoadAdd.addActionListener(this);
    add(btnLoadAdd);
    btnLoadLess = uiFactory.NewJButton("-", 1, 360, 120, 120, uiTheme);
    btnLoadLess.addActionListener(this);
    add(btnLoadLess);
    //endregion

    //region JLabel
    lblSVoltageBat = uiFactory.NewJLabel("Baterry Voltage", 1,1,100,30, uiTheme);
    add(lblSVoltageBat);
    lblSVoltageGen = uiFactory.NewJLabel("Generator Voltage", 1,35,300,30, uiTheme);
    add(lblSVoltageGen);
    lblSCurrentCharge = uiFactory.NewJLabel("Charging Current", 1,70,200,30, uiTheme);
    add(lblSCurrentCharge);
    lblVoltageBat = uiFactory.NewJLabel("0.00V", 730,1,100,30, uiTheme);
    add(lblVoltageBat);
    lblVoltageGen = uiFactory.NewJLabel("0.00V", 730,35,100,30, uiTheme);
    add(lblVoltageGen);
    lblCurrentCharge = uiFactory.NewJLabel("0.00A", 730,70,100,30, uiTheme);
    add(lblCurrentCharge);
    lblSMode = uiFactory.NewJLabel("Mode:", 1,120,100,30, uiTheme);
    add(lblSMode);
    lblMode = uiFactory.NewJLabel("CHARGING", 40,120,200,30, uiTheme);
    add(lblMode);
    lblMyCredit= uiFactory.NewJLabel("Linky141 2021", 20,330,200,30, uiTheme);
    add(lblMyCredit);
    //endregion

    //region ProgressBar
    pbarVoltageBat = uiFactory.NewJProgressBar(100,0,40,120,1,600,30,uiTheme);
    add(pbarVoltageBat);
    pbarVoltageGen = uiFactory.NewJProgressBar(100,0,40,120,35,600,30,uiTheme);
    add(pbarVoltageGen);
    pbarCurrentCharge = uiFactory.NewJProgressBar(100,0,40,120,70,600,30,uiTheme);
    add(pbarCurrentCharge);
    pbarLoad = uiFactory.NewJProgressBar(10,0,80,130,360,460,120,uiTheme);
    add(pbarLoad);
    //endregion
}

void RestartValuesAllControls(){
    pbarCurrentCharge.setValue(pbarCurrentCharge.getMinimum());
    pbarVoltageGen.setValue(pbarVoltageGen.getMinimum());
    pbarVoltageBat.setValue(pbarVoltageBat.getMinimum());
    pbarLoad.setValue(pbarLoad.getMinimum());
    lblCurrentCharge.setText("0A");
    lblVoltageBat.setText("0V");
    lblVoltageGen.setText("0V");
    lblMode.setText("DEFAULT");
}

    public void setLoadIndicator(int val){
        pbarLoad.setValue(val);
    }

    public void setVoltageBatIndicator(int val){
        pbarVoltageBat.setValue(val);
        lblVoltageBat.setText(Integer.toString(val) + "V");
    }

    public void setVoltageGenIndicator(int val){
        pbarVoltageGen.setValue(val);
        lblVoltageGen.setText(Integer.toString(val) + "V");
    }

    public void setCurrentChargeIndicator(int val){
        pbarCurrentCharge.setValue(val);
        lblCurrentCharge.setText(Integer.toString(val) + "A");
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==btnStartStopProgram) {
            if(mainThread == null){
                mainThread = new MainThread(this);
                mainThread.start();
                btnStartStopProgram.setText("OFF");
            }
            else
            {
                mainThread.interrupt();
                mainThread = null;
                btnStartStopProgram.setText("ON");
                RestartValuesAllControls();
            }

        }
        if(actionEvent.getSource()==btnLoadAdd) {
            if(!(mainThread == null)) {
                mainThread.IncreaseLoad();
            }
        }
        if(actionEvent.getSource()==btnLoadLess) {
            if(!(mainThread == null)) {
                mainThread.DecreaseLoad();
            }
        }
        else if(actionEvent.getSource()==btnChangeUi1){
            try {
                ReloadAllControls("DarkUi");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(actionEvent.getSource()==btnChangeUi2){
            try {
                ReloadAllControls("LightUi");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(actionEvent.getSource()==btnChangeUi3){
            try {
                ReloadAllControls("ColorUi");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
