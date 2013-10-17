package Assignment3_Garwick;

import java.util.Scanner;

public class GarwickTable {

	/**
	 *yufeng
	 */
	static boolean debug=false;
	static int L ; // Memory size 
	static int N ; // number of stacks
	static double[] p = {1,0.5,0}; 
	static int[] unit = {1,20,50};
	static int k;// current stack number
	static int runs;//times of operation
	static int[] table; //Table
	static int[] top;
	static int[] base;
	static int[] oldtop;
	static int[] newbase;
	static int[] increase;
	public static double[] culmuFrequency ;
	static double[][][] resultMatrix =  new double[3][3][7]; //table putput
	static double wordMoved;
	static double memoryReorged;
	static double wordMovedCount;

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in =  new Scanner(System.in);
		System.out.println("Please input the length of table: ");
		L = in.nextInt();
		System.out.println("Please input the number of stacks: ");
		N = in.nextInt();
		System.out.println("Please input the number of runs: ");
		runs = in.nextInt();	
		
		tableProduce(runs);
		
		

	}

	static void tableProduce(int runtimes){

		for(int t = 1; t <= runtimes; t++){
		for(int j = 1; j <= 3 ;j++){	//spurts loop
			int spurts = unit[j - 1];
//			System.out.print("\nSpurts in this loop: "+spurts);
		for(int i = 1;i <= 3; i++){		//p loop

			double rho = p[ i - 1];
//			System.out.print("\nRho in this loop: "+rho);
			
			resultMatrix[j-1][i-1][0]=rho;
			//table for uniform input
			initialization();		
			for (int k = 1; k <= 0.7 * L/spurts ; k++) {
			readInput(N,rho,spurts,1);
			}
			
//			System.out.print("\nWordMove for 70%(uniform):"+ wordMoved);
			
			resultMatrix[j - 1][i - 1][1] += wordMoved/runtimes;
			
			for (int k = 1; k <= 0.3 * L/spurts ; k++) {
			readInput(N,rho,spurts,1);
			}
			
//			System.out.print("\nWordMove for full set(uniform):"+ wordMoved);
			
			resultMatrix[j - 1][i - 1][2] += wordMoved/runtimes;
			
			resultMatrix[j - 1][i - 1][3] += memoryReorged/runtimes;
			
			
			
			//table for frequency input
			initialization();
			for (int k = 1; k <= 0.7 * L/spurts ; k++) {
				readInput(N,rho,spurts,2);
			}
			resultMatrix[j - 1][i - 1][4] += wordMoved/runtimes;			
			for (int k = 1; k <= 0.3 * L/spurts ; k++) {
			readInput(N,rho,spurts,2);
			}
			
			resultMatrix[j - 1][i - 1][5] += wordMoved/runtimes;
			
			resultMatrix[j - 1][i - 1][6] += memoryReorged/runtimes;		
		}
		}	

		}
		System.out.print("               Uniform random choice of stacks    Stacks chosen with frequency 1/2^n");	
		print("resultMatrix",resultMatrix);
		
		
		
		
	}
	
	static void initialization(){

		
		
		table = new int[L+1];
		top = new int[N];
		base = new int[N + 1];
		oldtop = new int[N];
		newbase = new int[N + 1];
		increase = new int[N];
		wordMoved = 0;
		memoryReorged = 0;
		// initialize table
		for (int i = 0; i <= L ; i++) {
			table[i] = 0;
		}
		// initialize base[] and top[] oldtop[]
		for (int i = 1; i <= N -1; i++) {
			base[i] = i * (int) (L / N);
			top[i] = base[i];
			oldtop[i] = top[i];
		}
		base[N] = L;
		
		culmuFrequency = new double[N];
		double frequencySum = 0;
		for (int i = 1; i <= N; i++) {
			frequencySum = Math.pow(0.5, i) + frequencySum;
			culmuFrequency[i - 1] = frequencySum;
		}	
		
		
		
		if(debug==true){
			System.out.println("frequencySum is: " + frequencySum);
		}
		
		if (debug == true){
		System.out.println("Initialization: ");
		print("base", base);
		print("top", top);
		}
		
		if(debug==true){
		print("table", table);
		print("base", base);
		print("top", top);
		print("oldtop", oldtop);
		System.out.println("Program on the example input");
		}

	}

	
	//read data in(choose model: uniform/frequency)
	static void readInput(int stacknumber, double rho,int spurts,int choise) {
	
		switch(choise){
		case 1:		
	
				input(uniformInputGenerate(N),rho,spurts);				
		
		break;
		
		case 2:			

				input(frequencyInputGenerate(N),rho,spurts);				
	
		break;
		}

//		System.out.println("The final table we get is :");
		
		if(debug==true){
		print("table", table);
		print("base", base);
		print("top", top);
		}
		



	}
	
	
	
	
	
	//generate one number according to the uniform
	static int uniformInputGenerate(int numberOfStacks){
		int stackNo =  (int) Math.ceil(Math.random()*N); 
			return stackNo;		
	}
	
	//generate one number according to the frequency
	static int frequencyInputGenerate(int numberOfStacks){
		int stackNo = 1;
		//calculate the frequency for each stack
		double ran = Math.random();
		for (int i = 1; i <= N; i++) {
			if (ran <= culmuFrequency[i - 1]) {
				stackNo = i;
				i = N+1;
			}
		}

		return stackNo;

		
	}
	
	
	

		static void garwick(double rho) {
			memoryReorged++;
			wordMovedCount = 0;

//			System.out.println("The " + memoryReorged + " time of Garwick");
			int spaceUsed = 0;
			int sumIncrease = 0;
			int REM = 0;
			double[] alloc = new double[N];
			int[] delta = new int[N];
			double remain = 0.0;  // Fraction part of Alloc1 ~ Alloc i-1
			int deltaSum = 0;

			for (int i = 1; i <= N; i++) {
				//   Total Space Used ( N (Top(i)-Base(i)) )
				spaceUsed = spaceUsed + (top[i - 1] - base[i - 1]);

				increase[i - 1] = Math.max(0, top[i - 1] - oldtop[i - 1]);

				// Calculate Sum of Increases ( N (Increase(i) )
				sumIncrease = sumIncrease + increase[i - 1];
			}

			REM = L - spaceUsed ;
			
			if(debug==true){
			System.out.println("spaceUsed for now is : " + spaceUsed);
			System.out.println("Increases are: " + sumIncrease);
			System.out.println("Remaining Space is : " + REM);
			}
			
			if (REM < 0) {
				System.out.println("The array is full. Can not reallocate.");
				top[k - 1] = top[k - 1] - 1;
			} else {
				if(debug==true){
				System.out.println();
				print("increase", increase);
				print("base", base);
				print("top", top);
				print("oldtop", oldtop);
				}
				for (int i = 1; i <= N; i++) {
					alloc[i - 1] = (double) REM * (0.1 / (double) N	+ ((double) increase[i - 1] / (double) sumIncrease)* rho * 0.9
									+ ((double) (top[i - 1] - base[i - 1]) / (double) spaceUsed)* (1 - rho) * 0.9);
				}
				if(debug==true){
				System.out.println();
				print("alloc", alloc);
				}
				double sumAlloc = 0.0;
				for (int i = 1; i <= N; i++) {
					sumAlloc = sumAlloc + alloc[i - 1];
				}


				// calculate delta
				for (int i = 1; i <= N - 1; i++) {
					delta[i - 1] = (int) alloc[i - 1] +(int)(alloc[i-1] - (int)alloc[i-1] + remain);
					remain = (alloc[i - 1] + remain) - delta[i - 1];
					deltaSum = deltaSum + delta[i - 1];
				}
				delta[N - 1] = REM - deltaSum;
				
				if(debug==true){
				System.out.println();
				print("delta", delta);
				}
				
				// update newbase
				newbase[0] = base[0];
				for (int i = 2; i <= N; i++) {
					newbase[i - 1] = newbase[i - 2] + (top[i - 2] - base[i - 2])+ delta[i - 2];
				}

				moving();
				for (int i = 1; i <= N; i++) {
					oldtop[i - 1] = top[i - 1];
				}
			}
			if(debug==true){
			System.out.println("After this reorganization, the table is :");
			print("table", table);
			System.out.println("There are " + wordMovedCount+ " numbers moved during this reorganization.");
			}
		}

		static void moving() {
			/*
			 * Set Newbase(N+1) to Base(N+1) and Top(k) to Top(k)-1 so it reflects
			 * the actual current top of stack k (the new element was not added
			 * yet). Set i to 2.
			 */
			newbase[N] = base[N];
			
			if(debug==true){
			System.out.println();
			print("newbase", newbase);
			System.out.println();
			}
			
			top[k - 1] = top[k - 1] - 1;

			int i = 2;
			while (i <= N) {
				if (newbase[i - 1] < base[i - 1]) {
					int downMove = base[i - 1] - newbase[i - 1];
					// System.out.println("q:" + q);
					for (int h = base[i - 1] + 1; h <= top[i - 1]; h++) {
						table[h - downMove ] = table[h ];
						table[h ] = 0; //Clear the slot
						wordMoved++;
						wordMovedCount++;
					}
					base[i - 1] = newbase[i - 1];
					top[i - 1] = top[i - 1] - downMove;
					i = i + 1;
				} else if (newbase[i - 1] > base[i - 1]) {
					int j = i + 1;
					while (j <= N + 1) {
						if (newbase[j - 1] <= base[j - 1]) {
							break;
						}
						j ++;
					}

					for (int t = j - 1; t >= i; t--) {
						int moveDown = newbase[t - 1] - base[t - 1];
						for (int h = top[t - 1]; h >= base[t - 1] + 1; h--) {
							table[h + moveDown ] = table[h ];
							table[h ] = 0;
							wordMoved++;
							wordMovedCount++;
						}
						base[t - 1] = newbase[t - 1];
						top[t - 1] = top[t - 1] + moveDown;
					}
					i = j;
				} else {
					i = i + 1;
				}
			}

		}

	static void input(int stackNumber,double p,int spurts) {
		for (int i = 1; i <= spurts; i++) {
		if (!add(stackNumber)) {
			if (debug == true){
			System.out.println("when we try to add 1 element to stack "
					+ stackNumber);
			System.out.println("So here we start Garwick Aglorithm.");
			}
			k = stackNumber;
			garwick(p);
			add(stackNumber);
			oldtop[k - 1]++;
		}
		if(debug==true){
		System.out.println("Add 1 number to stack " + stackNumber);
		print("table", table);
		System.out.println();
		}
	}
	}



static boolean add(int n) {
	//add a number into the n'th stack
	top[n - 1]++;
	// System.out.println("top[n-1]: " + top[n - 1]);
	if (top[n - 1] >= base[n] + 1) {
		if (debug == true){
		System.out.print("overflow happens ");
		}
		return false;
	} else  {
		table[top[n - 1]] = n;
		// System.out.println("insert successfully");
		return true;
	} 
}

	
	static void print(int[] table) {
		for (int i = 0; i <= table.length - 1; i++) {
			System.out.print(table[i] + " ");
		}
		System.out.println();
	}
	
	static void print(String str, int[] table) {
		System.out.print(str + ": ");
		for (int i = 0; i <= table.length - 1; i++) {
			System.out.print(table[i] + " ");
		}
		System.out.println();
	}

	static void print(String str, double[] table) {
		System.out.print(str + " :");
		for (int i = 0; i <= table.length - 1; i++) {
			System.out.print(table[i] + " ");
		}
		System.out.println();
	}
	
	
	 static void print(String str, double[][][] resultMatrix) {
		// TODO Auto-generated method stub
//		System.out.print(str + " :");
		for (int i = 1; i <= 3; i++) {
			System.out.println();
		for(int j = 1; j<= 3; j++){
			System.out.println();
				for(int k = 1; k<= 7; k++){
					System.out.print("    " + Baoliu(resultMatrix[i-1][j-1][k-1],1) + "    ");
				}
			}

		}
		System.out.println();
		
	}
	
    static double Baoliu(double dout,int n){
        double p= Math.pow(10, n); 
        return Math.round( dout * p ) / p;
   }
}
