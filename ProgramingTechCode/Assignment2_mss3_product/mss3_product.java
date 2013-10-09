package Assignment2_mss3_product;

import java.util.Scanner;

public class mss3_product {

/**
 *
 * @author yufeng
 * Programming Tech Assignment 2-4: maximum subsequence product problem
 * To divide the sequence into two parts: right and left, repeating this operation
 * until there is only one left in the subsequence
 * 2013/02/03
 */
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
		
		int maxsum = mss3product(a,1,n);
		System.out.println("The maxproduct of subsequence is: " + maxsum);
	}
	
	
	
	
	
	
	
	static int mss3product(int a[], int p1, int p2){
		int maxsum;
		if (p1==p2){
			if(a[p1-1] > 0){
				maxsum = a[p1-1];
			}
			else{
				maxsum = 0;
			}
		}
		else{
			int m = (p1+p2)/2; //find the "middle" point
			int L = mss3product(a,p1,m);    //recursion
			int R = mss3product(a,m+1,p2);  //resursion
			int sumlt = 1;	
			int sumrt = 1;
			int SL = 0;		//maxsum of left subsequence
			int SR = 0;		//maxsum of right subsequence
			int SLNegative = 0;
			int SRNegative = 0;
			for (int i = m; i>=p1;i--){		//find out the maxsum of left subsequence
				sumlt = sumlt * a[i-1];
				if(sumlt>SL){
					SL = sumlt;
				}
				if(sumlt<0 && Math.abs(sumlt) > Math.abs(SLNegative)){
					 SLNegative = sumlt;
				}
			}
			for (int j = m+1; j<=p2; j++){	//find out the maxsum of right subsequence
				sumrt = sumrt * a[j-1];
				if(sumrt>SR){
					SR = sumrt;
				}
				if(sumrt<0 && Math.abs(sumrt) > Math.abs(SRNegative)){
					 SRNegative = sumrt;
				}
			}
			maxsum = max(max(L,R),max(SL*SR, SRNegative*SLNegative)); //the maxmum subsequence result
		}
		
		
		

		return maxsum;
		
	}
	
	
	static int max(int x,int y){
		int maxnum = x;
		if(y>x){
			maxnum = y;
				}
			
		return maxnum;

	}

}
