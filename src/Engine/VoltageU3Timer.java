package Engine;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VoltageU3Timer extends Thread{

    private double voltageA = -1;
    private double voltageB = -1;
    private MainThread mainThread = null;

public VoltageU3Timer(MainThread mt)
{
    this.mainThread = mt;
}

public boolean CheckEqualsVoltages(){
    
    if(Math.abs(voltageA-voltageB) <0.5)
        return true;
    else
        return false;
}



    @Override
    public void run() {
        try{
            while(true) {                
                voltageB = voltageA;
                voltageA = mainThread.INA3221_ReadVoltage3();                
              Thread.sleep(3000);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
