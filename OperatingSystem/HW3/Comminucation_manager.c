/*
 * =====================================================================================
 *
 *       Filename:  Comminucation_manager.c

 *       Description:  To design and implement a communication system, which is constructed
												around a monitor.
 *
 *       Author:  Yufeng Wang 
 *       Email: Y.F.Wang@temple.edu
 *       Due: Oct 27 2013
 *		 Compile:  gcc Comminucation_manager.c -o Comminucation_manager -lpthread
 * =====================================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <pthread.h>
#include <string.h>
#define SIZE 5

/*
These are global
*/
pthread_mutex_t lock_it = 	PTHREAD_MUTEX_INITIALIZER;	//lock for the monitor
// pthread_cond_t write_it = PTHREAD_COND_INITIALIZER;
/* Condition variables for receive function for 4 threads*/
pthread_cond_t rec_1 = PTHREAD_COND_INITIALIZER;	
pthread_cond_t rec_2 = PTHREAD_COND_INITIALIZER;
pthread_cond_t rec_3 = PTHREAD_COND_INITIALIZER;
pthread_cond_t rec_4 = PTHREAD_COND_INITIALIZER;


typedef enum {FALSE, TRUE } boolean;
typedef struct{
	char *buffer[SIZE];
	int num;
}BUFFER;
BUFFER pool = {"",0};
// void *read(void *), *write(void *);
void *thread_operation(void *i); 
// boolean finish = FALSE;
int messageCount[4]; //count total number of messages sent to each thread 

void main(){
	pthread_t thread[4];  //thread_1, thread_2, thread_3, thread_4;
	int i;
	for(i=0; i<4; i++)
		pthread_create(&(thread[i]), NULL, &thread_operation, (void *) i);

	for(i = 0;i < 4;i++){
		pthread_join(thread[i],NULL);
	}

	pthread_mutex_destroy(&lock_it);
	pthread_cond_destroy(&rec_1);
	pthread_cond_destroy(&rec_2);
	pthread_cond_destroy(&rec_3);
	pthread_cond_destroy(&rec_4);	
}



void *thread_operation(void *i){
	boolean finish = FALSE;
    int k = (int)i+1;
	printf("\nThread %2d:  Starting\n",  k);
	while(!finish){
		FILE * fp;
       	char * line = NULL;
       	size_t len = 0;
       	ssize_t read;
		char fileName[11];
		sprintf(fileName, "%s%d%s", "thread", k, ".txt");
	  	fp = fopen(&fileName, "r");
       	while ((read = getline(&line, &len, fp)) != -1) {
        	printf("%s\n", line);

   			if(strncmp(line,"quit",4)==0){
   			/*quit operation*/
   				finish = TRUE;
   			}else if(strncmp(line,"send",4)==0){
   			/*send operation*/
   			}else if(strncmp(line,"receive",7)==0){
   			/*receive operation*/
   			}else{
			/*Error command file*/  	
    		printf("Something wrong with the command file\n");			
				finish = TRUE;
   			}
       }
       // break;

	}
	printf("Thread %2d: Exiting\n", k);
	return NULL;
}


/*
Code to fill the buffer
*/
// void *read(void * junk){
// 	int ch;
// 	printf("R %2d: Starting\n", pthread_self());
// 	while(!finish){
// 		pthread_mutex_lock(&lock_it);
// 		if(pool.num != SIZE){
// 			ch = getc(stdin);
// 			pool.buffer[pool.num++] = ch;
// 			printf("R %2d: Got text [%c]\n", pthread_self(), isalnum(ch) ? ch : '#');
// 			if(pool.num == SIZE){
// 				printf("R %2d: Signaling full\n", pthread_self());
// 				pthread_cond_signal(&write_it);
// 			}

// 		}
// 		pthread_mutex_unlock(&lock_it);
// 	}
// 	printf("R %2d: Exiting\n",pthread_self());
// 	return NULL;
// }


// Code to write buffer

// void *write(void * junk){
// 	int i;
// 	printf("W %2d: Starting\n", pthread_self());
// 	while(!finish){
// 		pthread_mutex_lock(&lock_it);
// 		printf("\nW %2d: Waiting\n", pthread_self());
// 		while(pool.num != SIZE)
// 			pthread_cond_wait(&write_it,&lock_it);
// 		printf("W %2d: Writing buffer\n", pthread_self());
// 		for(i=0;pool.buffer[i] && pool.num;++i,pool.num--)
// 			printf("The result: %c\n" ,putchar(pool.buffer[i]));
// 		pthread_mutex_unlock(&lock_it);
// 	}
// 	printf("W %2d: Exiting\n", pthread_self());
// 	return NULL;
// }
