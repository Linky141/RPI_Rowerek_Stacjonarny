package Engine;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

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
        int cycle=(int)(1000/frequency);
        low=(int)(cycle-((cycle*filling)/100));
        high=(int)((cycle*filling)/100);

        return true;
    }

    public boolean SetPWMFilling(int filling){
        if(filling > 100 || filling < 0) return false;
        this.filling = filling;
        int cycle=(int)(1000/frequency);
        low=(int)(cycle-((cycle*filling)/100));
        high=(int)((cycle*filling)/100);
        return true;
    }

    public boolean SetAll(int freq, int filling){
        if(filling > 100 || filling < 0 || freq < 0) return false;
        this.frequency = freq;
        this.filling = filling;
        int cycle=(int)(1000/frequency);
        low=(int)(cycle-((cycle*filling)/100));
        high=(int)((cycle*filling)/100);
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
                if(runningPWM){
                    Gpio00.high();
                Thread.sleep(high);
                    Gpio00.low();
                Thread.sleep(low);
                }
                else
                {
                    Gpio00.low();
                    Thread.sleep(low);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
