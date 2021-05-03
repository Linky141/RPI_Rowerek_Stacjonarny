package Engine;

import GUI.MainWindow;
import com.pi4j.gpio.extension.pca.PCA9685GpioProvider;
import com.pi4j.gpio.extension.pca.PCA9685Pin;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinPwmOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;
import java.math.BigDecimal;

public class MainThread extends Thread {

    //region variables
    MainWindow mainWindowReference;


    int load = 0;
    int maxLoad = 10;
    int minLoad = 0;
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

        i2c = I2CFactory.getInstance(I2CBus.BUS_1);

        //region config INA3221
        //        INA3221Device = i2c.getDevice(INA3221_ADDR);
//        INA3221Device.write(0x00, (byte) 0b01110001);
//        INA3221Device.write((byte) 0b00100111);

        byte INA3221config = INA3221_CONFIG_ENABLE_CHAN1 |
                INA3221_CONFIG_ENABLE_CHAN2 |
                INA3221_CONFIG_ENABLE_CHAN3 |
                INA3221_CONFIG_AVG1 |
                INA3221_CONFIG_VBUS_CT2 |
                INA3221_CONFIG_VSH_CT2 |
                INA3221_CONFIG_MODE_2 |
                INA3221_CONFIG_MODE_1 |
                INA3221_CONFIG_MODE_0;
//        INA3221Device.write(INA3221_REG_CONFIG, INA3221config);

        //endregion

        //region configPCA9685
        BigDecimal frequency = new BigDecimal("1000");
        BigDecimal frequencyCorrectionFactor = new BigDecimal("1");
        PCA9685provider = new PCA9685GpioProvider(i2c, PCA9685_ADDR, frequency, frequencyCorrectionFactor);
        PCA9685Outputs = provisionPwmOutputs(PCA9685provider);
        PCA9685provider.reset();
        //endregion

        mainWindowReference.setLoadIndicator(minLoad);
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


    public void SetLoad(int val) {
        if (load >= minLoad && load <= maxLoad) {
            int lastLoad = load;
            this.load = val;
            mainWindowReference.setLoadIndicator(load);
        }
    }

    public void DecreaseLoad() {
        if (load > minLoad) {
            load--;
            mainWindowReference.setLoadIndicator(load);
        }
    }

    public void IncreaseLoad() {
        if (load < maxLoad) {
            load++;
            mainWindowReference.setLoadIndicator(load);
        }
    }


    boolean changeLoadIndicator = false;
    int val, lastVal;

    @Override
    public void run() {
        try {
            while (true) {

//                if(load == 0) {
//                    PCA9685provider.setPwm(PCA9685Pin.PWM_00, 800);
//                    PCA9685provider.setPwm(PCA9685Pin.PWM_01, 800);
//                }
//                else if(load==100) {
//                    PCA9685provider.setPwm(PCA9685Pin.PWM_00, 499);
//                    PCA9685provider.setPwm(PCA9685Pin.PWM_01, 499);
//                }
//                else {
//                    PCA9685provider.setPwm(PCA9685Pin.PWM_00, (400+load));
//                    PCA9685provider.setPwm(PCA9685Pin.PWM_01, (400+load));
//                }

                if(load == 0) {
                    PCA9685provider.setPwm(PCA9685Pin.PWM_00, 320);
                    PCA9685provider.setPwm(PCA9685Pin.PWM_01, 320);
                }
                else if(load==maxLoad) {
                    PCA9685provider.setPwm(PCA9685Pin.PWM_00, 340);
                    PCA9685provider.setPwm(PCA9685Pin.PWM_01, 340);
                }
                 else {
                    PCA9685provider.setPwm(PCA9685Pin.PWM_00, (int)(320+load*2));
                    PCA9685provider.setPwm(PCA9685Pin.PWM_01, (int)(320+load*2));
                }
                Thread.sleep(100);

            }
        } catch (InterruptedException e) {
            System.out.println("MainThred is interrupted");
        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
