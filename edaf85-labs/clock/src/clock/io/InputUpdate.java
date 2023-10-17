package clock.io;

import java.util.concurrent.Semaphore;

import clock.io.ClockInput.UserInput;

public class InputUpdate extends Thread {
    
    private ClockInput in;
    private Semaphore s1;
    private Semaphore s2;
    private TimeData td;
    private ClockOutput out;


    public InputUpdate(ClockInput in, Semaphore s1, Semaphore s2 ,TimeData td, ClockOutput out){
        this.in = in;
        this.s1 = s1;
        this.s2 = s2;
        this.td = td;
        this.out = out;
    }

    @Override
    public void run(){

        while(true){
        try {
            
            s1.acquire();

            UserInput userInput = in.getUserInput();
            Choice c = userInput.choice();
            
            s2.acquire();
            switch(c){
                case SET_TIME:
                
                int h = userInput.hours();
                int m = userInput.minutes();
                int s = userInput.seconds();
                
                td.setTime(h, m, s);
                out.displayTime(h, m, s);
                

                break;
                
                case SET_ALARM:

                int h1 = userInput.hours();
                int m1 = userInput.minutes();
                int s1 = userInput.seconds();
                td.setAlarmTime(h1, m1, s1);
                
                break;

                case TOGGLE_ALARM:
                
                out.setAlarmIndicator(td.toggleAlarm());
                System.out.println("Alarm is " + td.toggleAlarm());
                
                break;
            }
            
            s2.release();
        } 
        
        catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Something wrong in code");
        }
    }    
}

}
