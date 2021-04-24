package Engine;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import java.util.concurrent.TimeUnit;

public class BreakingPWMThread extends Thread{

    private int frequency=-1;
    private int filling=-1;
    private int low=-1;
    private int high=-1;
    private boolean runningPWM = false;
    final GpioPinDigitalOutput Gpio00;

    public BreakingPWMThread(GpioPinDigitalOutput gpio){
        Gpio00 = gpio;
        frequency=0;
        low=0;
        high=0;
        filling=0;
    }

    public boolean SetFrequency(int freq){
        if(freq<0) return false;
        this.frequency = freq;
        int cycle=(int)(1000000000/frequency);
        low=(int)(cycle-((cycle*filling)/100));
        high=(int)((cycle*filling)/100);

        return true;
    }

    public boolean SetPWMFilling(int fill){
        if(filling > 100 || filling < 0) return false;
        this.filling = fill;
        int cycle=(int)(1000000000/frequency);
        low=(int)(cycle-((cycle*filling)/100));
        high=(int)((cycle*filling)/100);
        return true;
    }

    public boolean SetAll(int freq, int fill){
        if(filling > 100 || filling < 0 || freq < 0) return false;
        boolean succesFreq, succesFill;
        succesFreq = SetFrequency(freq);
        succesFill = SetPWMFilling(fill);
        if(succesFill && succesFreq) return true;
        else return false;
    }

    public void SetState(boolean state)
    {
        this.runningPWM = state;
    }

    @Override
    public void run() {
        try{
            while(true) {
                if(runningPWM){
                    Gpio00.high();
//                Thread.sleep(0,high);
                    TimeUnit.NANOSECONDS.sleep(high);
                    Gpio00.low();
//                Thread.sleep(0,low);
                    TimeUnit.NANOSECONDS.sleep(low);
                }
                else
                {
                    Gpio00.low();
//                    Thread.sleep(0,low);
                    TimeUnit.NANOSECONDS.sleep(low);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
