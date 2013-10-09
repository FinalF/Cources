package Assignment2_mss4_product;

import java.util.Scanner;

public class mss4_product {

	/**
	 *
	 * @author yufeng
	 * Programming Tech Assignment 2-2: maximum subsequence sproduct problem
	 * based on mss4:Keep a max and min value for current subsequences
	 * and they might become the maxproduct after being multiplied by the next number
	 * 2013/02/04
	 */
	static int N;
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Please input the length of number sequence:");
		Scanner in = new Scanner(System.in);
		N = in.nextInt();
		int a[] = new int[N];  //the number sequence
		System.out.println("Please input the number in the sequence: ");
		for (int i=0; i<N; i++){
//			System.out.println("Please input the #" + (i+1) +" number in the sequence: ");
			a[i] = in.nextInt();
			if(i==N-1){
				System.out.println(N+ " numbers have been read, input is done!");
			}
		}

		int result = modifiedmss4(a,1,N);
		System.out.println("The maxproduct of subsequence is: " + result);

		
		
	}

	static int modifiedmss4(int a[],int p1,int p2){

		
		int maxPositive = a[0];
		int minNegative = a[0];
		int max = a[0];
		for(int i=1; i<a.length;i++){
			
			int temp = minNegative;
			minNegative = min(a[i],min(maxPositive*a[i],minNegative*a[i]));
			maxPositive = max(a[i],max(maxPositive*a[i],temp*a[i]));

			if(maxPositive>max){
				max = maxPositive;
			}
		}
		if(max<0){
			max = 0;
		}
		return  max;
	}

	 static int max(int a, int b) {
		// TODO Auto-generated method stub
		 int max = a;
		 if(b>a){
			 max = b;
		 }
		return max;
	}
	
	 static int min(int a, int b) {
		// TODO Auto-generated method stub
		 int min = a;
		 if(b<a){
			 min = b;
		 }
		return min;
	}
	
	
}
