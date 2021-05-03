package Engine;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;

import java.util.concurrent.TimeUnit;

public class BreakingPWMThread extends Thread{

    private int frequency=-1;
    private int filling=-1;
    private int low=-1;
    private int high=-1;
    private boolean runningPWM = false;
//    final GpioPinDigitalOutput Gpio00;

    GpioController gpio = GpioFactory.getInstance();

    Pin pin = CommandArgumentParser.getPin(
            RaspiPin.class,    // pin provider class to obtain pin instance from
            RaspiPin.GPIO_01  // default pin if no pin argument found
            );             // argument array to search in

    GpioPinPwmOutput pwm = gpio.provisionPwmOutputPin(pin);

    public BreakingPWMThread(){
//        Gpio00 = gpio;
        frequency=0;
        low=0;
        high=0;
        filling=0;

        com.pi4j.wiringpi.Gpio.pwmSetMode(com.pi4j.wiringpi.Gpio.PWM_MODE_MS);
        com.pi4j.wiringpi.Gpio.pwmSetRange(1000);
        com.pi4j.wiringpi.Gpio.pwmSetClock(500);
    }

    public boolean SetFrequency(int freq){
        return true;
    }

    public boolean SetPWMFilling(int fill){
        pwm.setPwm(fill);
        return true;
    }

    public boolean SetAll(int freq, int fill){
        return true;
    }

    public void SetState(boolean state)
    {
        this.runningPWM = state;
    }

    @Override
    public void run() {
        try{
            while(true) {
              Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
