
package Assignment1_topologicalsort;

import java.util.ArrayList;
import java.util.Scanner;
import Assignment1_topologicalsort.ListArray_version3;
import Assignment1_topologicalsort.ListArray_version3.ListNode;

/**
 *
 * @author yufeng
 * Programming Tech Assignment 1: Topological sorting
 * 2013/01/30
 */
public class TopoSort_version3 {
        static int N; //  node number
	static int ResultN; // result number
	static int MAX = 30; //  the max number of topsorts result to show
	static int[] count; // predecessor array
	static ListArray_version3[] succ; //  successor array
	static ListArray_version3 bag; // bag
	static ListArray_version3 output; // output
	static boolean Debug = false;
	static boolean confirm =true;
	static boolean inputValidation = true;
   
        
        public static void main(String[] args){
            
          /*Step1:
           * read input number pairs
           */
          read();
          /*Step2:
           * initialize the predecessor array count[]
           * initialize the successor array succ[]
           */
          pre_succ_Initialization();
          /*Step3:
           * read number pairs 
           *correspondant operations on count[] and succ[] 
           */ 
          nextpair();
          /*Step4:
           * Need the user to confirm the input pairs, otherwise reinput the pairs
          * initialize bag, output array
          *correspondant initilization on count[] and succ[] 
 			Step5: topsorts()
           * include the pair input confirmation.
           */
          step4_5();
        }
        
        
        
static void step4_5(){
              int step;
        	  while(confirm){
          System.out.println("Please confirm the number pairs you input, are above pairs correct?(yes/no)");
          @SuppressWarnings("resource")
		Scanner ans = new Scanner(System.in);

          String answer = ans.next();
          System.out.println("The input is :"+answer);
          if(answer.equals("yes")){
        	  step = 1;
          }
          else if(answer.equals("no")){
        	  step = 2;
          }
          else{
        	  step = 3;
          }
          switch(step){
          case 1:  {
        	  confirm = false;
        	  baginitialization();  
              /* Step5: topsorts()
               * 
               */
              topsorts();
              
              resultNumber();
              
              break;
          }
          case 2: {
        	  System.out.println("Please reinput pairs");          
              nextpair();
              step4_5();
              break;
          }
          default:{
        	  System.out.println("invalid input");        
        	  step4_5();
        	  break;
          }
          }
        	  }
        }
        
        

@SuppressWarnings("resource")
static void read(){
        /*
         * Constant time
         */
        System.out.println("Please input the number of nodes");
        Scanner in = new Scanner(System.in);
        N = in.nextInt();
    }

    
static void pre_succ_Initialization(){
        /*read input number pairs
         * initialize the predecessor array count[]
         * initialize the successor array succ[]
         */
    	/*
    	 * count initia: Constant time
    	 * succ initia: proportional to N
    	 */

        //predecessor array initialization, constant time
        count = new int[N];       
		System.out.print("The number you input is " + N + ". ");
        // successor array initialization  ,proportional to n
        succ = new ListArray_version3[N];
		for (int i = 0; i <= N - 1; i++) {
			succ[i] = new ListArray_version3();
		}
	}
	
	
		
static void nextpair(){
	/*
	 * Read pairs: Proportional to m
	 */	
//    System.out.println("\nPlease input the number of Pairs: ");
	@SuppressWarnings("resource")
	Scanner in = new Scanner(System.in);
//	int m = in.nextInt();
	int[][] inputArray = new int[2*N][2];  //To store the input pairs
//	for(int i=0; i<m;i++){
	int paircount = 0;
	//read input pair takes constant time, intotal proportional to m
	while(inputValidation){
    System.out.print("Please input the Pair numbers\n First: ");
	int a = in.nextInt();
    System.out.print("Second:  \n");
	int b = in.nextInt();
	if ((a > N) | (b > N)) {
	// when number is larger than max
	System.out.println("Error! Numbers exceeds the maximum limit: "+ N);
	inputValidation = false;
	System.exit(1);
	}		
	else if(a == 0 || b == 0){
		System.out.println("Input is end!");
		inputValidation = false;
	}
    else {
	// filling number in predecessor array  
	count[b - 1]++;
	// link list to successor array
    succ[a - 1].add(b);

    inputArray[paircount][0] = a;
    inputArray[paircount][1] = b;
	paircount ++;
    }
	}

//debug : count & succ have been successfully initilized
	if(Debug){	
		for(int i=0; i<count.length;i++){
			System.out.println(count[i]);
		}

	}
	System.out.println("The pairs you input are :");
	for (int i=0;i<paircount;i++){
			System.out.println("Pair" + i + ": " + inputArray[i][0] + " -- " + inputArray[i][1]);		
	}
 }
    



static void baginitialization(){
	/*
	 * initialize bag, output array
	 *correspondant initilization on count[] and succ 
	 */
	// output numbers count initilization
	ResultN = 0;
	// bag initialization
	bag = new ListArray_version3();
	// output initialization
	output = new ListArray_version3();
	// predecessor array initialization
	
    if(Debug){
    System.out.println("Initialization");
    }
    
	for (int i = 0; i <= N - 1; i++) {
		if (count[i] == 0) {
			bag.add(i + 1);
			count[i] = -1;
		}
		
        if(Debug){
		//debug: bag has been successfully initialized
        System.out.println("Bag size is:" + bag.Size());
        }
        
	}        
}





 static void topsorts(){
     if (bag.Size() != 0) {
	// when bag is not empty
	// record current bag size
	int bagsize = bag.size;
	for (int i = 1; i <= bagsize; i++) { 
        // move the first object element to output array 
        int obj = bag.delete(1);
        
        if(Debug){
        System.out.println("Current bag size:" +bag.Size() +"\noutput number is:" + obj);
        }
        
        output.add(obj);
        
        if(Debug){
        output.display();
        System.out.println("The" + i + "number in output is: " + obj);
        }
        
        // remove this element's successor info in succ_array
        int size = succ[obj - 1].Size();
//		ListArray succlist = new ListArray();
		
        //Successors array traversals
        for (int k = 0; k < size ; k++) {
//            int t = succ[obj - 1].delete(1);
//            succlist.add(t);
        	int t = getData(succ[obj-1].current);
        	succ[obj-1].pointerMove(1);
            count[t - 1]--;
        // move object without predecessor to bag
			if (count[t-1] == 0) {
                 bag.add(t);
                 count[t-1] = -1;
			}
        }  
        // recursion
        
	topsorts();
				
// Reverse the above
	    succ[obj-1].current = succ[obj-1].head;
		for (int k = 0; k < size ; k++) {
//		int r = succlist.delete(1);
	    int r = getData(succ[obj-1].current);
    	succ[obj-1].pointerMove(1);
		succ[obj - 1].add(r);
		count[r - 1]++;
		if (count[r-1] == 0) {
            bag.delete(bag.Size());
            count[r-1] = 1;
		}
	} 
	// delete the correspondant object from output array
     output.delete(output.Size());
     bag.add(obj);
     
     if(Debug){
     System.out.println("After deleting the "+i+"number, number in output is: " + obj);
     output.display();
     }
	
     }
	} 
	// when bag is empty
    else {
	if (output.size == N) {
		// when output is full, print data in output
		if (ResultN +1 == MAX) {
                    // when output topsorts number became SHOWMAX
                    System.out.print("The " + (ResultN + 1) + " sequence is:");
                    output.display();  //output the last sequence because of output limit
                    System.out.println("You got more than " + MAX +" topological sorts.");
                    ResultN++; // update COUNT
                } 
		else if(ResultN +1 < MAX){
			System.out.print("#" + (ResultN + 1) + " :");
			output.display();
			ResultN++;
		}
		else{
			ResultN++;
		}

	}
        else {
            System.out.println("Opps! It seems that the input numbers do not follow properties of partial orders");
        	System.exit(1);
	}

		
	}
}
    
        
static void resultNumber(){
	System.out.println("There are in total " + ResultN +" sorting results");
}


//Get the data from the node
static int getData(ListNode x){
	return x.dataReturn();
}

    
    
}
