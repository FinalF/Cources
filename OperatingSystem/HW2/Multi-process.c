/*
 * =====================================================================================
 *
 *       Filename:  Multi-process.c
 *
 *       Description:  Multi-processes communication program
 *                     The  purpose of  this project  is  to  have  processes  be  responsible 
 *                     for servicing signals sent  by  other peer  processes .  The processes 
 *                     must  execute concurrently  and performance of  all process  activities  is  to  be  
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




#define true 1
#define false !true
static int runTimeNum = 30;
static int runSignalNum = 100000;
static int runTime = false;
static int runSignal = false;
static  int N  = 8;
static  int ReportN = 10;
static int reportSatrt = false;
static int sig1Counter = 0; //used for trigerring report 
static int sig2Counter = 0; //used for trigerring report 
static int sigTotal = 0; //total signals caught by report
static double tTotal=0.0;
static struct timeval tt1,tt2,t11,t12,t21,t22;  //counters for report interval duration/sig1 duration/sig2 duration
static int sig1Num=0; //record occurances of sig1 during each reporting
static int sig2Num=0;
static int counterLock = false;//control the conflict on counters in reporting



/*Shared space as critical sentions*/
typedef volatile unsigned long lock;
typedef struct {
    lock l;
    int counter;
}Counter;
/*Shared space as process control*/
typedef struct {
  int p[8];
}PID;

Counter *Scounter1, *Scounter2, *Rcounter1, *Rcounter2; //4 shared counters as critical sections
PID *pids;
#define PID_KEY      000
#define SEND1_KEY    111
#define SEND2_KEY    222
#define RECEIVE1_KEY 333
#define RECEIVE2_KEY 444

/*functions used
1. Shared space for counters
2. Shared space for PIDs of all processes
3. The signal generation function 
4&5. The signal hadling function and its sub function
6&7. The reporting function and its sub function*/
Counter* sharedMem(key_t key);
PID* sharedPID(key_t key);
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

    // int i = 0;
    // for (i = 0; i < argc; i++) {
    //     printf("argv[%d] = %s\n", i, argv[i]);
    // }

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


int status;
/*parent ignore signal 1 and 2*/
(void) signal(SIGUSR1, SIG_IGN);
(void) signal(SIGUSR2, SIG_IGN);
/*initialize cretical sections*/
pids = sharedPID(PID_KEY);
Scounter1 = sharedMem(SEND1_KEY);
Scounter2 = sharedMem(SEND2_KEY);
Rcounter1 = sharedMem(RECEIVE1_KEY);
Rcounter2 = sharedMem(RECEIVE2_KEY);
memset(pids, 0, N*sizeof(int));
memset(Scounter1, 0, sizeof(Counter));
memset(Scounter2, 0, sizeof(Counter));
memset(Rcounter1, 0, sizeof(Counter));
memset(Rcounter2, 0, sizeof(Counter));

printf("scounter1: %d\n, scounter2: %d\n", Scounter1->counter, Scounter2->counter);
printf("Rcounter1: %d\n, Rcounter2: %d\n", Rcounter1->counter, Rcounter2->counter);
// return;
/* create 8 subprocesses*/
for (int i = 0; i < N; i++){

  status = fork();

  if (status == 0 || status == -1) break;//Only parent do fork
  pids->p[i] = status;

}
/*Assign jobs for processes*/
  if (status == -1){
    //error
  }else if (status == 0){ 
    // childTest();
    //sub proces
    int pid = getpid();
    // printf("%d\n", pid);

    if(pid == pids->p[0]){
      printf("Reporting\n");
      sigReport();
      // exit(0);
      //report process
    }else if(pid==pids->p[1] || pid==pids->p[2]){
      printf("sigHandling grp 1\n");
      sigHandle(1);
      // exit(0);
      //sig receive process
    }else if(pid==pids->p[3] || pid==pids->p[4]){
      printf("sigHandling grp 2\n");
      sigHandle(2);

    }else if(pid==pids->p[5] || pid==pids->p[6] || pid==pids->p[7]){
      printf("sigGenerating\n");
      //sig generate process
      // exit(0);
      sigGen();
    }else{
      exit(-1);
    }
  }else{

   
    //parent process
  // for(int i = 0; i  < N; i++){
  //   printf("%d th: %d\n", i, pids->p[i]);
  // }
/*
      for(int i = 0; i < N; i++){
        int waitStatus;
        if(waitpid(pids->p[i],&waitStatus,0)){
            printf("%d killed\n",pids->p[i]);
            //kill(pids->p[i],SIGKILL);
          }
      }

*/
  while(1);
  //    printf("sig1gen counter: %d\n  sig2gen counter: %d\n",Scounter1->counter,Scounter2->counter);
    }




}

/*spinlock*/
void spinLock(lock l){
    //printf("lock %x\n", l);
    while(test_and_set_bit(0, &l));  // TS-> 0: unlocked 1: locked

}

void spinUnlock(lock l){
    clear_bit(0, &l);
}

/*create shared memory space as cretical sections*/
Counter* sharedMem(key_t key){
  Counter *C;
  int shmid;
  shmid = shmget(key,sizeof(Counter),0666 | IPC_CREAT);
  if( shmid == -1){
    perror("shmget error");
    exit(key);
  }

  if((C = shmat(shmid, (void*)0, 0)) == (void*)-1) {
    perror("shmat error\n");
    exit(key);
  }

  return C;
}



/*create shared memory space for process control*/
PID* sharedPID(key_t key){
  PID *p;
  int shmid;
  shmid = shmget(key,N*sizeof(int),0666 | IPC_CREAT);
  if(shmid == -1){
    perror("shmget error");
    exit(key);
  }

  if((p = shmat(shmid, (void*)0, 0)) == (void*)-1) {
    perror("shmat error\n");
    exit(key);
  }

  return p;
}




void sigReport(){
  /*pid[0] report statistics
  report statistics
  1. Report time interval between the 1st and last signal received for each signal type and time cost for this interval
      tt1&tt2&sysTime for this interval
      t11&t12 for sig1 
      t21&t22 for sig2
      Then calculate average time between receiptions of each signal type during this period (sig1Ave, sig2Ave)
  2. Report values of 4 counters
  3. Report number of each signal caught in this period*/

  printf("Signal reporting process. %d\n", getpid());
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
        spinLock(Scounter1->l);
        int S1counter = Scounter1->counter;
        spinUnlock(Scounter1->l);

        spinLock(Scounter2->l);
        int S2counter = Scounter2->counter;
        spinUnlock(Scounter2->l);

        spinLock(Rcounter1->l);
        int R1counter = Rcounter1->counter;
        spinUnlock(Rcounter1->l);

        spinLock(Rcounter2->l);
        int R2counter = Rcounter2->counter;
        spinUnlock(Rcounter2->l);
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
            printf("The signals processed in %f is: %d\n", tTotal,            sigTotal);
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
  printf("Process %d generating\n", getpid());       
  while(1){
    int sigChoose = rand() % 100 ;
    // printf("%d\n",sigChoose);
    if(sigChoose % 2 == 1){
      c++;
      kill(0, SIGUSR1);
      //cretical section
      spinLock(Scounter1->l);
        Scounter1->counter++;
        // printf("Process %d is in\n", getpid());
        // printf("The sig1Counter is now: %d\n",Scounter1->counter);
        // printf("This is the %d operation\n",c);
      spinUnlock(Scounter1->l);
    }else{
      c++;
      kill(0, SIGUSR2);
      //cretical section
      spinLock(Scounter2->l);
        Scounter2->counter++;
        // printf("Process %d is in\n", getpid());
        // printf("The sig2Counter is now: %d\n",Scounter2->counter);
        // printf("This is the %d operation\n",c);
      spinUnlock(Scounter2->l);
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
  printf("Process %d handling\n", getpid());   
  if(sigNum == 1){
    signal(SIGUSR1, sigFunc);
  }else if(sigNum == 2){
    signal(SIGUSR2, sigFunc);
  }else{
    printf("Handling error");
    exit(-1);
  }
  while(1){

  }

}

void sigFunc(int sig){
  switch(sig){
    case SIGUSR1:
  //  printf("handling sig1\n");
    spinLock(Rcounter1->l);
    Rcounter1->counter++;
    // printf("Process %d is in\n", getpid());
    // printf("The sig1Counter is now: %d\n",Scounter1->counter);
    // printf("This is the %d operation\n",c);
    spinUnlock(Rcounter1->l);
     break;
    case SIGUSR2:
  //      printf("handling sig2\n");
    spinLock(Rcounter2->l);
    Rcounter2->counter++;
    // printf("Process %d is in\n", getpid());
    // printf("The sig2Counter is now: %d\n",Scounter2->counter);
    // printf("This is the %d operation\n",c);
    spinUnlock(Rcounter2->l);
      break;
    default:
    printf("Handling error");
    exit(2);
      break;
  }
}



// void childTest(){
//   //printf("I'm the child!\n");
//   if(count == 0){
//     printf("Report \n");
//   }else if(1 <= count <= 3){
//     printf("Sig generating Process\n");
//   }else{
//     printf("Sig handling Process\n");
//   }
//   count++;
//   //printf("count is %d\n",count);

// }
