/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingalgorithms;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Guest
 */
public class SchedulingAlgorithms {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
       // System.out.println("Enter the initial position of the head.");
        // 53
      //  int init = sc.nextInt();
       // System.out.println();
        System.out.print("Enter the queue:  ");
        String line = sc.nextLine();
        //98 183 37 122 14 124 65 67
        String[] parts = line.split(" ");
        ArrayList <Integer> requests = new ArrayList <>();
        for (int i=0 ; i<parts.length ; i++)
        {
            requests.add(Integer.parseInt(parts[i]));
        }
         System.out.print("Enter the initial position of the head:  ");
        // 53
       int init = sc.nextInt();
        
        // Now we have taken the inputs, Lets run the algorithms 
        FCFS fcfs = new FCFS(requests,init);
        System.out.println();
        System.out.println("Running First Come First Served algorithms.");
        int n1 = fcfs.run();
        
        System.out.println();
        
        OptimizedAlgo OA = new OptimizedAlgo (requests);
        System.out.println("Running The new optimized algorithm.");
        int n2 = OA.run();
        
        System.out.println();
        if (n1>n2)
        {
         System.out.println("The new opimized algorithm is better that FCFS because it has less number of movement");
         System.out.println("Optimized has "+ n2+ " movements , FCFS has "+n1+" movements.");
        }
        else {
         System.out.println("The new opimized algorithm is worse that FCFS because it has more number of movement");
         System.out.println("Optimized has "+ n2+ " movements , FCFS has "+n1+" movements.");
        }
        
        
    }
    
}
