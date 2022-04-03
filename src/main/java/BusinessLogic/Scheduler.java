package BusinessLogic;
import Model.*;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers,int maxTasksPerServer){
        servers = new ArrayList<>();
        changeStrategy(SelectionPolicy.SHORTEST_TIME);
        for(int i = 1;i <= maxNoServers;i++){
            Server s = new Server(i);
            servers.add(s);
            Thread t = new Thread(s);
            t.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ShortestQueueStrategy();
        }else if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new TimeStrategy();
        }
    }
    public void dispatchTask(Task task){
        strategy.addTask(servers,task);
    }

    public String toStringServers(){
        StringBuilder sb = new StringBuilder("");
        for (Server s : servers) {
            sb.append(s.toString());
        }
        return sb.toString();
    }

    public boolean serversEmpty(){
        for (Server s: servers) {
            if(s.getClients() != 0){
                return false;
            }
        }
        return true;
    }

    public List<Server> getServers(){
        return servers;
    }
}
