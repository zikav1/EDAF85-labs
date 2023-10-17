package clock.io;

import java.util.concurrent.Semaphore;

public class AlarmUpdate extends Thread {
    
    private ClockOutput out;
    private Semaphore s;
    private TimeData td;
    private int alarmTime;
    private boolean on;

    public AlarmUpdate(ClockOutput out, Semaphore s, TimeData td){
        this.out = out;
        this.s = s;
        this.td = td;
        this.alarmTime = 0;
        this.on = false;
    }

    @Override
    public void run(){

        while(true){
            try {

                s.acquire();

                long t = System.currentTimeMillis();
                long diff;

                if(on){
                    out.alarm();
                    alarmTime++;
                    if(alarmTime == 20 || !td.toggleAlarm()){
                        on = false;
                        alarmTime = 20;
                    }

                } else if(compareTime() && td.toggleAlarm()){
                    on = true;
                }
                
                s.release();

                t += 1000;
                diff = t - System.currentTimeMillis();
                if(diff > 0) sleep(diff);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("Something wrong in code");
            }
        }

    }


    private boolean compareTime(){

        int[] time = td.getTime();
        int[] alarmTime = td.getAlarmTime();
        
        return (time[0] == alarmTime[0]) && (time[1] == alarmTime[1]) && (time[2] == alarmTime[2]);
    }

}
