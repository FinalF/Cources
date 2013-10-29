package Assignment2_mss4;

import java.util.Scanner;

public class mss4 {

	/**
	 *
	 * @author  yufeng
	 * Programming Tech Assignment 2-2: maximum subsequence sum problem
	 * based on mss4:and maxsum cannot include a negative initial segment
	 * 2013/02/04
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Please input the length of number sequence:");
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int a[] = new int[n];  //the number sequence
		System.out.println("Please input the number in the sequence: ");
		for (int i=0; i<n; i++){
//			System.out.println("Please input the #" + (i+1) +" number in the sequence: ");
			a[i] = in.nextInt();
			if(i==n-1){
				System.out.println(n+ " numbers have been read, input is done!");
			}
		}

		int result[] = modifiedmss4(a,1,n);
		System.out.println("The maxsum of subsequence is: " + result[0]);
		System.out.println("The first position of this subsequence is :" + result[1]);
		System.out.println("The last position of this subsequence is :" + result[2]);
		
		
		
	}

	static int[] modifiedmss4(int a[],int p1,int p2){
		int result[] = new int[3];
		int maxsum = 0;
		int sum=0;
		int i=p1;
		int first = p1;  //first and last position of the maximum subsequence
		int last = p1; 
		while(i<=p2){
			sum = sum+a[i-1];
			if(sum>maxsum){
				maxsum = sum;
				last = i ;
			}
			else if(sum<0){
				sum = 0;
				if (i<p2){
				first = i + 1;
				last = first;
				}
			}
			
//			System.out.println(maxsum);
			i++;
		}
		result[0] = maxsum;
		result[1] = first;
		result[2] = last;
		return result;
		
		
		
	}
	
	
	
	
}
