package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.Arrays;

public class MainWindow  extends JFrame implements ActionListener {




    //region VARIABLES

    //region Window Controlls
    //region Buttons
    JButton btnStartProgram;
    JButton btnStopProgram;
    JButton btnOutputMode;
    JButton btnCloseProgram;
    //endregion
    //region Lables
    JLabel lblSTBaterry;
    JLabel lblChargingMode;
    JLabel lblCurrentVoltage1;
    JLabel lblCurrentVoltage2;
    //endregion
    //region Textbox

    //endregion
    //region ProgressBar
    JProgressBar pbarVoltageInLimit;
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
        if(customGui) getContentPane().setBackground(Color.darkGray);
        setLayout(null);
        //endregion

        //region Buttons
        btnStartProgram = new JButton("On");
        btnStartProgram.setBounds(10,500,200,30);
        if(customGui) btnStartProgram.setBackground(Color.darkGray);
        if(customGui) btnStartProgram.setForeground(Color.lightGray);
        if(customGui) btnStartProgram.setBorder(BorderFactory.createMatteBorder(2,6,2,6,Color.lightGray));
        if(customGui) btnStartProgram.setFocusPainted(false);
        btnStartProgram.addActionListener(this);
        add(btnStartProgram);

        btnStopProgram = new JButton("Off");
        btnStopProgram.setBounds(220,500,200,30);
        if(customGui) btnStopProgram.setBackground(Color.darkGray);
        if(customGui) btnStopProgram.setForeground(Color.lightGray);
        if(customGui) btnStopProgram.setBorder(BorderFactory.createMatteBorder(2,6,2,6,Color.lightGray));
        if(customGui) btnStopProgram.setFocusPainted(false);
        btnStopProgram.addActionListener(this);
        add(btnStopProgram);

        btnOutputMode = new JButton("Output power ON");
        btnOutputMode.setBounds(10,460,420,30);
        if(customGui) btnOutputMode.setBackground(Color.darkGray);
        if(customGui) btnOutputMode.setForeground(Color.lightGray);
        if(customGui) btnOutputMode.setBorder(BorderFactory.createMatteBorder(2,6,2,6,Color.lightGray));
        if(customGui) btnOutputMode.setFocusPainted(false);
        btnOutputMode.addActionListener(this);
       add(btnOutputMode);

        btnCloseProgram = new JButton("Close");
        btnCloseProgram.setBounds(10,700,100,30);
        if(customGui) btnCloseProgram.setBackground(Color.darkGray);
        if(customGui) btnCloseProgram.setForeground(Color.lightGray);
        if(customGui) btnCloseProgram.setBorder(BorderFactory.createMatteBorder(2,6,2,6,Color.lightGray));
        if(customGui) btnCloseProgram.setFocusPainted(false);
        btnCloseProgram.addActionListener(this);
        add(btnCloseProgram);
        //endregion

        //region Lables
        lblSTBaterry = new JLabel("Batery");
        lblSTBaterry.setBounds(10,10,100,30);
        if(customGui) lblSTBaterry.setForeground(Color.lightGray);
        add(lblSTBaterry);

        lblChargingMode = new JLabel("Work mode: ---");
        lblChargingMode.setBounds(10,130,300,30);
        if(customGui) lblChargingMode.setForeground(Color.lightGray);
        add(lblChargingMode);

        lblCurrentVoltage1 = new JLabel("Batery: -.--V");
        lblCurrentVoltage1.setBounds(100,40,200,30);
        if(customGui) lblCurrentVoltage1.setForeground(Color.lightGray);
        add(lblCurrentVoltage1);

        lblCurrentVoltage2 = new JLabel("Generator: -.--V");
        lblCurrentVoltage2.setBounds(10,90,200,30);
        if(customGui) lblCurrentVoltage2.setForeground(Color.lightGray);
        add(lblCurrentVoltage2);
        //endregion

        //region Textbox
        //endregion

        //region ProgressBar
        pbarVoltageInLimit = new JProgressBar();
        pbarVoltageInLimit.setBounds(90,10,500,30);
        pbarVoltageInLimit.setMinimum(100);
        pbarVoltageInLimit.setMaximum(100);
        pbarVoltageInLimit.setValue(0);
        if(customGui) pbarVoltageInLimit.setBackground(Color.darkGray);
        if(customGui) pbarVoltageInLimit.setForeground(Color.gray);
        if(customGui) pbarVoltageInLimit.setBorder(BorderFactory.createMatteBorder(6,1,6,1,Color.lightGray));
        add(pbarVoltageInLimit);
        //endregion

    }




    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==btnStartProgram) {

        }
        else if(actionEvent.getSource()==btnStopProgram){

        }
        else if(actionEvent.getSource()==btnOutputMode){

        }
        else if(actionEvent.getSource()==btnCloseProgram){

        }
    }
}
