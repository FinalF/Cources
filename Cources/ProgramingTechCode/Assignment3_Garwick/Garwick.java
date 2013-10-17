package Assignment3_Garwick;


public class Garwick {

	/**
	 * Yufeng 
	 * 02/10/2013
	 * Garwick Algorithm
	 */

		static int L = 21; // Memory size 
		static int N = 3; // number of stacks
		static double p = 0.5; // as Rho

		static int k;// current stack number

		static int[] table; //Table

		static int[] top;
		static int[] base;
		static int[] oldtop;
		static int[] newbase;
		static int[] increase;

		static int wordMoved;
		static int memoryReorged;
		static int wordMovedCount;

		static int[] input = {1,1,1,3,3,3,3,2,2,3,3,3,3,2,2,2,1,2,2,2,2};


		public static void main(String[] args) {

			System.out.println("Garwick's Algorithm");
				exampleInput(input);
		}

		static void garwick() {
			memoryReorged++;
			wordMovedCount = 0;

			System.out.println("The " + memoryReorged + " time of Garwick");
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

			System.out.println("spaceUsed for now is : " + spaceUsed);
			System.out.println("Increases are: " + sumIncrease);
			System.out.println("Remaining Space is : " + REM);

			if (REM < 0) {
				System.out.println("The array is full. Can not reallocate.");
				top[k - 1] = top[k - 1] - 1;
			} else {
				System.out.println();
				print("increase", increase);
				print("base", base);
				print("top", top);
				print("oldtop", oldtop);

				for (int i = 1; i <= N; i++) {
					alloc[i - 1] = (double) REM * (0.1 / (double) N	+ ((double) increase[i - 1] / (double) sumIncrease)* p * 0.9
									+ ((double) (top[i - 1] - base[i - 1]) / (double) spaceUsed)* (1 - p) * 0.9);
				}
				System.out.println();
				print("alloc", alloc);
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

				System.out.println();
				print("delta", delta);

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
			System.out.println("After this reorganization, the table is :");
			print("table", table);
			System.out.println("There are " + wordMovedCount+ " numbers moved during this reorganization.");

		}

		static void moving() {
			/*
			 * Set Newbase(N+1) to Base(N+1) and Top(k) to Top(k)-1 so it reflects
			 * the actual current top of stack k (the new element was not added
			 * yet). Set i to 2.
			 */
			newbase[N] = base[N];

			System.out.println();
			print("newbase", newbase);
			System.out.println();

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

		static void exampleInput(int[] input) {
			
		
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

			System.out.println("Initialization: ");
			print("table", table);
			print("base", base);
			print("top", top);
			print("oldtop", oldtop);
			System.out.println("Program on the example input");

			for (int i = 0; i < input.length; i++) {	
					input(input[i]);				
			}

			System.out.println("The final table we get is :");

			print("table", table);
			print("base", base);
			print("top", top);

			System.out.println("Total word moved times: " + wordMoved);
			System.out.println("Total memory reorganized times: " + memoryReorged);
			wordMoved = 0;
			memoryReorged = 0;

		}

		static void input(int stackNumber) {
				if (!add(stackNumber)) {
					System.out.println("when we try to add 1 element to stack "
							+ stackNumber);
					System.out.println("So here we start Garwick Aglorithm.");
					k = stackNumber;
					garwick();
					add(stackNumber);
					oldtop[k - 1]++;
				}
				System.out.println("Add 1 number to stack " + stackNumber);
				print("table", table);
				System.out.println();
			}

		

		static boolean add(int n) {
			//add a number into the n'th stack
			top[n - 1]++;
			// System.out.println("top[n-1]: " + top[n - 1]);
			if (top[n - 1] == base[n] + 1) {
				System.out.print("overflow happens ");
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
}
	
