import java.util.concurrent.Semaphore;

import clock.AlarmClockEmulator;
import clock.io.AlarmUpdate;
import clock.io.Choice;
import clock.io.ClockInput;
import clock.io.ClockInput.UserInput;
import clock.io.ClockOutput;
import clock.io.InputUpdate;
import clock.io.TimeData;
import clock.io.TimeUpdate;


public class ClockMain {
    public static void main(String[] args) throws InterruptedException {
        

        AlarmClockEmulator emulator = new AlarmClockEmulator();
        ClockInput  in  = emulator.getInput();
        ClockOutput out = emulator.getOutput();
        
        Semaphore s = new Semaphore(1);
        TimeData td = new TimeData();
        td.setTime(0, 0, 0);

        TimeUpdate t = new TimeUpdate(out, s, td);
        InputUpdate i = new InputUpdate(in, in.getSemaphore(), s, td, out);
        AlarmUpdate a = new AlarmUpdate(out, s, td);

        t.start();
        i.start();
        a.start();        
    }
}
