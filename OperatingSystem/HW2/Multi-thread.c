/*
 * =====================================================================================
 *
 *       Filename:  Multi_thread.c
 *
 *       Description:  Multi-threads communication program
 *                     The  purpose of  this project  is  to  have  threads  be  responsible 
 *                     for servicing signals sent  by  other peer threads.  The threads
 *                     must  execute concurrently  and performance of  all thread  activities  is  to  be  
 *                     monitored and analyzed.
 *
 *       Author:  Yufeng Wang 
 *       Email: Y.F.Wang@temple.edu
 *       Due: Oct 07 2013
 * =====================================================================================
 */

#include <signal.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>
#include <sys/shm.h>
#include <sys/ipc.h>
#include "bitops.h"
#include <sys/time.h>
#include <time.h>
#include <pthread.h>



#define true 1
#define false !true
static int runTimeNum = 30;
static int runSignalNum = 100000;
static int runTime = false;
static int runSignal = false;
#define N 8
static  int ReportN = 10;
static int reportSatrt = false;
static int sig1Counter = 0; //used for trigerring report 
static int sig2Counter = 0; //used for trigerring report 
static int sigTotal = 0;//total signals caught by report
static double tTotal=0.0;
static struct timeval tt1,tt2,t11,t12,t21,t22;  //counters for report interval duration/sig1 duration/sig2 duration
static int sig1Num=0; //record occurances of sig1 during each reporting
static int sig2Num=0;
static int counterLock = false; //control the conflict on counters in reporting
/*Shared space as critical sentions*/
typedef volatile unsigned long lock;
typedef struct {
    lock l;
    int counter;
}Counter;
// Shared space as process control


Counter Scounter1, Scounter2, Rcounter1, Rcounter2; //4 shared counters as critical sections

// sigset_t waitset;
pthread_t tid[N];
/*functions used
1. The 'central control' function used to assign jobs to threads
2. The signal generation function 
3&4. The signal hadling function  and its sub function
5&6. The reporting function and its sub function*/

void* workDistribute(void *arg); 
void sigGen();
void sigHandle(int sigNum);
void sigFunc();
void sigReport();
void reportMonitor(int sig);
/* main process starts*/
int main(int argc, char *argv[])
{
    if(argc < 2){
      printf("incorrect input\n");
      printf("two options suppoted: t & n\n");
      exit(-1);
    }


    if(strcmp(argv[1], "t")==0){
        runTime = true;
        printf("Run for %d seconds\n",runTimeNum);
    }else if(strcmp(argv[1], "n")==0){
        printf("Run for %d signals\n",runSignalNum);
        runSignal = true;
    }else{
        printf("Input error!");
        exit(0);
    }


int err,i;

/*initialize cretical sections*/
memset(&Scounter1, 0, sizeof(Counter));
memset(&Scounter2, 0, sizeof(Counter));
memset(&Rcounter1, 0, sizeof(Counter));
memset(&Rcounter2, 0, sizeof(Counter));
printf("scounter1: %d\n, scounter2: %d\n", Scounter1.counter, Scounter2.counter);
printf("rcounter1: %d\n, rcounter2: %d\n", Rcounter1.counter, Rcounter2.counter);
// return;
/* Create 8 threads*/
for (i = 0; i < N; i++){
    err = pthread_create(&(tid[i]),NULL,&workDistribute,(void *) i );
    printf("Create the %d thread\n",i+1);
    if (err != 0)
      printf("n't create thread :[%s]", strerror(err));
    else{
      // printf("\n Thread created successfully\n");
    }
}





/*Wait for all the threads*/
for(int t = 0;t < N;t++){
  pthread_join(tid[t],NULL);
}



}


void* workDistribute(void *arg){ 
    // put threads into different categories
  long i = (long) arg; 
 // printf("This is the %ldth thread\n",i+1);
    if(i==0){
      //reporting
      printf("Reporting thread\n");  
      sigReport();

    }else if(i==1 || i==2){
      //handling
      printf("Handling grp1 thread\n");     
      sigHandle(1); 
    }else if(i==3 || i==4){
      //handling
      printf("Handling grp2 thread\n"); 
      sigHandle(2);
    }else if(i==5 || i==6 ||i==7){
      //sending
      printf("Sending thread\n"); 
      sigGen();
    }else{ 
      exit(-1);
    }
}


void spinLock(lock l){
    // printf("lock %x\n", l);
    // printf("lock %x\n", &l);
    while(test_and_set_bit(0, &l));  // TS. 0: unlocked 1: locked

}

void spinUnlock(lock l){
    clear_bit(0, &l);
}





void sigReport(){
  /* report statistics
  1. Report time interval between the 1st and last signal received for each signal type and time cost for this interval
      tt1&tt2&sysTime for this interval
      t11&t12 for sig1 
      t21&t22 for sig2
      Then calculate average time between receiptions of each signal type during this period (sig1Ave, sig2Ave)
  2. Report values of 4 counters
  3. Report number of each signal caught in this period*/
  int sig;

  printf("Signal reporting process. %d\n", (int)pthread_self());
while(1){
  signal(SIGUSR1, reportMonitor);
  signal(SIGUSR2, reportMonitor);

    if(reportSatrt == true){
      //system time and calculate ave time for each signal
        double sysTime = (double) ((tt2.tv_sec * 1000 + tt2.tv_usec/1000) - (tt1.tv_sec * 1000 + tt1.tv_usec/1000)); 
        double sig1Ave = (double) ((t12.tv_sec * 1000 + t12.tv_usec/1000) - (t11.tv_sec * 1000 + t11.tv_usec/1000))/sig1Num;
        double sig2Ave = (double) ((t22.tv_sec * 1000 + t22.tv_usec/1000) - (t11.tv_sec * 1000 + t11.tv_usec/1000))/sig2Num;
        tTotal+=sysTime;
        sigTotal+=10;
      //report counter values
        spinLock(Scounter1.l);
        int S1counter = Scounter1.counter;
        spinUnlock(Scounter1.l);

        spinLock(Scounter2.l);
        int S2counter = Scounter2.counter;
        spinUnlock(Scounter2.l);

        spinLock(Rcounter1.l);
        int R1counter = Rcounter1.counter;
        spinUnlock(Rcounter1.l);

        spinLock(Rcounter2.l);
        int R2counter = Rcounter2.counter;
        spinUnlock(Rcounter2.l);
      //report
        printf("---------------------------------------------------------------------------\n");
        printf("SIGUSR1 sent: %d, received %d\n", S1counter, R1counter);
        printf("SIGUSR2 sent: %d, received %d\n", S2counter, R2counter);
        printf("%d have been handled by report\n",sigTotal);
        printf("Time cost for this interval(ms): %.2f\n",sysTime);
        printf("Catched %d sig1,   %d sig2\n", sig1Num, sig2Num);
        printf("SIGUSR1 average interval(ms): %.2f, SIGUSR2 average interval(ms): %.2f\n", sig1Ave, sig2Ave);

          if(runTime){
            if(tTotal/1000>=runTimeNum){
              printf("The signals processed in %f is: %d\n", tTotal,        sigTotal);
              exit(0);
            }
          }else if(runSignal){
            if(sigTotal>=runSignalNum){
              printf("The time for processing %d signals is: %.2f\n",            sigTotal,tTotal);
              exit(0);
          }
          }

      reportSatrt = false;
    }
  }


}


/*monitor the signals received for reporting triggering*/
void reportMonitor(int sig)
{
   int sigFlag;
   static struct timeval tStart,t1Start,t2Start;
   switch(sig) {
    case SIGUSR1:
        sig1Counter++;
//        printf("catch sig1\n");
            if(sig1Counter==1){
              //the starting time of sig1 in this interval
              gettimeofday(&t1Start,NULL);
            }
        //temporarily record the ending time of sig1
        gettimeofday(&t12,NULL);
        sigFlag = 1;
      break;
    case SIGUSR2:
        sig2Counter++;
  //      printf("catch sig2\n");
            if(sig2Counter==1){
              //the starting time of sig2 in this interval
              gettimeofday(&t2Start,NULL);
            }
        //temporarily record the ending time of sig2
        gettimeofday(&t22,NULL);
        sigFlag = 2;
      break;
    default:
      break;
    }

    if((sig1Counter + sig2Counter) == 1) {      // Get start time
        gettimeofday(&tStart,NULL);
    }
    else if((sig1Counter + sig2Counter) >= ReportN && counterLock == false) {    // End time
        counterLock = true;
        gettimeofday(&tt2,NULL);
        tt1=tStart;
        t11 = t1Start;
        t21 = t2Start;
        //get end time of the last signal
        if(sigFlag == 1){
            gettimeofday(&t12,NULL);

        }else if(sigFlag == 2){
            gettimeofday(&t22,NULL);

        }
        sig1Num = sig1Counter;
        sig2Num = sig2Counter;
        /* Report */
        reportSatrt = true;
        sig1Counter = 0;   
        sig2Counter = 0;
        counterLock = false;
    }
}



void sigGen(){
/* pid[5-7] randomly generate one of the two signals and send it to hadler processors*/
  int i=0;
  int c=0;
  float d;
  printf("Process %d generating\n", (int)pthread_self());       
  while(1){
    int sigChoose = rand() % 100 ;
     // printf("%d\n",sigChoose);
    if(sigChoose % 2 == 1){
      c++;
      kill(0,SIGUSR1);
//      printf("sig1 sent out******************\n");
      //cretical section
      spinLock(Scounter1.l);
        Scounter1.counter++;
        // printf("Process %d is in\n", getpid());
        // printf("The sig1Counter is now: %d\n",Scounter1.counter);
        // printf("This is the %d operation\n",c);
      spinUnlock(Scounter1.l);
    }else{
      c++;
      kill(0,SIGUSR2);
 //     printf("sig2 sent out******************\n");
      //cretical section
      spinLock(Scounter2.l);
        Scounter2.counter++;
        // printf("Process %d is in\n", getpid());
        // printf("The sig2Counter is now: %d\n",Scounter2.counter);
        // printf("This is the %d operation\n",c);
      spinUnlock(Scounter2.l);
    }

    /* Sleep random time (0.01~0.1s)*/
    d = (float)(rand() % 90000 + 10000);
            // printf("Sleep for %f s.\n", d/1000000);
    //printf("sleep: %f\n", d);
    usleep(d);

    i++;
  }




}



void sigHandle(int sigNum){
  /* pid[1-4]receive signal, operations on counter*/
  printf("Process %d handling\n", (int)pthread_self());   
  int sig;
  while(1){
     // sigwait(&waitset,&sig);
    //   if(sigNum == 1 && sig == SIGUSR1){
    //     sigFunc(sig);
    //   }else if(sigNum == 2 && sig == SIGUSR2){
    //     sigFunc(sig);
    //   }
    if(sigNum==1){
      signal(SIGUSR1,sigFunc);
    }else if(sigNum==2){
      signal(SIGUSR2,sigFunc);
    }else{
      printf("handling error");
    }
  }

}

void sigFunc(int sig){
  switch(sig){
    case SIGUSR1:
   // printf("handling sig1\n");
    spinLock(Rcounter1.l);
    Rcounter1.counter++;
    // printf("Process %d is in\n", getpid());
    // printf("The sig1Counter is now: %d\n",Scounter1.counter);
    // printf("This is the %d operation\n",c);
    spinUnlock(Rcounter1.l);
     break;
    case SIGUSR2:
       // printf("handling sig2\n");
    spinLock(Rcounter2.l);
    Rcounter2.counter++;
    // printf("Process %d is in\n", getpid());
    // printf("The sig2Counter is now: %d\n",Scounter2.counter);
    // printf("This is the %d operation\n",c);
    spinUnlock(Rcounter2.l);
      break;
    default:
    printf("Handling error");
    exit(2);
      break;
  }

}


