package Engine;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import java.util.concurrent.TimeUnit;

public class ChargingPWMThread extends Thread{

    private int frequency=-1;
    private int filling=-1;
    private int low=-1;
    private int high=-1;
    private boolean runningPWM = false;
//    final GpioPinDigitalOutput Gpio01;

    public ChargingPWMThread(){
//        Gpio01 = gpio;
        frequency=0;
        low=0;
        high=0;
        filling=0;
    }

    public boolean SetFrequency(int freq){
      return true;
    }

    public boolean SetPWMFilling(int fill){
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
