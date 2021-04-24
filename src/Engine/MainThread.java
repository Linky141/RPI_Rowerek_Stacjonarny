package Engine;

import GUI.MainWindow;
import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import sun.applet.Main;

import java.io.IOException;

public class MainThread extends Thread {

    //region variables
    MainWindow mainWindowReference;


    int load = 0;
    int maxLoad = 10;
    int minLoad = 0;
    //endregion

    //region VARIABLES
    //region Device config variables
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

//    BaterryAndGeneratorInfo batChargInfo;

    I2CBus i2c;
    I2CDevice device;


    private GpioController gpio = GpioFactory.getInstance();
    private GpioPinDigitalOutput Gpio00 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "Gpio00", PinState.HIGH);
    private GpioPinDigitalOutput Gpio01 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Gpio01", PinState.HIGH);
    private GpioPinDigitalOutput Gpio02 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Gpio02", PinState.HIGH);
    private GpioPinDigitalOutput Gpio03 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Gpio03", PinState.HIGH);

    BreakingPWMThread breakingPWMThread = new BreakingPWMThread(Gpio00);
    ChargingPWMThread chargingPWMThread = new ChargingPWMThread(Gpio01);
//endregion

    //region constructor
    public MainThread(MainWindow mw) throws IOException, I2CFactory.UnsupportedBusNumberException {
        this.mainWindowReference = mw;



        i2c = I2CFactory.getInstance(I2CBus.BUS_1);
        device = i2c.getDevice(INA3221_ADDR);


//        device.write(0x00, (byte) 0b01110001);
//        device.write((byte) 0b00100111);

        byte config = INA3221_CONFIG_ENABLE_CHAN1 |
                INA3221_CONFIG_ENABLE_CHAN2 |
                INA3221_CONFIG_ENABLE_CHAN3 |
                INA3221_CONFIG_AVG1 |
                INA3221_CONFIG_VBUS_CT2 |
                INA3221_CONFIG_VSH_CT2 |
                INA3221_CONFIG_MODE_2 |
                INA3221_CONFIG_MODE_1 |
                INA3221_CONFIG_MODE_0;
//        device.write(INA3221_REG_CONFIG, config);


        breakingPWMThread.start();
        breakingPWMThread.SetAll(400, 50);
        breakingPWMThread.SetState(true);


        chargingPWMThread.start();
        chargingPWMThread.SetAll(300, 50);
        chargingPWMThread.SetState(true);

        mainWindowReference.setLoadIndicator(minLoad);
    }
    //endregion


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

                    breakingPWMThread.SetPWMFilling(load * 10);
                    chargingPWMThread.SetPWMFilling(load * 10);

                Thread.sleep(load * 10);
//                Gpio00.low();
//                Gpio01.low();
                Gpio02.low();
                Gpio03.low();
                Thread.sleep(load * 10);
//                Gpio00.high();
//                Gpio01.high();
                Gpio02.high();
                Gpio03.high();
            }
        } catch (InterruptedException e) {
            System.out.println("MainThred is interrupted");
        }
    }

}
