/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu;

import java.awt.Color;
import java.util.Comparator;
import static javafx.scene.paint.Color.color;

/**
 *
 * @author Mariam Ahmed Amin
 */
public class Process_AG {
     

    public String name;
    public int arrivalTime;
    public int burstTime;
    public int priority;
    public int quantum;
    public int agFactor;
    public int remainTime; // for all time
    public int duration; // for one exec
    public int totalProcessingTime;
    public int waitingTime ;
    public int turnAround ;
    public Color color;

    public Process_AG(String n, int at, int bt, int p, int q , Color c) {
        name = n;
        arrivalTime = at;
        burstTime = bt;
        priority = p;
        quantum = q;
        agFactor = arrivalTime + priority + burstTime;
        remainTime = burstTime;
        totalProcessingTime = 0;
        waitingTime =0;
        turnAround = 0;
        color = c;

    }

    // compare by ag factor
   static class myComparator implements Comparator<Process_AG> {

        @Override
        public int compare(Process_AG x, Process_AG y) {
            return x.agFactor - y.agFactor;
        }
    }

    // compare by arrival time
    static class myComparator1 implements Comparator<Process_AG> {

        @Override
        public int compare(Process_AG x, Process_AG y) {
            return x.arrivalTime - y.arrivalTime;
        }

    }
    
}
