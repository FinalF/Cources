package Assignment5_ParenthesisBinaryTree;

import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class LexicographicTreeOrder {
	/**
	 * Yufeng 
	 */
	static int COUNT = 0;
	private static Scanner scanner;
	static int counter = 0;
	public static boolean stop = false;
	 public static int n ;
	public static int[] leftArray;
	public static int[] rightArray;
	public static int[] string;
	public static Stack<Integer> s = new Stack<Integer>();
	
	public static void main(String[] args) throws IOException {
				// Binary Tree Generation
				System.out.println("Please input n (n is the # of nodes): ");
				scanner = new Scanner(System.in);
				 n = scanner.nextInt();
				System.out.println("Binary Trees in lexicographic order:");
				Lexicograph(n);
					System.out.println("There are totally " + COUNT
							+ " diffrent binary threes of " + n + " nodes.");
				
		}
	

	public static void Lexicograph(int nodeNumber) {

		// Initialization of Array

		string = new int[nodeNumber * 2];
		for (int i = 0; i < nodeNumber * 2; i = i + 2) {
			string[i] = 1;
			if (i + 1 < nodeNumber * 2) {
				string[i + 1] = 0;
			}
		}
		// Initialization of L/R
		leftArray = new int[nodeNumber];
		rightArray = new int[nodeNumber];
		int index = 0;
		for (index = 0; index < nodeNumber; index++) {
			leftArray[index] = 0;
			rightArray[index] = index + 2;
		}
		rightArray[index - 1] = 0;

		// Print the first array and LR out.
		System.out.print("#" + (++COUNT) + ": ");
		print(string);
		printLR(leftArray, rightArray);

		while (!stop) {
			binaryTreeGen(n);
		}
	}
		
	public static void  binaryTreeGen(int node){

		if (inPosition(rightArray, node)) {
			// if node n is in it's right position,move it to the left
			moveLeft(leftArray, rightArray, node);		
			System.out.println("Node " + node + " move left");
//			if(!s.empty()){
			int nextParent = findNextParent(leftArray,rightArray,node-1);
			System.out.println("The parent is " + nextParent);
			if(node + 1 <= n){
			moveUpRight(leftArray,rightArray, nextParent, node+1);
//			s.clear();
			}	
			findSuccessor(string);
			printLR(leftArray, rightArray);	
		} 			
		else if (inPosition(leftArray, node)) {
//			s.push(n); //store the current node

			if(node>2){
			binaryTreeGen(node-1);
			}else{
				stop=true;
			}
		}			
		else{
			/* node n is theright sub of other nodes
			 * then find that node, find next available position
			 * put the suc of node n to the right sub of that node
			 */		
			int  nodePosition,nodeparent,nodepre;
			nodePosition = getIndex(rightArray, node);
			nodeparent = nodePosition + 1;//the parent node of current node 
			nodepre = findPre(node);			
			goDown( leftArray,rightArray,string,node, nodepre, nodeparent);			
		}

	}
	
	

	
	public static void goDown(int leftArray[],int rightArray[],int string[],int node,int nodepre,int nodeparent){
		int i = leftArray[nodeparent-1]; 
//		Stack st = new Stack();
		while(i < nodepre+1){
		if(rightArray[i-1] == 0){
			nodemoveDownLeft(leftArray,rightArray,i,nodeparent,node,n);
//			nodeparent = i;
			System.out.println("Move node " + node+ " from " + nodeparent + " to " +i);
			findSuccessor(string);
			printLR(leftArray, rightArray);
			break;
//			i = leftArray[i-1];
		}
		else if(rightArray[i - 1]!=0){
			i = rightArray[i - 1];
//			st.push(i);
		}
		else{
			i = leftArray[i-1];
		}
	}
//		lastNodemoveDownLeft(leftArray,rightArray,nodepre,nodenextparent,n);						
//		findSuccessor(string);
//		printLR(leftArray, rightArray);
	}
	
	
	
	
	public static void findSuccessor(int[] array) {

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
		System.out.print("\n#" + (++COUNT) + ": ");
		print(array);

	}

	public static void print(int array[]) {
		int i = 0;
		while (i <= array.length - 1) {
			System.out.print(array[i]);
			i++;
		}
		System.out.println();
	}

	public static void printLR(int left[], int right[]) {
		int i = 1;
		while (i <= left.length) {
			System.out.println("   L[" + i + "]=" + left[i - 1] + "   R[" + i
					+ "]=" + right[i - 1]);

			i++;
		}
		System.out.println();
	}

	//index of the pointer
	public static int getIndex(int[] array, int k) {
		int index = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i] == k) {
				index = i;
			}
		}
		return index;
	}

public static int findNextParent(int leftArray[],int rightArray[],int startnode){

	int i = getIndex(leftArray,startnode);
	int j = getIndex(rightArray,startnode);
	int temp = startnode;
	while((i != -1 || j != -1) && temp != 1){
		if(j!=-1){
		temp = getIndex(rightArray,temp) + 1;
		i = getIndex(leftArray,temp);
		j = getIndex(rightArray,temp);
		}else if(i!=-1){
		temp = getIndex(leftArray,temp) + 1;	
		if(rightArray[temp-1]==0){
			startnode = temp;
		}
		i = getIndex(leftArray,temp);
		j = getIndex(rightArray,temp);
		}
	}

	return startnode;
}
	
	public static void nodemoveDownLeft(int leftArray[],int rightArray[],int i, int nodeParent, int node, int n){
		rightArray[i-1] = node;
		rightArray[nodeParent - 1] = 0;
		int nodeNextParent = findNextParent(leftArray,rightArray,nodeParent);
		if(node+1<=n){
		leftArray[node-1] = 0;
		moveUpRight(leftArray,rightArray,nodeNextParent,node+1);
		}
	}

	public static void moveLeft(int leftArray[], int rightArray[], int n) {
		rightArray[n - 2] = 0;
		leftArray[n - 2] = n;
		leftArray[n-1] = 0;
	}
	
	public static void moveRight(int leftArray[], int rightArray[], int n) {
		rightArray[n - 2] = n;
		leftArray[n - 2] = 0;
	}
	
	public static void moveUpRight(int leftArray[], int rightArray[], int parent, int node){
		rightArray[parent - 1] = node;
		if(leftArray[parent] == node){
			leftArray[parent] = 0;
		}
		for(int i = n; i>=node+1; i--){
			moveRight(leftArray,rightArray,i);
		}
	}
	
//	public static void	moveUpNextParent(int leftArray[], int rightArray[], int nodenextparent, int node,int nodesucc){
//		rightArray[nodenextparent - 1] = nodesucc;
//		leftArray[node - 1] = 0;
//		//rightArray[node-1] = 0;
//		if(node+1<n){
//		for(int i = nodesucc+1; i<=n; i++){
//			moveRight(leftArray,rightArray,i);
//		}
//		}
//	}
	
	


	
	//check whether node n is the right/left sub of its pre
	public static boolean inPosition(int[] string, int n) {
		if (string[n - 2] == n) {
			return true;
		} else {
			return false;
		}
	}

	public static int findSucc(int n){
		return n+1;
	}
	
	public static int findPre(int n){
		return n-1;
	}
}





