package wash.control;

import actor.ActorThread;
import wash.io.WashingIO;
import wash.simulation.WashingSimulator;

public class Wash {

    public static void main(String[] args) throws InterruptedException {
        WashingSimulator sim = new WashingSimulator(Settings.SPEEDUP);
        

        WashingIO io = sim.startSimulation();

        TemperatureController temp = new TemperatureController(io);
        WaterController water = new WaterController(io);
        SpinController spin = new SpinController(io);

        ActorThread<WashingMessage> at = new ActorThread<>();

        WashingProgram1 w1;
        WashingProgram2 w2;
        WashingProgram3 w3;

        temp.start();
        water.start();
        spin.start();

        while (true) {

            int n = io.awaitButton();
            System.out.println("user selected program " + n);

            switch(n){

                case 0:

                at.interrupt();

                break;

                case 1:

                w1 = new WashingProgram1(io, temp, water, spin);
                w1.start();

                break;

                case 2:
                    
                w2 = new WashingProgram2(io, temp, water, spin);    
                w2.start();

                break;

                
                case 3:

                w3 = new WashingProgram3(io, temp, water, spin);
                w3.start();

                break;


            }
        }
    }
};
