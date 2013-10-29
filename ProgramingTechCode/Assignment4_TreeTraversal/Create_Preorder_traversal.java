package Assignment4_TreeTraversal;

import java.util.Scanner;
import java.util.Stack;






public class Create_Preorder_traversal {

	/**
	 * Yufeng 
	 */
	static Scanner scan;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Node tree = treeCreate();
//		System.out.println(tree.getValue());
		if (tree != null) {
			// traverse non-empty tree
			preorderTraversal(tree);
		} else {
			// empty tree
			System.out.println("An empty tree could not be traversed.");
		}
	}
		
		
		 static class Node{

			    int nodenumber;         
			    Node left;      //define the left pointer
			    Node right;		//define the right pointer
			    Node(){
			    	
			    }
			    	Node(int n,Node leftsub, Node rightsub){
			        nodenumber = n;
			        left = null;
			        right = null;
			        }
			        
			        void setLeft(Node subleft){
			        	left = subleft;
			        }
			        void setRight(Node subright){
			        	right = subright;
			        }
			        
			        Node getLeft(){
			        	return left;
			        }
			        Node getRight(){
			        	return right;
			        }
			        int getValue(){
			        	return nodenumber;
			        }
			}
		

	static void preorderTraversal(Node root) {
				// Preorder Traversal
				System.out.println("Preorder Traversal");
				Node p;
				Node q;
				Node rtptr;
				p = root;
				Stack<Node> s = new Stack<Node>();

				while ((p != null) || (!s.empty())) {   //visit from the root
					if (p != null) {
						visit(p);
						s.push(p);  //put nodes into the stack
						if (p.getLeft() != null) {  //visit left subnodes
							p = p.getLeft();
						} 
						else {
							p = p.getRight();
						}
					} 
					else {
						do { 
							q = s.pop();  
							if (!s.empty()) {
								rtptr = s.peek().getRight();
							}
							else {
								rtptr = null;
							}
						} while ((!s.empty()) && (q == rtptr));
						p = rtptr;
					}

				}
				System.out.println();
			}

	static void visit(Node n) {
				// print info, lt and rt of node n
				System.out.print(n.getValue() + "\t");
				if (n.getLeft() != null) {
					System.out.print(" L: " + n.getLeft().getValue() + "\t\t");
				} else {
					System.out.print(" L: NULL" + "\t");
				}

				if (n.getRight() != null) {
					System.out.print(" R: " + n.getRight().getValue() + "\t");
				} else {
					System.out.print(" R: NULL");
				}
				System.out.println();
			}

			

static Node treeCreate() {
	// creatTree using input infomation
	// return the root of new tree

	System.out.println("Input 1 for a non-empty tree; input 0 for a empty tree.");
	Stack<Node> s = new Stack<Node>();
	int nodeNumber = 1;
	int flag = 0;

	Node root = new Node();
	Node pred = new Node();

	int lastinput;
	int currentinput;

	scan = new Scanner(System.in);

	if (scan.nextInt() == 0) {
		System.out.println("An empty tree has been created.");
		root = null;
	} 
	else {
		System.out.println("Please input node information use 2-number pairs like \"11 00 01 10\" for every entry.");

		int input = scan.nextInt();
		lastinput = input;

		// create root Node
		//put nodes who have right subtree into the stack for future traversal
		if (input == 11) {
			Node rootnode = new Node(nodeNumber, null, null);
			root = rootnode;
			pred = rootnode;
			s.push(rootnode);

		} 
		else if (input == 10) {
			Node rootnode = new Node(nodeNumber, null, null);
			root = rootnode;
			pred = rootnode;
		}
		else if (input == 01) {
			Node rootnode = new Node(nodeNumber, null, null);
			root = rootnode;
			pred = rootnode;
			s.push(rootnode);
		}
		else if (input == 00) {
			Node rootnode = new Node(nodeNumber, null, null);
			root = rootnode;
			System.out.println("A single node tree has been created.");
			System.out.println();
			return root;
		}
		else {
			System.out.println("Wrong input.");
		}

		while ((!s.empty()) || (flag == 0)) {

//			System.out.println("!s.empty() = " + !s.empty());
			nodeNumber++;
			currentinput = scan.nextInt(); // waiting for next input

			if ((lastinput == 11) | (lastinput == 10)) {
				Node newnode = new Node(nodeNumber, null, null);
				pred.setLeft(newnode);
				pred = newnode;
				if ((currentinput == 01) | (currentinput == 11)) {
					s.push(newnode);
				}

			} 
			else if ((lastinput == 01) | (lastinput == 00)) {
				Node newnode = new Node(nodeNumber, null, null);
				s.pop().setRight(newnode);
				pred = newnode;
				if ((currentinput == 01) | (currentinput == 11)) {
					s.push(newnode);
				}
			}

			if ((currentinput == 01) | (currentinput == 00)) {
				flag = 1;
			} 
			else {
				flag = 0;
			}

			lastinput = currentinput;
		}
		System.out.println("A non-empty binary tree has been created.");
		System.out.println();
	}

	return root;

}
}

