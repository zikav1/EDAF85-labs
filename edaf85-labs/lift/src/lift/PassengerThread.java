package lift;

public class PassengerThread extends Thread {
    
    private Monitor monitor;

    public PassengerThread(Monitor monitor){
        this.monitor = monitor;
    }
    
    @Override
    public void run() {
        while(true){
            try {
                Passenger pass = monitor.startPassenger();
                monitor.runPassenger(pass);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
}

}
