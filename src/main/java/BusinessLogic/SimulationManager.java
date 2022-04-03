package BusinessLogic;
import Model.*;
import View.GraphicInterface;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable{

    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int numberOfServers;
    public int numberOfClients;

    private Scheduler scheduler;

    //private SimulationFrame frame;
    private List<Task> tasks;

    private SelectionPolicy selectionPolicy;

    public SimulationManager(int timeLimit,int numberOfServers) {
        this.timeLimit = timeLimit;

        tasks = new ArrayList<>();
        scheduler = new Scheduler(numberOfServers,10);
        // => create and start numberOfServer threads
        // => initialize selection strategy => createStrategy
        // initialize frame to display simulation
        // and store them to generateTasks

        Logger.deleteFileContents();
    }

    public void generateRandomTasks(int numberOfClients,int minArrivalTime,int maxArrivalTime,int minServiceTime,int maxServiceTime){
        Random rand = new Random();
        for(int i = 1;i <= numberOfClients;i++){
            Task t = new Task(i,
                    rand.nextInt(maxArrivalTime-minArrivalTime+1)+minArrivalTime,
                    rand.nextInt(maxServiceTime-minServiceTime+1)+minServiceTime);
            tasks.add(t);
        }
        Collections.sort(tasks);
    }

    public String toStringWaitingLine() {
        StringBuilder sb = new StringBuilder("");
        for (Task t: tasks) {
            sb.append(t.toString());
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public void run() {
        boolean addedFirstTask = false;
        int currentTime=0;
        while(currentTime<=timeLimit){
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.print("\nTime "+currentTime + ":");
            StringBuilder queueLog = new StringBuilder("");
            queueLog.append("\nTime ").append(currentTime).append(":");

            List<Task> toRemove = new ArrayList<>();

            for (Task t : tasks) {
                if(t.getArrivalTime() == currentTime){
                    addedFirstTask = true;
                    scheduler.dispatchTask(t);
                    toRemove.add(t);
                }else{
                    break;
                }
            }
            tasks.removeAll(toRemove);

            currentTime++;
            System.out.print(toStringWaitingLine());
            queueLog.append(toStringWaitingLine());

            System.out.println(scheduler.toStringServers());
            queueLog.append(scheduler.toStringServers());

            Logger.writeLine(queueLog.toString());
            GraphicInterface.getInstance().writeTextArea(queueLog.toString());
            if((scheduler.serversEmpty() || tasks.size() == 0) && addedFirstTask){
                break;
            }
        }
    }
}
