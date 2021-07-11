package Engine;

import GUI.MainWindow;
import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.gpio.extension.pca.PCA9685Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;

public class MainThread extends Thread {

    //region variables
    MainWindow mainWindowReference;
    VoltageU3Timer timer = null;
    public boolean chargingState = false;
    public boolean allowTurnOnCharging = false;
    public int chargingPWMValue = 1;

    private int increasePWMChargingIterator = -1;
    private double minimumVoltageToAllowCharging = 8;
    private double criticalLowBaterryVoltage = 8;
    private double criticalHighBaterryVoltage = 18;
    private double minimumVoltageToChargingWithUPS = 10.75;
    private double maximumVoltageToChargingWithUPS = 14.1;
    private double maxChargingCurrent = 1;
    private int maximumMOSLoad = 400;
    private int minimumMOSLoad = 100;
    private double maximumRechargingVoltage = 14.5;
    private int changePWMStep = 50;

    //endregion

    //region VARIABLES
    //region INA 3221 CONFIG variables
    public static final int INA3221_ADDR = 0x40;
    public static final byte READ_DATA_1_BYTE = (byte) 0x04;
    public static final int INA3221_REG_CONFIG = 0x00;
    public static final byte INA3221_CONFIG_ENABLE_CHAN1 = (byte) 0x4000;
    public static final byte INA3221_CONFIG_ENABLE_CHAN2 = (byte) 0x2000;
    public static final byte INA3221_CONFIG_ENABLE_CHAN3 = (byte) 0x1000;
    public static final byte INA3221_CONFIG_AVG2 = (byte) 0x0800;
    public static final byte INA3221_CONFIG_AVG1 = (byte) 0x0400;
    public static final byte INA3221_CONFIG_AVG0 = (byte) 0x0200;
    public static final byte INA3221_CONFIG_VBUS_CT2 = (byte) 0x0100;
    public static final byte INA3221_CONFIG_VBUS_CT1 = (byte) 0x0080;
    public static final byte INA3221_CONFIG_VBUS_CT0 = (byte) 0x0040;
    public static final byte INA3221_CONFIG_VSH_CT2 = (byte) 0x0020;
    public static final byte INA3221_CONFIG_VSH_CT1 = (byte) 0x0010;
    public static final byte INA3221_CONFIG_VSH_CT0 = (byte) 0x0008;
    public static final byte INA3221_CONFIG_MODE_2 = (byte) 0x0004;
    public static final byte INA3221_CONFIG_MODE_1 = (byte) 0x0002;
    public static final byte INA3221_CONFIG_MODE_0 = (byte) 0x0001;
    //endregion

    //region PCA9685 CONFIG variables
    public static final byte PCA9685_ADDR = (byte) 0x42;
    PCA9685GpioProvider PCA9685provider = null;
    GpioPinPwmOutput[] PCA9685Outputs = null;
    //endregion

    I2CBus i2c;
    I2CDevice INA3221Device;

//endregion


    //region constructor
    public MainThread(MainWindow mw) throws IOException, I2CFactory.UnsupportedBusNumberException, InterruptedException {
        this.mainWindowReference = mw;

        timer = new VoltageU3Timer(this);


        i2c = I2CFactory.getInstance(I2CBus.BUS_1);

        //region config INA3221
        INA3221Device = i2c.getDevice(INA3221_ADDR);
        INA3221Device.write(0x00, (byte) 0b01110001);
        INA3221Device.write((byte) 0b00100111);

        byte INA3221config = INA3221_CONFIG_ENABLE_CHAN1 |
                INA3221_CONFIG_ENABLE_CHAN2 |
                INA3221_CONFIG_ENABLE_CHAN3 |
                INA3221_CONFIG_AVG1 |
                INA3221_CONFIG_VBUS_CT2 |
                INA3221_CONFIG_VSH_CT2 |
                INA3221_CONFIG_MODE_2 |
                INA3221_CONFIG_MODE_1 |
                INA3221_CONFIG_MODE_0;
        INA3221Device.write(INA3221_REG_CONFIG, INA3221config);

        //endregion

        timer.start();
        //region configPCA9685
        BigDecimal frequency = new BigDecimal("1000");
        BigDecimal frequencyCorrectionFactor = new BigDecimal("1");
        PCA9685provider = new PCA9685GpioProvider(i2c, PCA9685_ADDR, frequency, frequencyCorrectionFactor);
        PCA9685Outputs = provisionPwmOutputs(PCA9685provider);
        PCA9685provider.reset();
        //endregion

    }
    //endregion

    private static int checkForOverflow(int position) {
        int result = position;
        if (position > PCA9685GpioProvider.PWM_STEPS - 1) {
            result = position - PCA9685GpioProvider.PWM_STEPS - 1;
        }
        return result;
    }


    private static GpioPinPwmOutput[] provisionPwmOutputs(final PCA9685GpioProvider gpioProvider) {
        GpioController gpio = GpioFactory.getInstance();
        GpioPinPwmOutput myOutputs[] = {
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_00, "Pulse 00"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_01, "Pulse 01"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_02, "Pulse 02"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_03, "Pulse 03"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_04, "Pulse 04"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_05, "Pulse 05"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_06, "Pulse 06"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_07, "Pulse 07"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_08, "Pulse 08"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_09, "Pulse 09"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_10, "Always ON"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_11, "Always OFF"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_12, "Servo pulse MIN"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_13, "Servo pulse NEUTRAL"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_14, "Servo pulse MAX"),
                gpio.provisionPwmOutputPin(gpioProvider, PCA9685Pin.PWM_15, "not used")};
        return myOutputs;
    }

    public double INA3221_ReadVoltage1() throws IOException {
        byte[] bytes = new byte[2];
        INA3221Device.read(0x02, bytes, 0, 2);
        int value = 0;
        value = Byte.toUnsignedInt(bytes[1]);
        value ^= (bytes[0] << 8);
        return (double) (value) * 0.001;
    }

    public double INA3221_ReadVoltage2() throws IOException {
        byte[] bytes = new byte[2];
        INA3221Device.read(0x04, bytes, 0, 2);
        int value = 0;
        value = Byte.toUnsignedInt(bytes[1]);
        value ^= (bytes[0] << 8);
        return (double) (value) * 0.007;
    }

    public double INA3221_ReadVoltage3() throws IOException {
        byte[] bytes = new byte[2];
        INA3221Device.read(0x06, bytes, 0, 2);
        int value = 0;
        value = Byte.toUnsignedInt(bytes[1]);
        value ^= (bytes[0] << 8);
        return (double) (value) * 0.001;
    }

    public double INA3221_ReadCurrent1() throws IOException {
        byte[] bytes = new byte[2];
        INA3221Device.read(0x01, bytes, 0, 2);
        int value = 0;
        value = Byte.toUnsignedInt(bytes[1]);
        value ^= (bytes[0] << 8);
        return (double) (value) * 0.0005;
    }

    private void ChangeChargingButtonState() throws IOException {
        CheckChargeOnAllow();
        if (!chargingState) {
            if (allowTurnOnCharging) {
                mainWindowReference.ChangeColorChargingButton(Color.RED);
            } else {
                mainWindowReference.ChangeColorChargingButton(Color.GRAY);
            }
        } else {
            mainWindowReference.ChangeColorChargingButton(Color.GREEN);
        }
    }

    private void CheckChargeOnAllow() throws IOException {
        if (CheckErrors()){
            allowTurnOnCharging = false;
        }
        else if (INA3221_ReadVoltage2() > minimumVoltageToAllowCharging || INA3221_ReadVoltage3() > maximumVoltageToChargingWithUPS) {
            allowTurnOnCharging = false;
        } else if(timer.CheckU2(false, minimumVoltageToAllowCharging)) {
            allowTurnOnCharging = true;
        }

    }


    private boolean CheckErrors() throws IOException {

        if (INA3221_ReadVoltage1() < 3 && INA3221_ReadVoltage3() > 3) {
            mainWindowReference.SetBaterryInformationCommunicate("---");
            mainWindowReference.SetErrorCommunicate("SPALONY BEZPIECZNIK 10A");
            return true;
        } else if (timer.CheckU3(false, criticalLowBaterryVoltage) || timer.CheckU3(true, criticalHighBaterryVoltage)) {
            mainWindowReference.SetBaterryInformationCommunicate("błąd");
            mainWindowReference.SetErrorCommunicate("USZKODZONY AKUMULATOR");
            return true;
        }
        mainWindowReference.SetErrorCommunicate("---");
        return false;
    }

    private void CheckExternalCharging() throws IOException {
        if (timer.CheckU3(false, minimumVoltageToAllowCharging)) {
            SetUPSRelay(true);
            mainWindowReference.SetBaterryInformationCommunicate("Ładowanie akumulatora z 230V");
        } else if (timer.CheckU3(true, maximumVoltageToChargingWithUPS)) {
            SetUPSRelay(false);
            mainWindowReference.SetBaterryInformationCommunicate("Akumulator naładowany");
        }
    }


    private void SetChargingPWM(int value) {
        if (value > 0 && value < 1000)
            PCA9685provider.setPwm(PCA9685Pin.PWM_00, value);
        else
            PCA9685provider.setPwm(PCA9685Pin.PWM_00, 1);
    }

    private void SetChargingRelay(boolean state) {
        if (state)
            PCA9685provider.setPwm(PCA9685Pin.PWM_01, 999);
        else
            PCA9685provider.setPwm(PCA9685Pin.PWM_01, 1);
    }

    private void SetUPSRelay(boolean state) {
        if (state)
            PCA9685provider.setPwm(PCA9685Pin.PWM_02, 999);
        else
            PCA9685provider.setPwm(PCA9685Pin.PWM_02, 1);
    }

    public void SetChargingState(boolean state) {
        if (state) {
            chargingState = true;
            increasePWMChargingIterator = 0;
        } else {
            chargingState = false;
            chargingPWMValue = 1;
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                if (CheckErrors()) {
                    mainWindowReference.setVoltageBatIndicator(INA3221_ReadVoltage1());
                    mainWindowReference.setVoltageGenIndicator(INA3221_ReadVoltage2());
                    mainWindowReference.setCurrentChargeIndicator(INA3221_ReadCurrent1());
                    allowTurnOnCharging = false;
                    chargingState = false;
                    chargingPWMValue = 1;
                    SetChargingRelay(chargingState);
                    SetChargingPWM(chargingPWMValue);
                    ChangeChargingButtonState();
                    Thread.sleep(1000);
                } else {
                    while (true) {
                        if (CheckErrors())
                            break;
                        CheckExternalCharging();
                        SetChargingRelay(chargingState);


                        if (timer.CheckU3(true, maximumRechargingVoltage)) {
                            if (INA3221_ReadCurrent1() > 0.5 && chargingPWMValue > minimumMOSLoad) {
                                chargingPWMValue -= changePWMStep;
                            } else if (chargingPWMValue < maximumMOSLoad) {
                                chargingPWMValue+= changePWMStep;
                            }
                        } else {
                            if (INA3221_ReadCurrent1() > maxChargingCurrent && chargingPWMValue > minimumMOSLoad) {
                                chargingPWMValue -= changePWMStep;
                            } else if (chargingPWMValue < maximumMOSLoad) {
                                chargingPWMValue+= changePWMStep;
                            }
                        }

                        if (increasePWMChargingIterator >= 0) {
                            increasePWMChargingIterator++;
                            if(chargingPWMValue < maximumMOSLoad) chargingPWMValue++;
                            if (increasePWMChargingIterator == maximumMOSLoad)
                                increasePWMChargingIterator = -1;
                        }

                        SetChargingPWM(chargingPWMValue);
                        //System.out.println(chargingPWMValue);

//                        PCA9685provider.setPwm(PCA9685Pin.PWM_00, 690);
//                        PCA9685provider.setPwm(PCA9685Pin.PWM_01, 800);


                        mainWindowReference.setVoltageBatIndicator(INA3221_ReadVoltage1());
                        mainWindowReference.setVoltageGenIndicator(INA3221_ReadVoltage2());
                        mainWindowReference.setCurrentChargeIndicator(INA3221_ReadCurrent1());

                        ChangeChargingButtonState();
                        Thread.sleep(1);

                    }
                }
            }
        } catch (InterruptedException | IOException e) {
            System.out.println("MainThred is interrupted");
            System.out.println(e);
        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
