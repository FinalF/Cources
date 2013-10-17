package Assignment6;

import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class StralerNoAndPruningNo {
	static int[] array;
	static int numRecord[] = new int[7];
	static int numRecord2[] = new int[7];
	static int sum = 0;
	public static void main(String[] args) throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.println("Please input the number of nodes: ");
		int n = in.nextInt();	
		System.out.println("Strahler Number\t #1\t #2\t #3\t #4\t #5\t #6\t Total");
		for (int i = 1; i <= n; i++) {
			System.out.print("   node=" + i + " :\t");
			StrahlerCal(i);
			sum = 0;

			for (int j = 1; j < numRecord.length; j++) {
				System.out.print(" " + numRecord[j] + "\t");

				sum = sum + numRecord[j];
			}
			System.out.print(sum + "\t");
			System.out.println();

			for (int k = 1; k < numRecord.length; k++) {
				numRecord[k] = 0;
			}
		}
		System.out.println();
		System.out.println("Pruning Number\t #1\t #2\t #3\t #4\t #5\t #6\t Total");
		for (int i = 1; i <= n; i++) {
			System.out.print("   node=" + i + " :\t");
			PruningCal(i);
			sum = 0;

			for (int j = 1; j < numRecord2.length; j++) {
				System.out.print(" " + numRecord2[j] + "\t");

				sum = sum + numRecord2[j];
			}
			System.out.print(sum + "\t");
			System.out.println();

			for (int k = 1; k < numRecord2.length; k++) {
				numRecord2[k] = 0;
			}
		}

		
		
	}
	
//Initialize the array,do the calculation
public static void StrahlerCal(int n){
		array = new int[n * 2];
		for (int i = 0; i < n * 2; i = i + 2) {
			array[i] = 1;
			if (i + 1 < n * 2) {
				array[i + 1] = 0;
			}
		}
		StratreeGen(array); // calculate the first array's sequence and LR array

		while (hasSuccessor(array)) {
			// next array exists
			StratreeGen(successorFind(array)); 										
		}
	}


public static void PruningCal(int n){
	array = new int[n * 2];
	for (int i = 0; i < n * 2; i = i + 2) {
		array[i] = 1;
		if (i + 1 < n * 2) {
			array[i + 1] = 0;
		}
	}
	pruningNumber(array); // calculate the first array's sequence and LR array

	while (hasSuccessor(array)) {
		// next array exists
		pruningNumber(successorFind(array)); 										
	}
}




	
public static void StratreeGen(int[] array) {
		int[] leftArray = new int[array.length / 2];
		int[] rightArray = new int[array.length / 2];
		int[] signal = new int[array.length / 2];
		int count = 0;
		Stack<Integer> s = new Stack<Integer>();
		int index = 0;
		while (index < array.length) {
			if (array[index] == 1) {
				if (count > 0) {
					int temp = s.peek();
					if (signal[temp - 1] == 0) {
						leftArray[temp - 1] = count + 1;
						signal[temp - 1] = 1;
					} else {
						rightArray[temp - 1] = count + 1;
						signal[temp - 1] = 0;
						s.pop();
					}
				}
				s.push(++count); //store the current node we are processing
			} 
			else if (array[index] == 0) {
				if (count > 0) {
					int temp = s.peek();
					if (signal[temp - 1] == 0) {
						leftArray[temp - 1] = 0;
						signal[temp - 1] = 1;
					} else {
						rightArray[temp - 1] = 0;
						signal[temp - 1] = 0;
						s.pop();
					}
				}
			} 
			index++;
		}
		int[] strNumberArray = new int[leftArray.length];
		for (int i = 0; i < strNumberArray.length; i++) {
			strNumberArray[i] = 0;
		}
		numRecord[strahlerNum(leftArray, rightArray, strNumberArray, 1)]++;
	}
	
	
	public static int[] successorFind(int[] array) {
		/**
		 * Find the successor according to the file from the class
		 * find out the specific parenthesis pattern, and replace it
		 */
		int y = 0;
		int x = -1;
		int index;
		for (int i = array.length - 1; i >= 0; i--) {
			if (array[i] == 0) {
				y++;
			} else {
				break;
			}
		}
		for (int i = array.length - 1 - y; i >= 0; i--) {
			if (array[i] == 1) {
				x++;
			} else {
				break;
			}
		}
		index = array.length - 1 - y - (x + 1);
		array[index++] = 1;
		for (int i = 1; i <= y + 1 - x; i++) {
			array[index++] = 0;
		}
		for (int i = index; i <= array.length - 1; i = i + 2) {
			array[i] = 1;
			if (i + 1 < array.length) {
				array[i + 1] = 0;
			}
		}

		return array;
	}
	
	public static boolean hasSuccessor(int[] array) {
		/**
		 *The first array should be like {0101010101……01}
		 *the last one is like {11111……00000}	
		 */
		for (int index = 0; index <= array.length / 2 - 1; index++) {
			if (array[index] == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static int strahlerNum(int[] L, int[] R, int[] straArray, int n) {
		if ((L[n-1] == 0) && (R[n-1] == 0))//the straNum of leaf is 1
			straArray[n-1] = 1;
		else if ((L[n-1] != 0) && (R[n-1] == 0)) {
			straArray[n-1] = strahlerNum(L, R, straArray, L[n-1]);
		} else if ((L[n-1] == 0) && (R[n-1] != 0)) {
			straArray[n-1] = strahlerNum(L, R, straArray, R[n-1]);
		} else {
			if (strahlerNum(L, R, straArray, L[n-1]) == strahlerNum(L,R, straArray, R[n-1]))
				straArray[n-1] = strahlerNum(L, R, straArray, L[n-1]) + 1;
			else
				straArray[n-1] = Math.max(
						strahlerNum(L, R, straArray, L[n-1]),
						strahlerNum(L, R, straArray, R[n-1]));
		}
		return straArray[n-1];
	}
	
	
	public static void pruningNumber(int[] array) {
		// Pruning Number
		Stack<Integer> s = new Stack<Integer>(); //record the pruning number(Assignmend Strahler Number) of every node
		
		int index = 0;
		int treeCut = 0; //used to record the terminate point while we convert binary tree to forest
		int pruNum = 1;

		while (index < array.length) {
			if (array[index] == 1) {         // a node has been read
				treeCut++;  // record the number of 1 has been read, in the forest, every tree has equivalent number of 1s and 0s
				s.push(-1);  // reserve the position for this node, its pruning number is currently unknown.
			} else if (array[index] == 0) { 
				treeCut--;
				if (s.peek() == -1) { // if top of stack is -1 , it means this 0 is the node's left null suc
					s.pop();
					s.push(1);  // so the node we read before has Pruning# of 1 (the right suc will be connected to this node's parent,
								//	therefore, this node becomes a leaf in the tree forests) 
				} else { // if there are other sucs, then calculate the Pruning# for their predecessor
					int max = 0; // max to record the max pruning# of al sucs
					int count = 0; // record how many times the max value occurs
									// if more than one node has same max number, the pre should have max+1
									// if there's only one max, the pre has max
					int newpop;
					while (s.peek() != -1) { 
						newpop = s.pop();
						if (newpop > max) {
							max = newpop;
							count = 1;
						} else if (newpop == max) {
							count++;
						}
					}
					s.pop();
					if (count >= 2) {
						s.push(max + 1);
					} else {
						s.push(max);
					}
				}
				if (treeCut == 0) {
					pruNum = Math.max(pruNum, s.pop()); // the forest's Pruning# is the largest P# of the trees
					if (!s.empty()) {
						System.out.println("Array Wrong.");
					}
				}

			} else {
				System.out
						.println("Invalid array: the input array contains other number than 0 and 1.");
			}
			index++;
		}
		numRecord2[pruNum]++;
	}
}
