package bankeralgo;

import java.util.Scanner;

public class BankerAlgo {

    public static boolean exceed(int numResourcesTypes, int need[], int available[]) {
        for (int j = 0; j < numResourcesTypes; j++) {
            if (need[j] > available[j]) {
                return true;
            }
        }
        return false;
    }
    public static boolean finish (boolean [] state)
    {
        for(int i=0 ; i<state.length ; i++)
        {
            if (state[i]==false)
               return false;
        }
        return true;
    }
    public static void run (int processes , int numResourcesTypes ,  int need [][], int available [], int [][] allocation )
    {
        
        System.out.println();
        System.out.println("The Sequence is:");
        String sequence ="";
        boolean[] state = new boolean[processes];
        for (int i = 0; i < processes; i++) {
            state[i] = false;
        }
        int count = 0;
        while(!finish(state)){
        for (int i = 0; i < processes; i++) {
            if (state[i] == false) {
                if(exceed( numResourcesTypes,  need[i], available) )
                {   System.out.println("Deny the request for process P" + i );
                    if (count==processes)
                    {
                        System.out.println("There is no safe sequence");
                        return;
                    }
                    else { count++;
                        continue;}
                }
                    
                else {
                    count =0;
                System.out.println("Accept the request for process P" + i );
                sequence +="P" + i + " ";
                state[i] = true;
                for (int j = 0; j < numResourcesTypes; j++) {
                    available[j] += allocation[i][j];
                }

            }}
        }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the following data:");

        System.out.print("1)Number of processes:  ");
        int processes = sc.nextInt();

        System.out.print("2)Number of resource types:   ");
        int numResourcesTypes = sc.nextInt();

        int[] available = new int[numResourcesTypes]; //the available amount of each resource
        System.out.println("3)Available number of instances of resources of each type:   ");
        for (int i = 0; i < numResourcesTypes; i++) {
            System.out.print("For type " + i + " :  ");
            available[i] = sc.nextInt();
        }
        int[][] maximum = new int[processes][numResourcesTypes]; //the maximum demand of each process
        int[][] allocation = new int[processes][numResourcesTypes];  //the amount currently allocated to each process
        int[][] need = new int[processes][numResourcesTypes]; //the remaining needs of each process
        System.out.println("4)Maximum demand of instances of resources for each process. ");
        for (int i = 0; i < processes; i++) {
            System.out.println("For process " + i + " Enter num of maximum demand:");
            for (int j = 0; j < numResourcesTypes; j++) {
                System.out.print("For instance :" + j+" ");
                maximum[i][j] = sc.nextInt();
            }

        }
        System.out.println("5)Allocation of instances of resources for each process. ");
        for (int i = 0; i < processes; i++) {
            System.out.println("For process " + i + " Enter num of allcocation of:");
            for (int j = 0; j < numResourcesTypes; j++) {
                System.out.print("Resourse num :" + j+ "  ");
                allocation[i][j] = sc.nextInt();
                // calculate the need here to optimize the code
                need[i][j] = maximum[i][j] - allocation[i][j];
            }}

         int tempAvailable[] = available;

        run ( processes ,  numResourcesTypes ,   need , tempAvailable ,allocation );
        // to request 
        while (true){
            tempAvailable= available;
        System.out.println("To quit Enter 0 ."
                + "To request Enter 1");
        int choice = sc.nextInt();
        if (choice == 0)
        {
            return;
        }
        else if (choice==1){
            System.out.println("Enter your request. Enter process in the format p#");
            String req = sc.next();
            if (!req.equals("RQ"))
            { 
                System.out.println("Invalid syntax");
                continue;
            }
            String pro = sc.next();
            int proNum = Integer.parseInt(pro.substring(1, pro.length()));
            int [] request = new int [numResourcesTypes];
            for (int i=0 ; i<numResourcesTypes;i++)
            {
                request[i]=sc.nextInt();
            }
            if(exceed( numResourcesTypes, request,  need[proNum]))
            {
                System.out.println("The request exceeded the needed resources of this process");
                continue;
            }
            else{
                if (exceed( numResourcesTypes, request,  tempAvailable))
                {
                    System.out.println("The request exceeded the available resources");
                continue;
                }
                else {
                    for (int j=0 ; j<numResourcesTypes ; j++)
                    {
                        need[proNum][j]-=request[j];
                        available[j]-=request[j];
                        allocation[proNum][j]+=request[j];
                    }
                        run ( processes ,  numResourcesTypes ,   need , tempAvailable ,allocation );
                }
            }
            
        }
        else{
            System.out.println("Enter either 0 or 1");
        }
    }}

}
