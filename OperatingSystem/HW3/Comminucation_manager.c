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
#include <ctype.h>
#include <pthread.h>
#include <string.h>
#define SIZE 20

/*
These are global
*/
pthread_mutex_t lock_it = 	PTHREAD_MUTEX_INITIALIZER;	//lock for the monitor
pthread_cond_t write_it = PTHREAD_COND_INITIALIZER;
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
void *read(void *), *write(void *);
boolean finish = FALSE;
void main(){
	pthread_t t_read, t_write;
	pthread_create(&t_read, NULL, read, (void *) NULL);
	pthread_create(&t_write, NULL, write, (void *) NULL);

	pthread_join(t_write,(void **) NULL);
	pthread_mutex_destroy(&lock_it);
	pthread_cond_destroy(&write_it);
}

/*
Code to fill the buffer
*/
void *read(void * junk){
	int ch;
	printf("R %2d: Starting\n", pthread_self());
	while(!finish){
		pthread_mutex_lock(&lock_it);
		if(pool.num != SIZE){
			ch = getc(stdin);
			pool.buffer[pool.num++] = ch;
			printf("R %2d: Got text [%c]\n", pthread_self(), isalnum(ch) ? ch : '#');
			if(pool.num == SIZE){
				printf("R %2d: Signaling full\n", pthread_self());
				pthread_cond_signal(&write_it);
			}

		}
		pthread_mutex_unlock(&lock_it);
	}
	printf("R %2d: Exiting\n",pthread_self());
	return NULL;
}

/*
Code to write buffer
*/
void *write(void * junk){
	int i;
	printf("W %2d: Starting\n", pthread_self());
	while(!finish){
		pthread_mutex_lock(&lock_it);
		printf("\nW %2d: Waiting\n", pthread_self());
		while(pool.num != SIZE)
			pthread_cond_wait(&write_it,&lock_it);
		printf("W %2d: Writing buffer\n", pthread_self());
		for(i=0;pool.buffer[i] && pool.num;++i,pool.num--)
			putchar(pool.buffer[i]);
		pthread_mutex_unlock(&lock_it);
	}
	printf("W %2d: Exiting\n", pthread_self());
	return NULL;
}
