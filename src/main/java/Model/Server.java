package Model;

import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private int ID;
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public Server(int ID){
        this.ID=ID;
        tasks = new LinkedBlockingDeque<>();
        waitingPeriod = new AtomicInteger(0);
    }

    public void addTask(Task newTask){
        //add task to queue
        tasks.add(newTask);
        //increment waitingPeriod
        waitingPeriod.set(waitingPeriod.get()+newTask.getServiceTime());
    }

    @Override
    public void run(){
        while(true){
            Task t;
            try {
                t = tasks.peek();
                if(t==null)
                    continue;
                int currentST = t.getServiceTime();
                for(int i = 0;i < currentST;i++) {
                    Thread.sleep(1000);
                    t.decrementServTime();
                    waitingPeriod.set(waitingPeriod.get() - 1);
                }
                tasks.remove(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //take next task from queue
            //stop the thread for a period of time with the task's processing time
            //decrement waiting period
        }
    }

    public Task[] getTasks(){
        Task[] tasks = new Task[10];
        return tasks;
    }

    @Override
    public String toString(){
        StringBuilder aux = new StringBuilder("Queue " + ID + ":");
        if(tasks.size() == 0)
            aux.append("closed");
        for (Task t: tasks) {
            aux.append(t.toString());
        }
        aux.append("\n");
        return aux.toString();
    }

    public int getClients(){
        return tasks.size();
    }

    public int getID(){
        return ID;
    }
}
