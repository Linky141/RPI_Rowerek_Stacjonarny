package Engine;

import GUI.MainWindow;

public class Test extends Thread {
    MainWindow mw = null;

    public Test(MainWindow a) {
        mw = a;
    }

    @Override
    public void run() {
        try {
            while (true) {
                for (int clk = 0; clk < 100; clk++) {
                    mw.setVoltageBatIndicator(clk);
                    mw.setCurrentChargeIndicator(clk);
                    mw.setVoltageGenIndicator(clk);
                    Thread.sleep(25);

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
