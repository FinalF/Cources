/*
 * =====================================================================================
 *
 *       Filename:  yufeng_wang_homework1.c
 *
 *    Description:  
 *
 *        Version:  1.0
 *        Created:  2013年09月08日 18时13分34秒
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  YOUR NAME (wyf), 
 *   Organization:  
 *
 * =====================================================================================
 */

#include <sys/time.h>
#include<time.h>
#include<stdio.h>
#include<string.h>
#include<pthread.h>
#include<stdlib.h>
#include<unistd.h>

void* tfunc(void *arg) { 
    // adding
        long n = (long)arg; 
	pthread_t id;
	id = pthread_self();
	volatile int  x;
	long i,j;

		for(i=0;i<n;i++){
			x = 0;
			for(j=0;j<10000;j++){
				x=x+j;
			}
		}
    return NULL;
}

void main () {
//    using namespace boost;
    printf("choose a mode:\n 1 start to test\n0 quit\n");
    int c;
    scanf("%d",&c);
//    clock_t b, e;
    double t_spent;
	struct timeval t1,t2;  
    while(c != 0){
         long n;
         int size;
	 printf("Tell me how many threads you wonna create:\n");
	 scanf("%d",&size);
         printf("Tell me how many iterations you wonna each thread run:\n");
	 scanf("%ld",&n);
        
//	clock_t begin, end; //timing
//	double time_spent;
	int t,err;
//	int size=3;
	pthread_t tid[size];
//	 thread thrd[argv[0]];
//	b = clock();
	gettimeofday(&t1,NULL);
		for(t = 0;t < size;t++){
		    err = pthread_create(&(tid[t]),NULL,&tfunc,(void *) n );
			printf("Create the %d thread",t+1);
			 if (err != 0)
        	    printf("n't create thread :[%s]", strerror(err));
       		 else
        	    printf("\n Thread created successfully\n");

    	}
		for(t = 0;t < size;t++){
			pthread_join(tid[t],NULL);
		}

//	pthread_exit(NULL);
//    e = clock();
	 gettimeofday(&t2,NULL);
//    t_spent = (double)(e - b) / CLOCKS_PER_SEC;
	t_spent = (double) ((t2.tv_sec * 1000 + t2.tv_usec/1000)
		  - (t1.tv_sec * 1000 + t1.tv_usec/1000))/1000;  
  printf("The total time is: %f s\n",t_spent);
	pthread_exit(NULL);

            

        printf("choose a mode:\n1 start to test0 quit\n");
		scanf("%d",&c);
}
}


