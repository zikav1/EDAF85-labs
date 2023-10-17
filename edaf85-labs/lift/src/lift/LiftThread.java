package lift;

public class LiftThread extends Thread {
    
    private Monitor monitor;

    public LiftThread(Monitor monitor){
        this.monitor = monitor;
    }

    @Override
    public void run(){
        while(true){
            try {

                monitor.runLift();
                monitor.moveLift();
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
