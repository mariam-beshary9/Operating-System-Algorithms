# AG-CPU-schedueling-Algorithm
a. The Round Robin (RR) CPU scheduling algorithm is a fair scheduling algorithm that gives equal time
    quantum to all processes So All processesare provided a static time to execute called quantum.
    
b. A new factor is suggested to attach with each submitted process in our AG scheduling algorithm. This factor sums the effects of all three basic factors
    (priority, arrival time and burst time). The equation summarizes this relation
    is:
        AG-Factor = Priority + Arrival Time + Burst Time
    
 c. Once a process is executed for given time period, it’s called Non-preemptive AG till the finishing of (ceil (50%)) of its Quantum time, after that it’s converted to preemptive AG
    
 d. We have 3 scenarios of the running process
 
   i. The running process used all its quantum time and it still have job to
              do (add this process to the end of the queue, then increases its
              Quantum time by (ceil(10% of the (mean of Quantum))) ).
              
   ii. The running process didn’t use all its quantum time based on another
              process converted from ready to running (add this process to the end
              of the queue, and then increase its Quantum time by the remaining
              unused Quantum time of this process).
              
   iii. The running process finished its job (set its quantum time to zero
              and remove it from ready queue and add it to the die list).
