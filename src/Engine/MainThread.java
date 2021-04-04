package Engine;

import GUI.MainWindow;
import sun.applet.Main;

public class MainThread extends Thread{

    //region variables
    MainWindow mainWindowReference;


    int load = 0;
    int maxLoad = 10;
    int minLoad = 0;
    //endregion

    //region constructor
    public MainThread(MainWindow mw)
    {
        this.mainWindowReference = mw;

        mainWindowReference.setLoadIndicator(minLoad);
    }
    //endregion

  

    public void SetLoad(int val){
        if(load >= minLoad && load <=maxLoad) {
            int lastLoad = load;
            this.load = val;
            mainWindowReference.setLoadIndicator(load);
        }
    }
    public void DecreaseLoad(){
        if(load>minLoad){
            load--;
            mainWindowReference.setLoadIndicator(load);
        }
    }
    public void IncreaseLoad(){
        if(load<maxLoad) {
            load++;
            mainWindowReference.setLoadIndicator(load);
        }
    }


boolean changeLoadIndicator = false;
    int val, lastVal;

    @Override
    public void run(){
        while(true){
try{
    Thread.sleep(10);

} catch (InterruptedException e) {
    System.out.println("MainThred is interrupted");;
}
        }
    }

}
