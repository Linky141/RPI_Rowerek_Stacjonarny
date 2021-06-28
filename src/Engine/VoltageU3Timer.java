package Engine;

import com.pi4j.io.gpio.*;
import com.pi4j.util.CommandArgumentParser;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class VoltageU3Timer extends Thread{

    private double U3voltageA = -1;
    private double U3voltageB = -1;
      private double U2voltageA = -1;
    private double U2voltageB = -1;
    private MainThread mainThread = null;

public VoltageU3Timer(MainThread mt)
{
    this.mainThread = mt;
}

public boolean U3CheckEqualsVoltages(){
    
    if(Math.abs(U3voltageA-U3voltageB) <0.1)
        return true;
    else
        return false;
}

public boolean U2CheckEqualsVoltages(){
    
    if(Math.abs(U2voltageA-U2voltageB) <0.1)
        return true;
    else
        return false;
}

public double GetU3(){
    return U3voltageB;
}

public double GetU2(){
    return U2voltageB;
}

public boolean CheckU3(boolean prefix, double value)
{
    if(prefix)
    {
        if(value < U3voltageA && value < U3voltageB)
            return true;
        else
            return false;
    }
    else
    {
        if(value > U3voltageA && value > U3voltageB)
            return true;
        else
            return false;        
    }
}


public boolean CheckU2(boolean prefix, double value)
{
    if(prefix)
    {
        if(value > U2voltageA && value > U2voltageB)
            return true;
        else
            return false;
    }
    else
    {
        if(value < U2voltageA && value < U2voltageB)
            return true;
        else
            return false;        
    }
}



    @Override
    public void run() {
        try{
            while(true) {                
                U3voltageB = U3voltageA;
                U3voltageA = mainThread.INA3221_ReadVoltage3();      
                
                U2voltageB = U2voltageA;
                U2voltageA = mainThread.INA3221_ReadVoltage2();                
              Thread.sleep(3000);
                System.out.println(U3voltageA + " " + U3voltageB);
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
