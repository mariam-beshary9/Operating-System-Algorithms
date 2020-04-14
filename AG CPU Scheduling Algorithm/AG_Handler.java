/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cpu;

import java.awt.Color;
import java.awt.GridLayout;
import static java.lang.Math.ceil;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 *
 * @author Mariam Ahmed Amin
 */
public class AG_Handler {

    public int searchProcess(ArrayList<Process_AG> process, String n) {
        for (int i = 0; i < process.size(); i++) {
            if (process.get(i).name.equals(n)) {
                return i;
            }
        }
        return -1;
    }

    public double getAverageQuantum(ArrayList<Process_AG> process) {
        double aver = 0;
        for (int i = 0; i < process.size(); i++) {
            aver += process.get(i).quantum;
        }
        aver = aver / process.size();
        return aver;
    }

    public boolean finished(ArrayList<Process_AG> process) {
        for (int i = 0; i < process.size(); i++) {
            if (process.get(i).quantum != 0) {
                return false;
            }
        }
        return true;
    }

    public PriorityQueue<Process_AG> setProcess(int n, Scanner s) {

        PriorityQueue<Process_AG> notArrivedProcess = new PriorityQueue(n, new Process_AG.myComparator1());

        System.out.println("Enter the quantum time:");
        int q;
        q = s.nextInt();
        for (int i = 0; i < n; i++) {
            System.out.println("");
            System.out.println("For process: " + (i + 1));
            // System.out.println("");
            String na;
            System.out.print("Name:  ");
            na = s.next();
            int at;
            System.out.print("Arrival Time: ");
            at = s.nextInt();
            int bt;
            System.out.print("Burst Time:  ");
            bt = s.nextInt();
            int pr;
            System.out.print("Priority: ");
            pr = s.nextInt();

            System.out.print("Color:  ");
            String temp = s.next();
            Color c;
            try {
                Field field = Class.forName("java.awt.Color").getField(temp);
                c = (Color) field.get(null);
            } catch (Exception e) {
                c = null; // Not defined
            }
            Process_AG process = new Process_AG(na, at, bt, pr, q, c);
            notArrivedProcess.add(process);
        }
        return notArrivedProcess;
    }

    public void run() {

        System.out.print("Enter the number of process.");
        int n;
        Scanner s = new Scanner(System.in);
        n = s.nextInt();
        // we add process
        PriorityQueue<Process_AG> notArrivedProcess = setProcess(n, s);
        // we loop over time

        PriorityQueue<Process_AG> currentProcess = new PriorityQueue(n, new Process_AG.myComparator());

        PriorityQueue<Process_AG> deadProcess = new PriorityQueue(n, new Process_AG.myComparator1());
        ArrayList<Process_AG> process = new ArrayList<>();
        Process_AG excecutedProcess = null;
         JFrame frame = new JFrame("Process Execution");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    frame.setLayout(new GridLayout(2, 2));

    Border border = LineBorder.createGrayLineBorder();
        int time = 0;
        while ((currentProcess.size() != 0) || (notArrivedProcess.size() != 0) || (excecutedProcess != null)) {
            if ((finished(process)) && (process.size() == n)) {
                break;
            }
            // if there was a process made all his job
            if ((excecutedProcess != null) && (excecutedProcess.remainTime <= 0)) {
                excecutedProcess.waitingTime = time - excecutedProcess.arrivalTime - excecutedProcess.totalProcessingTime;
                excecutedProcess.turnAround = time - excecutedProcess.arrivalTime;
                deadProcess.add(excecutedProcess);
                int k = searchProcess(process, excecutedProcess.name);
                process.remove(k);
                excecutedProcess.quantum = 0;
                process.add(excecutedProcess);

                excecutedProcess = null;

            }
            // here we see if there is new process 
            Process_AG current = notArrivedProcess.peek();
            if (current != null) {
                if (current.arrivalTime <= time) {
                    notArrivedProcess.poll();
                    currentProcess.add(current);

                    process.add(current);
                }
            }

            if (excecutedProcess == null) {

                for (int i = 0; i < process.size(); i++) {
                    if ((currentProcess.contains(process.get(i))) && (process.get(i).quantum != 0)) {
                        excecutedProcess = process.get(i);
                        excecutedProcess.duration = 0;
                        currentProcess.remove(excecutedProcess);
                        break;
                    }

                }

            } else if ((excecutedProcess != null) && (ceil(excecutedProcess.quantum / 2.0) <= excecutedProcess.duration) && (excecutedProcess.duration < excecutedProcess.quantum)) {

                Process_AG swapProcess = currentProcess.peek();
                if (swapProcess != null) {

                    if (swapProcess.agFactor < excecutedProcess.agFactor) {
                        //    System.out.println("Process: " + swapProcess.name +" has more priority than "+excecutedProcess.name);
                        //   System.out.println("new process to exec is : "+swapProcess.name);
                        currentProcess.poll();

                        int k = searchProcess(process, excecutedProcess.name);
                        process.remove(k);
                        excecutedProcess.totalProcessingTime += excecutedProcess.duration;
                        excecutedProcess.quantum += excecutedProcess.quantum - excecutedProcess.duration;

                        process.add(excecutedProcess);
                        //  
                        currentProcess.add(excecutedProcess);

                        excecutedProcess = swapProcess;
                        excecutedProcess.duration = 0;

                    }
                }
            } else if ((excecutedProcess != null) && (excecutedProcess.duration == excecutedProcess.quantum) && (excecutedProcess.remainTime > 0)) {
                Process_AG swapProcess = process.get(0);
                for (int i = 0; i < process.size(); i++) {
                    if ((currentProcess.contains(process.get(i))) && (process.get(i).quantum != 0)) {
                        swapProcess = process.get(i);
                        swapProcess.duration = 0;
                        currentProcess.remove(swapProcess);
                        break;
                    }

                }
                if (swapProcess != null) {
                    //  System.out.println("The process "+excecutedProcess.name + " completed the quantum." );

                    currentProcess.remove(swapProcess);
                    int k = searchProcess(process, excecutedProcess.name);
                    process.remove(k);
                    excecutedProcess.totalProcessingTime += excecutedProcess.duration;
                    excecutedProcess.quantum += ceil(0.1 * getAverageQuantum(process));

                    process.add(excecutedProcess);
                    //  excecutedProcess.duration=0;

                    currentProcess.add(excecutedProcess);

                    excecutedProcess = swapProcess;
                    excecutedProcess.duration = 0;

                }
            }

            time++;
            for (int i = 0; i < process.size(); i++) {
                System.out.println("For process: " + process.get(i).name + ", Quantum = " + process.get(i).quantum);
                System.out.println(process.size());
            }
            System.out.println();
            if (excecutedProcess != null) {
                excecutedProcess.remainTime--;
                excecutedProcess.duration++;
                System.out.println("At time " + (time - 1) + " , Process " + excecutedProcess.name);
                JLabel label = new JLabel();
                    label.setText("  " +excecutedProcess.name + ", at time: " +time);
                 
                   label.setBackground(excecutedProcess.color);
                    
                    label.setOpaque(true);
                    label.setBorder(border);
                    frame.add(label);
                    
                    frame.setSize(1000, 200);
                    frame.setVisible(true);
            }
            else if (excecutedProcess == null)
            {
                JLabel label = new JLabel();
                    label.setText("  There is no process at time: " +time);
                 
                   label.setBackground(Color.WHITE);
                    
                    label.setOpaque(true);
                    label.setBorder(border);
                    frame.add(label);
                    
                    frame.setSize(1000, 200);
                    frame.setVisible(true);
            }
            

        }//
        double averageWaitingTime = 0;
        double averageTurnAroundTime = 0;
        for (int i = 0; i < process.size(); i++) {
            System.out.println("For process: " + process.get(i).name + ", waiting time = " + process.get(i).waitingTime + ", with turn around time = " + process.get(i).turnAround);
            averageWaitingTime += process.get(i).waitingTime;
            averageTurnAroundTime += process.get(i).turnAround;
        }
        averageWaitingTime = averageWaitingTime / process.size();
        averageTurnAroundTime = averageTurnAroundTime / process.size();
        System.out.println("The average waiting time = " + averageWaitingTime);
        System.out.println("The average turn around time = " + averageTurnAroundTime);
    }

}
