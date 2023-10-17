package clock.io;
import java.util.concurrent.Semaphore;

public class TimeUpdate extends Thread {

    private ClockOutput out;
    private Semaphore s;
    private TimeData td;


    public TimeUpdate(ClockOutput out, Semaphore s, TimeData td){
        this.s = s;
        this.out = out;
        this.td = td;   

    }

    @Override
    public void run(){
        
        while(true){
            
            long t = System.currentTimeMillis();
            long diff;
            
            try {

                s.acquire();


                int[] time = td.getTime();
                int hour = time[0];
                int minutes = time[1];
                int seconds = time[2];
                
                seconds++;
                if(seconds == 60){
                    seconds = 0;
                    minutes++;
                }

                if(minutes == 60){
                    minutes = 0;
                    hour++;
                }

                if(hour == 24){
                    hour = minutes = seconds = 0;
                }

                td.setTime(hour, minutes, seconds);
                out.displayTime(hour, minutes, seconds);
                s.release();

                t += 1000;
                diff = t - System.currentTimeMillis();

                if(diff > 0) Thread.sleep(diff);

            } catch (InterruptedException e) {
                System.out.println("Something wrong in code");
                e.printStackTrace();
            }
        }
    }
    

}
