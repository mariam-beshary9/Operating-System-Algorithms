package schedulingalgorithms;


import static java.lang.Math.abs;
import java.util.ArrayList;

public class FCFS {
    
    int requestsQueue [];
    int initialPosition ;
    
    FCFS ( ArrayList <Integer> queueList, int h )
    {
        initialPosition = h;
        requestsQueue = new int [queueList.size()];
        for (int i=0 ; i<queueList.size() ; i++)
        {
            requestsQueue[i]=queueList.get(i);
        }
    }
    int run ()
    {
        int sum =  0 ;
        System.out.println("The head is at cylinder :  "+ initialPosition);
        int temp = initialPosition;
        for (int i=0 ; i<requestsQueue.length ; i++)
        {   
            System.out.println("The head moved from "+ temp + " to "+requestsQueue[i]);
            sum+=abs(requestsQueue[i]-temp);
            temp=requestsQueue[i];
        }
        return sum;
    }
}
