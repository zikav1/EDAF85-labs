package lift;

public class Main {

    public static void main(String[] args) throws InterruptedException {
 
        Monitor monitor = new Monitor();
        PassengerThread[] passengers = new PassengerThread[20];
        LiftThread lift = new LiftThread(monitor);
        
        for(int i = 0; i < 20; i++){
            passengers[i] = new PassengerThread(monitor);
        }
        
        
        for(PassengerThread passengerThread : passengers){
            passengerThread.start();
        }
           
        lift.start();
    }
}
