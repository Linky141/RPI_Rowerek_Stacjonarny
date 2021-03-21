package GUI;

import Engine.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.Arrays;

public class MainWindow  extends JFrame implements ActionListener {




    //region VARIABLES
UIFactory uiFactory = new UIFactory();


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

        LoadAllControls("SampleUi");

    }

    void SetMainWindowTheme(String uiTheme){
        if(uiTheme.equals("SampleUi")) getContentPane().setBackground(Color.darkGray);
        else if(uiTheme.equals("ContrastUi")) getContentPane().setBackground(Color.black);
        else if(uiTheme.equals("ColorUi")) getContentPane().setBackground(Color.red);
    }

    void ReloadAllControls(String uiTheme) {
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
        //endregion

        //region JProgressBar
        pbarVoltageBat = uiFactory.ReloadJProgressBarUISchema(pbarVoltageBat, uiTheme);
        pbarVoltageGen = uiFactory.ReloadJProgressBarUISchema(pbarVoltageGen, uiTheme);
        pbarCurrentCharge = uiFactory.ReloadJProgressBarUISchema(pbarCurrentCharge, uiTheme);
        pbarLoad = uiFactory.ReloadJProgressBarUISchema(pbarLoad, uiTheme);
        //endregion
    }

    void LoadAllControls(String uiTheme){
    SetMainWindowTheme(uiTheme);

    //region JButton
    btnStartStopProgram = uiFactory.NewJButton("On", 10, 200, 200, 30, uiTheme);
    btnStartStopProgram.addActionListener(this);
    add(btnStartStopProgram);
    btnChangeUi1 = uiFactory.NewJButton("UI 1", 750, 390, 50, 30, uiTheme);
    btnChangeUi1.addActionListener(this);
    add(btnChangeUi1);
    btnChangeUi2 = uiFactory.NewJButton("UI 2", 750, 420, 50, 30, uiTheme);
    btnChangeUi2.addActionListener(this);
    add(btnChangeUi2);
    btnChangeUi3 = uiFactory.NewJButton("UI 3", 750, 450, 50, 30, uiTheme);
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
    //endregion

    //region ProgressBar
    pbarVoltageBat = uiFactory.NewJProgressBar(100,0,40,120,1,600,30,uiTheme);
    add(pbarVoltageBat);
    pbarVoltageGen = uiFactory.NewJProgressBar(100,0,40,120,35,600,30,uiTheme);
    add(pbarVoltageGen);
    pbarCurrentCharge = uiFactory.NewJProgressBar(100,0,40,120,70,600,30,uiTheme);
    add(pbarCurrentCharge);
    pbarLoad = uiFactory.NewJProgressBar(100,0,80,130,360,460,120,uiTheme);
    add(pbarLoad);
    //endregion
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
            Test t = new Test(this);
            t.start();
        }
        else if(actionEvent.getSource()==btnChangeUi1){
            ReloadAllControls("SampleUi");
        }
        else if(actionEvent.getSource()==btnChangeUi2){
            ReloadAllControls("ContrastUi");
        }
        else if(actionEvent.getSource()==btnChangeUi3){
            ReloadAllControls("ColorUi");
        }
    }
}
