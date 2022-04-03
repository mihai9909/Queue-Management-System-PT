package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class TimeStrategy implements Strategy{

    @Override
    public void addTask(List<Server> servers, Task task) {
        int smallest = Integer.MAX_VALUE;
        Server min = null;

        for (Server s: servers) {       //find server with smallest waiting period
            if(s.getWaitingPeriod() < smallest){
                min = s;
                smallest = s.getWaitingPeriod();
            }
        }

        if(min != null) {
            min.addTask(task);
        }
    }
}
