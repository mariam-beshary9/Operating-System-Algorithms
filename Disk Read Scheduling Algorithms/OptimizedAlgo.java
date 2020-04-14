package schedulingalgorithms;


import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Guest
 */
public class OptimizedAlgo {
    PriorityQueue<Integer> requestsQueue ;
    
    OptimizedAlgo(ArrayList <Integer> queueList)
    {
       requestsQueue = new PriorityQueue<>(); 
       
       for (int i=0 ; i<queueList.size() ; i++)
       {
           requestsQueue.add(queueList.get(i));
       }
    }
    int run(){
        int sum =  0 ;
        
        System.out.println("The head is at cylinder :  "+ 0);
        int previousPosition = 0;
        while(requestsQueue.size()>0 )
        {   int position = requestsQueue.poll();
            System.out.println("The head moved from "+ previousPosition + " to "+position);
            sum+=(position-previousPosition);
            previousPosition=position;
        }
        return sum;
    }
    
}
