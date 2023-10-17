package clock.io;

public class TimeData {

    private int hours, minutes, seconds;
    private int alarmHours, alarmMinutes, alarmSeconds;
    private boolean signal;

    public TimeData(){
        hours = minutes = seconds = 0;
        alarmHours = alarmMinutes = alarmSeconds = 0;
        signal = false;
    }


    
    public void setTime(int hours, int minutes, int seconds){
        
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }
    
    public int[] getTime(){
        
        int[] time = new int[3];
        time[0] = hours;
        time[1] = minutes;
        time[2] = seconds;

        return time;
    }

    public void setAlarmTime(int hours, int minutes, int seconds) {
        
        this.alarmHours = hours;
        this.alarmMinutes = minutes;
        this.alarmSeconds = seconds;        
    }

    public int[] getAlarmTime(){
        
        int[] alarmTime = new int[3];
        alarmTime[0] = alarmHours;
        alarmTime[1] = alarmMinutes;
        alarmTime[2] = alarmSeconds;

        return alarmTime;
    }

    public boolean toggleAlarm(){
        signal = true;
        return signal;
    }
    
}
