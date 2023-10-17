package lift;

public class Monitor {
    
    private boolean moving;
    private boolean movingUp;
    private int[] toEnter;
    private int[] toExit;
    private int currentFloor;
    private int passengersInLift;
    protected int MAX_PASSENGERS = 4, NBR_FLOORS = 7;
    LiftView liftView;

    public Monitor() {
        
        liftView = new LiftView(NBR_FLOORS, MAX_PASSENGERS);
        toEnter = new int[NBR_FLOORS]; 
        toExit = new int[NBR_FLOORS];

        for(int i = 0; i < NBR_FLOORS; i++){
            toEnter[i] = 0;
            toExit[i] = 0;
        }

        //current floor of the lift used in runLift()
        currentFloor = 0;

        //passengers currently in the lift.
        passengersInLift = 0;
        
        //is the lift moving or not
        moving = true;

        //only used for the logic behind moving the lift
        movingUp = true;
    }

    public Passenger startPassenger(){
        Passenger pass = liftView.createPassenger();
        pass.begin();
        return pass;
    }

    public synchronized void runPassenger(Passenger pass) throws InterruptedException{
        
        //get start floor and destination floor for the current passenger 
        //that I return from StratPassenger().
        int fromFloor = pass.getStartFloor();
        int toFloor = pass.getDestinationFloor();
        
        
        toEnter[fromFloor]++;
        liftView.showDebugInfo(toEnter, toExit);
        
        notifyAll();

        //passenger should wait for the lift to arrive 
        //or wait if passenger in lift is greater than max passengers or the lift is moving
        while((currentFloor != fromFloor) || (passengersInLift >= MAX_PASSENGERS) || moving){
            wait();
        }
    

        //passenger enters the lift, we increment passenger in lift by one.
        enterLift(pass);
        passengersInLift++;
        toEnter[fromFloor]--;
        toExit[toFloor]++;
        liftView.showDebugInfo(toEnter, toExit);
        
        notifyAll();

        //passenger waits until to floor is reached or the lift is moving
        while((currentFloor != toFloor) || moving){
            wait();
        }
        
        exitLift(pass);
        passengersInLift--;
        toExit[toFloor]--;
        liftView.showDebugInfo(toEnter, toExit);

        notifyAll();
        
        pass.end();
    }
    

    public void enterLift(Passenger pass){
        pass.enterLift();
    }

    public void exitLift(Passenger pass){
        pass.exitLift();
    }


    
    public synchronized void runLift() throws InterruptedException {
        if((toEnter[currentFloor] > 0 && passengersInLift < MAX_PASSENGERS) || toExit[currentFloor] > 0){
        //open doors 
        liftView.openDoors(currentFloor);
        
        //lift is not moving
        moving = false;
        
        //notify
        notifyAll();
        
        //should wait at the floor if there are passengers wating to enter at the current floor and passengers in the lift is
        //less than max passengers or there is someone who wants to exit the lift.
        while((toEnter[currentFloor] > 0 && passengersInLift < MAX_PASSENGERS) || toExit[currentFloor] > 0){
            wait();
        }

        //closing the door and now the lift is moving so we set the moving to "true"
        liftView.closeDoors();
    }
        moving = true;  

        notifyAll();
    }


    //functionality for moving the lift, attribute movingUp only for this method, 
    //which is later being called instide of run() in lift.   
    public void moveLift(){
        
        if(movingUp){
    
            int nextFloor = currentFloor + 1;
            liftView.moveLift(currentFloor, nextFloor);
            currentFloor++;

            if(currentFloor == NBR_FLOORS - 1){
                movingUp = !movingUp;

            }

        } else {
            int nextFloor = currentFloor - 1;
            liftView.moveLift(currentFloor, nextFloor);
            currentFloor--;

            if(currentFloor == 0){
                movingUp = !movingUp;
            }
            
        }
    }
    
}
