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
pthread_cond_t write_it = PTHREAD_COND_INITIALIZER;	//condition variable for the buffer
/* Condition variables for receive function for 4 threads*/
pthread_cond_t rec_1 = PTHREAD_COND_INITIALIZER;	
pthread_cond_t rec_2 = PTHREAD_COND_INITIALIZER;
pthread_cond_t rec_3 = PTHREAD_COND_INITIALIZER;
pthread_cond_t rec_4 = PTHREAD_COND_INITIALIZER;


typedef enum {FALSE, TRUE } boolean;
typedef struct{
	char *buffer[SIZE];
	int num ;
	int exist[4];
}BUFFER;
BUFFER pool = {"",0};

// void *read(void *), *write(void *);
void *thread_operation(void *i); 
BUFFER bufferInitial(BUFFER pool);
BUFFER buffer_reset(BUFFER pool);
BUFFER messageAdd(int ID,char * m, BUFFER  pool);
void messageSent(int ID);
void signalWait(int ID);
BUFFER messageTake( int ID, BUFFER  pool);
int dstID(char * m);
// boolean finish = FALSE;

void main(){

	pthread_t thread[4];  //thread_1, thread_2, thread_3, thread_4;
	int i;
	pool = bufferInitial(pool);
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
 	float d;
	boolean finish = FALSE;
    int k = (int)i+1;
	printf("\nThread %2d:  Starting\n",  k);
	// while(!finish){
		FILE * fp;
       	char * line = NULL;
       	size_t len = 0;
       	ssize_t read;
		char fileName[11];
		sprintf(fileName, "%s%d%s", "thread", k, ".txt");
	  	fp = fopen(&fileName, "r");
       	while ((!finish && (read = getline(&line, &len, fp)) != -1)) {
        	//printf("command: %s, the length is %d\n", line, strlen(line));
   			if(strncmp(line,"quit",4)==0){
   			/*quit operation*/
   				finish = TRUE;
   			}else if(strncmp(line,"send",4)==0){
   			/*send operation*/
				pthread_mutex_lock(&lock_it);
				/*In the case the buffer is full*/
				if(pool.num == SIZE){
					printf("\nThe buffer is full, waiting");
					pthread_cond_wait(&write_it,&lock_it);
				}
	        	/*put the message into the buffer*/
				pool = messageAdd(k,line,pool);
				pool.exist[k-1] = 1;
				messageSent(dstID(line));
				printf("\nSend notification to thread %d",dstID(line));
				pthread_mutex_unlock(&lock_it);

   			}else if(strncmp(line,"receive",7)==0){
   			/*receive operation*/
				pthread_mutex_lock(&lock_it);
				/*In the case the buffer is empty*/
				if(pool.num == 0)
				signalWait(k);
				/*wait for the message sent to me*/
				pool = messageTake(k,pool);
				// printf("\nThe value of pool.exist is: %d",pool.exist[k-1]);
				// showBuffer(pool.buffer);
				if(pool.exist[k-1] ==  0){
					printf("\nNo message for thread %d, waiting",k);
					signalWait(k);
					pool = messageTake(k,pool);
				}
				pthread_mutex_unlock(&lock_it);
   			}else{
			/*Error command file*/  	
    			printf("Something wrong with the command file\n");			
				finish = TRUE;
   			}
 			/* Sleep random time (0.01~0.02s)*/
			// d = (float)(rand() % 10000 + 10000);
			// usleep(d);  			
       }
	// }
	printf("\nThread %2d: Exiting\n", k);
	return NULL;
}


void signalWait(int ID){
	if(ID==1){
		pthread_cond_wait(&rec_1,&lock_it);
	}else if(ID==2){
		pthread_cond_wait(&rec_2,&lock_it);		
	}else if(ID==3){
		pthread_cond_wait(&rec_3,&lock_it);		
	}else if(ID==4){
		pthread_cond_wait(&rec_4,&lock_it);		
	}

}

void messageSent(int ID){
	if(ID==1){
		pthread_cond_signal(&rec_1);
	}else if(ID==2){
		pthread_cond_signal(&rec_2);		
	}else if(ID==3){
		pthread_cond_signal(&rec_3);		
	}else if(ID==4){
		pthread_cond_signal(&rec_4);		
	}

}

int dstID(char * m){
	char *dst = (char*) malloc(sizeof(char));
	strncpy(dst, m+5, 1);
	int dstID = atoi(dst);
	return dstID;
}

BUFFER messageTake(int ID, BUFFER  pool){
/*find out the message belongs to me based on the content*/
	int i ;
	printf("\nThread %d is trying to receiving", ID);
	for(i = 0; i < pool.num; i++){
		char * line = malloc(strlen(pool.buffer[i]));
		strncpy(line,pool.buffer[i],strlen(pool.buffer[i]));
		char *dst = (char*) malloc(sizeof(char));
		strncpy(dst, line+5, 1);
		int dstID = atoi(dst);
		// printf("\nThe %d message is sent to %d", i+1,dstID);
		if(ID == dstID){
			pool.exist[ID-1] = 1;
			// printf("\nThe value of pool.exist inside the func is: %d",pool.exist[ID-1]);
			char * message = (char*) malloc((strlen(line)-7)*sizeof(char));
			strncpy(message, line+7, (strlen(line)-7));
			// /*Empty this slot*/
			pool.buffer[i] = malloc(strlen("null"));
			strcpy(pool.buffer[i], "null");
			// /*Reset the buffer, guarantee FIFO*/
			pool = buffer_reset(pool);
			pool.num--;
			// /*If a send has been blocked because of full buffer, here release it*/
			if(pool.num == SIZE - 1){
				pthread_cond_signal(&write_it);
			}
			printf("\nThread %d is receiving message: %s", ID, message);
			printf("\nCurrent number of messages in the buffer: %d", pool.num);
			showBuffer(pool.buffer);
			/*everytime only take one message*/
			break;
		}else{
			pool.exist[ID-1] = 0;
			// printf("\nNot found! The value of pool.exist inside the func is: %d",pool.exist[ID-1]);
		}
	}
	// printf("\nThe value of pool.exist inside the func is: %d",pool.exist[ID-1]);
	return pool;
}


BUFFER messageAdd(int ID, char * m, BUFFER  pool){
	char *dst = (char*) malloc(sizeof(char));
	strncpy(dst, m+5, 1);
	int dstID = atoi(dst);
	char * message = (char*) malloc((strlen(m)-7)*sizeof(char));
	strncpy(message, m+7, (strlen(m)-7));
	printf("\nThread %d is trying to send to thread %d a message: %s", ID, dstID,message);
/*put the whole message from the command file into the buffer*/
	pool.buffer[pool.num] = malloc(strlen(m));
	strncpy(pool.buffer[pool.num],m,strlen(m));
	pool.num++;
		printf("\nCurrent number of messages in the buffer: %d", pool.num);
	return pool;
}

BUFFER  buffer_reset(BUFFER  pool){
	BUFFER  tmp = {"",0};
	tmp.num = pool.num;
	int i;
	for(i=0;i<4;i++){
		tmp.exist[i] = pool.exist[i];
	}
	int j = 0;
	for(i = 0; i < SIZE; i++){
		if(strncmp(pool.buffer[i],"null",4)!=0){
			tmp.buffer[j] = malloc(strlen(pool.buffer[i]));
			strncpy(tmp.buffer[j],pool.buffer[i],strlen(pool.buffer[i]));
			j++;
		}
	}
	for(i = j; i < SIZE; i++){
		tmp.buffer[i] = malloc(strlen("null"));
		strcpy(tmp.buffer[i], "null");
	}

	return tmp;
}


void showBuffer(char* buffer[SIZE]){
	int i;
	for(i = 0; i < SIZE; i++){
		// if(strncmp(buffer[i],"null",4)!=0)
			printf("\nThe %d message: %s",i+1, buffer[i]);
	}
}

BUFFER bufferInitial(BUFFER pool){
	int i;
	for(i = 0; i < SIZE; i++){
			pool.buffer[i] = malloc(strlen("null"));
			strncpy(pool.buffer[i],"null",strlen("null"));
	}
	return pool;
}
