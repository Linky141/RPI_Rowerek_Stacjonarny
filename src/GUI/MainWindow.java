package GUI;

import Engine.MainThread;
import com.pi4j.io.i2c.I2CFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class MainWindow extends JFrame implements ActionListener {


    //region VARIABLES
    UIFactory uiFactory = new UIFactory();
    MainThread mainThread = null;
    JLabel BackgroundImage;
    //region Window Controlls

    //region Buttons
    JButton btnChargingStateChange;
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
    JLabel lblMyCredit;
    JLabel lblNapięcieAkumulatoraMinimum;
    JLabel lblNapięcieAkumulatoraMaximum;
    JLabel lblNapięcieRowerkaMinimum;
    JLabel lblNapięcieRowerkaMaksimum;
    JLabel lblPradLadowaniaMinimum;
    JLabel lblPradLadowaniaMaksimum;
    JLabel lblErrorInfo;
    JLabel lblBaterryInfo;
    //endregion

    //region ProgressBar
    JProgressBar pbarVoltageBat;
    JProgressBar pbarVoltageGen;
    JProgressBar pbarCurrentCharge;
    //endregion

    //endregion
    boolean customGui = true;
    String stopinfoText = "Proszę zatrzymać rowerek w celu wdrożenia zmiany";

    //endregion

    public MainWindow() throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {


        //region Window
        int windowWidth = 800;
        int windowHeight = 480;
        setSize(windowWidth, windowHeight);
        setTitle("ROWEREK");

        setLayout(null);
        //endregion

        BufferedImage myPicture = ImageIO.read(new File("src/GUI/Assets/Schema2_WindowBG.png"));
        BackgroundImage = new JLabel(new ImageIcon(myPicture));
        BackgroundImage.setBounds(0, 0, 800, 480);

        LoadAllControls("DarkUi");

        add(BackgroundImage);
        mainThread = new MainThread(this);
        mainThread.start();
    }

    void SetMainWindowTheme(String uiTheme) throws IOException {
        if (uiTheme.equals("DarkUi")) {
            getContentPane().setBackground(Color.darkGray);
            BufferedImage myPicture = ImageIO.read(new File("src/GUI/Assets/Schema1_WindowBG.png"));
            BackgroundImage.setIcon(new ImageIcon(myPicture));
        } else if (uiTheme.equals("LightUi")) {
            BufferedImage myPicture = ImageIO.read(new File("src/GUI/Assets/Schema2_WindowBG.png"));
            BackgroundImage.setIcon(new ImageIcon(myPicture));
        } else if (uiTheme.equals("ColorUi")) {
            BufferedImage myPicture = ImageIO.read(new File("src/GUI/Assets/Schema3_WindowBG.png"));
            BackgroundImage.setIcon(new ImageIcon(myPicture));
        }
    }


    void ReloadAllControls(String uiTheme) throws IOException {
        SetMainWindowTheme(uiTheme);

        //region JButton
        btnChargingStateChange = uiFactory.ReloadJbuttonUISchema(btnChargingStateChange, uiTheme, true);
        btnChangeUi1 = uiFactory.ReloadJbuttonUISchema(btnChangeUi1, uiTheme, false);
        btnChangeUi2 = uiFactory.ReloadJbuttonUISchema(btnChangeUi2, uiTheme, false);
        btnChangeUi3 = uiFactory.ReloadJbuttonUISchema(btnChangeUi3, uiTheme, false);
        //endregion

        //region JLabel
        lblSVoltageBat = uiFactory.ReloadJLabelUISchema(lblSVoltageBat, uiTheme);
        lblSVoltageGen = uiFactory.ReloadJLabelUISchema(lblSVoltageGen, uiTheme);
        lblSCurrentCharge = uiFactory.ReloadJLabelUISchema(lblSCurrentCharge, uiTheme);
        lblVoltageBat = uiFactory.ReloadJLabelUISchema(lblVoltageBat, uiTheme);
        lblVoltageGen = uiFactory.ReloadJLabelUISchema(lblVoltageGen, uiTheme);
        lblCurrentCharge = uiFactory.ReloadJLabelUISchema(lblCurrentCharge, uiTheme);
        lblMyCredit = uiFactory.ReloadJLabelUISchema(lblMyCredit, uiTheme);
        lblNapięcieAkumulatoraMinimum = uiFactory.ReloadJLabelUISchema(lblNapięcieAkumulatoraMinimum, uiTheme);
        lblNapięcieAkumulatoraMaximum = uiFactory.ReloadJLabelUISchema(lblNapięcieAkumulatoraMaximum, uiTheme);
        lblNapięcieRowerkaMinimum = uiFactory.ReloadJLabelUISchema(lblNapięcieRowerkaMinimum, uiTheme);
        lblNapięcieRowerkaMaksimum = uiFactory.ReloadJLabelUISchema(lblNapięcieRowerkaMaksimum, uiTheme);
        lblPradLadowaniaMinimum = uiFactory.ReloadJLabelUISchema(lblPradLadowaniaMinimum, uiTheme);
        lblPradLadowaniaMaksimum = uiFactory.ReloadJLabelUISchema(lblPradLadowaniaMaksimum, uiTheme);
        lblErrorInfo = uiFactory.ReloadJLabelUISchema(lblErrorInfo, uiTheme);
        lblBaterryInfo = uiFactory.ReloadJLabelUISchema(lblBaterryInfo, uiTheme);
        //endregion

        //region JProgressBar
        pbarVoltageBat = uiFactory.ReloadJProgressBarUISchema(pbarVoltageBat, uiTheme);
        pbarVoltageGen = uiFactory.ReloadJProgressBarUISchema(pbarVoltageGen, uiTheme);
        pbarCurrentCharge = uiFactory.ReloadJProgressBarUISchema(pbarCurrentCharge, uiTheme);
        //endregion
    }

    void LoadAllControls(String uiTheme) throws IOException {
        SetMainWindowTheme(uiTheme);

        //region JButton
        btnChargingStateChange = uiFactory.NewJButton("Ładowanie", 10, 220, 300, 230, uiTheme, Color.RED, 30);
        btnChargingStateChange.addActionListener(this);
        add(btnChargingStateChange);
        btnChangeUi1 = uiFactory.NewJButton("UI 1", 700, 390, 100, 30, uiTheme, null, 15);
        btnChangeUi1.addActionListener(this);
        add(btnChangeUi1);
        btnChangeUi2 = uiFactory.NewJButton("UI 2", 700, 420, 100, 30, uiTheme, null, 15);
        btnChangeUi2.addActionListener(this);
        add(btnChangeUi2);
        btnChangeUi3 = uiFactory.NewJButton("UI 3", 700, 450, 100, 30, uiTheme, null, 15);
        btnChangeUi3.addActionListener(this);
        add(btnChangeUi3);
        //endregion

        //region JLabel
        lblSVoltageBat = uiFactory.NewJLabel("Napięcie akumulatora:", 250, 30, 200, 30, uiTheme);
        add(lblSVoltageBat);
        lblSVoltageGen = uiFactory.NewJLabel("Napięcie rowerka:", 250, 100, 300, 30, uiTheme);
        add(lblSVoltageGen);
        lblSCurrentCharge = uiFactory.NewJLabel("Prąd ładowania:", 250, 170, 200, 30, uiTheme);
        add(lblSCurrentCharge);
        lblVoltageBat = uiFactory.NewJLabel("0.00V", 450, 30, 100, 30, uiTheme);
        add(lblVoltageBat);
        lblVoltageGen = uiFactory.NewJLabel("0.00V", 450, 100, 100, 30, uiTheme);
        add(lblVoltageGen);
        lblCurrentCharge = uiFactory.NewJLabel("0.00A", 450, 170, 100, 30, uiTheme);
        add(lblCurrentCharge);
        lblMyCredit = uiFactory.NewJLabel("Rowerek-2021", 20, 450, 200, 30, uiTheme);
        add(lblMyCredit);
        lblNapięcieAkumulatoraMinimum = uiFactory.NewJLabel("8V", 10, 30, 200, 30, uiTheme);
        add(lblNapięcieAkumulatoraMinimum);
        lblNapięcieAkumulatoraMaximum = uiFactory.NewJLabel("20V", 765, 30, 200, 30, uiTheme);
        add(lblNapięcieAkumulatoraMaximum);
        lblNapięcieRowerkaMinimum = uiFactory.NewJLabel("0V", 10, 100, 200, 30, uiTheme);
        add(lblNapięcieRowerkaMinimum);
        lblNapięcieRowerkaMaksimum = uiFactory.NewJLabel("50V", 765, 100, 200, 30, uiTheme);
        add(lblNapięcieRowerkaMaksimum);
        lblPradLadowaniaMinimum = uiFactory.NewJLabel("0A", 10, 170, 200, 30, uiTheme);
        add(lblPradLadowaniaMinimum);
        lblPradLadowaniaMaksimum = uiFactory.NewJLabel("10A", 765, 170, 200, 30, uiTheme);
        add(lblPradLadowaniaMaksimum);
        lblErrorInfo = uiFactory.NewJLabel("ERROR: ---", 400, 420, 400, 30, uiTheme);
        add(lblErrorInfo);
        lblBaterryInfo = uiFactory.NewJLabel("AKUMULATOR: ---", 400, 390, 400, 30, uiTheme);
        add(lblBaterryInfo);
        //endregion

        //region ProgressBar
        pbarVoltageBat = uiFactory.NewJProgressBar(2000, 8, 8, 10, 1, 780, 30, uiTheme);
        add(pbarVoltageBat);
        pbarVoltageGen = uiFactory.NewJProgressBar(5000, 0, 0, 10, 70, 780, 30, uiTheme);
        add(pbarVoltageGen);
        pbarCurrentCharge = uiFactory.NewJProgressBar(1000, 0, 0, 10, 140, 780, 30, uiTheme);
        add(pbarCurrentCharge);

        //endregion
    }

    void RestartValuesAllControls() {
        pbarCurrentCharge.setValue(pbarCurrentCharge.getMinimum());
        pbarVoltageGen.setValue(pbarVoltageGen.getMinimum());
        pbarVoltageBat.setValue(pbarVoltageBat.getMinimum());
        lblCurrentCharge.setText("0A");
        lblVoltageBat.setText("0V");
        lblVoltageGen.setText("0V");
    }

    public void setVoltageBatIndicator(double val) {
        pbarVoltageBat.setValue((int) (val *100) );
        DecimalFormat df = new DecimalFormat("#.0");
        lblVoltageBat.setText(df.format(val) + "V");
    }

    public void setVoltageGenIndicator(double val) {
        pbarVoltageGen.setValue((int) (val *100));
        DecimalFormat df = new DecimalFormat("#.0");
        lblVoltageGen.setText(df.format(val) + "V");
    }

    public void setCurrentChargeIndicator(double val) {
        pbarCurrentCharge.setValue((int) (val *100));
        DecimalFormat df = new DecimalFormat("#.0");
        lblCurrentCharge.setText(df.format(val) + "A");
    }

    public void ChangeColorChargingButton(Color color) {
        btnChargingStateChange = uiFactory.ChangeButtonColor(btnChargingStateChange, color);
    }


    public void SetErrorCommunicate(String text)
    {
        lblErrorInfo.setText("ERROR: " + text);
    }

    public void SetBaterryInformationCommunicate(String text)
    {
        lblBaterryInfo.setText("AKUMULATOR: " + text);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnChargingStateChange) {
            if (!mainThread.chargingState) {
                if (mainThread.allowTurnOnCharging) {
                    mainThread.SetChargingState(true);
                }
            } else {
                mainThread.SetChargingState(false);
            }

        } else if (actionEvent.getSource() == btnChangeUi1) {
            try {
                ReloadAllControls("DarkUi");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (actionEvent.getSource() == btnChangeUi2) {
            try {
                ReloadAllControls("LightUi");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (actionEvent.getSource() == btnChangeUi3) {
            try {
                ReloadAllControls("ColorUi");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
