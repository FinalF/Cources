package Assignment4_TreeTraversal;

import java.util.Scanner;
import java.util.Stack;







public class Modified_Robson {

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
			modRobson(tree);
		} else {
			// empty tree
			System.out.println("An empty tree could not be traversed.");
		}
	}
	
	
	
	static void modRobson(Node root) {
		// Modified Robson Algorithm

		if (root == null) {
			return;
		}

		Node pred = null; // define pred, current node's predecessor
		Node succ = null; // define succ, current node's succcessor
		Node current = root; // define current node
		Node avail = null; // define available node to be put into stack

		Node stack = null; // define stack
		Node top = null; // define top

		boolean StopFlag = false; // define StopFlag
		boolean BackToRoot = false; // define BackToRoot flag
		boolean BackFromLeftFlag = false;// define BackFromLeftFlag
		boolean LeftFlag = false;// define LeftFlag

		boolean NoLeftSubtree;

		if (root.getLeft() == null) {
			NoLeftSubtree = true;
		} else {
			NoLeftSubtree = false;
		}

		System.out.println("ModRobson ");
		System.out.println("Stack N[RightPointer]->LeftPointer means N is a stack node.");
		System.out.println("Path [LeftPointer]N[RightPinter] means N is a path node.");

		while (!StopFlag) {
			// if stopflag == false
			while (current != null) {
				/* Step 1:
				 * If current node is not null,
				 *    and if the bottom node is a leaf, put it to avail stack
				 * go left to the bottom.
				 * */
				System.out.println();

				// print current node info, lt and rt
				visit(current);

				// print top node info
				printTop(top);

				// print stack nodes' info, lt and rt
				printStack(stack);

				// Print Path
				printPath(pred, stack, top);

				if (current.getLeft() == null && current.getRight() == null) {
					// if current node is leaf, record avail node
					if (avail == null)
						avail = current;
					else {
						current.setRight(avail);
						avail = current;
					}
				}

				// go down left
				succ = current.getLeft();
				current.setLeft(pred); //left subtree is being traversed, its left point is pointing to predp
				pred = current;
				current = succ;
			}

			// Step2: make flag to show going back to root

			BackToRoot = true;
			BackFromLeftFlag = true;

			succ = current;
			current = pred;
			pred = pred.getLeft();
			current.setLeft(succ);

			while (BackToRoot && !StopFlag) {
				/* Step3:(If going back to root)
				 * go back to root 
				 * if the node has rt, and it come back from left, we find a new top
				 * if current goes back to root, stop.
				 * */	
				if (current.getRight() != null && current != avail && BackFromLeftFlag) {
					// if there exists rt of cur, go right for only one further level
					succ = current.getRight();
					current.setRight(pred); //null lf, set its rt to its pred
					if (current.getLeft() != null) {//Find the most recent
						// find a new top
						if (top != null) {
							Node newstack = avail;
							avail = avail.getRight();
							newstack.setLeft(null);
							newstack.setRight(top);
							if (stack != null) {
								newstack.setLeft(stack);
							}
							stack = newstack;
						}
						top = current;
					}
					pred = current;
					current = succ;
					BackToRoot = false;
				}

				if (current == root) {
					// when back to root, traversal should stop
					StopFlag = true;
				}

				if (BackToRoot == true && pred != null) {
					Node prenode = null;
					LeftFlag = true;

					// get subnode
					if (pred == root) {
						prenode = null;
						if (NoLeftSubtree)
							LeftFlag = false;
					} else if (pred.getLeft() != null && top != pred) {
						prenode = pred.getLeft();
					} else {
						prenode = pred.getRight();
						LeftFlag = false;
					}

					if (top == pred) {
						// go back from right
						pred.setRight(current);
						BackFromLeftFlag = false;
						if (top != null) {

							if (stack != null) {
								top = stack.getRight();
								Node currentStack = stack;
								stack = stack.getLeft();
								currentStack.setLeft(null);
								currentStack.setRight(null);
							} else
								top = null;
						}
					} else if (LeftFlag) {
						// go back from left
						pred.setLeft(current);
						BackFromLeftFlag = true;
					} else {
						// go back from right
						pred.setRight(current);
						pred.setLeft(null);
						BackFromLeftFlag = false;

					}
					current = pred;
					pred = prenode;
				}

			}

		}
		// empty avail node		
		refreshAvail(avail);
		System.out.println();
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


	static void visit(Node n) {
		// print info, lt and rt of node n
		System.out.print("#" + n.getValue() + "\t");
		if (n.getLeft() != null) {
			System.out.print(" L: " + n.getLeft().getValue() + "\t\t");
		} 
		else {
			System.out.print(" L: NULL" + "\t");
		}

		if (n.getRight() != null) {
			System.out.print(" R: " + n.getRight().getValue() + "\t");
		} 
		else {
			System.out.print(" R: NULL");
		}
		System.out.println();
	}

	static void printTop(Node top) {
		// print top info
		if (top == null) {
			System.out.println("\t" + " Top: " + "NULL" + "\t");
		} 
		else {
			System.out.println("\t" + " Top: " + top.getValue() + "\t");
		}
	}

	static void printStack(Node stack) {
		// print info, lt and rt for every node in stack
		if (stack == null) {
			System.out.println("\t" + " Stack: " + "NULL");
			return;
		} 
		else {
			Node temp = stack;
			System.out.print("\t" + " Stack: ");
			while (temp != null) {
				System.out.print(temp.getValue() + "["+ temp.getRight().getValue() + "]->");
				temp = temp.getLeft();
			}
			System.out.println("NULL");
		}
	}

	static void printPath(Node pred, Node stack, Node top) {
		// print info, lt and rt for every node in path
		Node pathnode;
		Node stacknode;
		pathnode = pred;
		stacknode = stack;
		System.out.print("\t" + " Path: ");

		while (pathnode != null) {
			// pathnode exists
			if ((pathnode.getLeft() != null) && (pathnode.getRight() != null)) {
				// pathnode has both lt and rt

				System.out.print("-->[" + pathnode.getLeft().getValue() + "]"+ pathnode.getValue() + "["+ pathnode.getRight().getValue() + "]");
			}
			else if ((pathnode.getLeft() != null)&& (pathnode.getRight() == null)) {
				// pathnode has only lt
				System.out.print("-->[" + pathnode.getLeft().getValue() + "]"+ pathnode.getValue() + "[NULL]");
			} 
			else if ((pathnode.getLeft() == null)&& (pathnode.getRight() != null)) {
				// pathnode has only rt

				System.out.print("-->" + "[NULL]" + pathnode.getValue() + "["	+ pathnode.getRight().getValue() + "]");
			} 
			else {
				// pathnode doese have lt and rt

				System.out.print("-->" + "[NULL]" + pathnode.getValue()+ "[NULL]");
			}

			if (pathnode == top) {
				// when pathnode is top, go up by rt

				pathnode = pathnode.getRight();
							}
			else if ((stacknode != null)&& (pathnode == stacknode.getRight())) {
				// when pathnode is stacknode, go up by rt

				pathnode = pathnode.getRight();
				stacknode = stacknode.getLeft();
			} 
			else {
				// when pathnode is common node

				if ((pathnode.getLeft() == null) && (pathnode.getValue() != 1)) {
					// pathnode has only rt, go up from rt

					pathnode = pathnode.getRight();
				} 
				else {
					// //pathnode has lt, go up from lt

					pathnode = pathnode.getLeft();
				}
			}
		}
		System.out.println();

	}
	static void refreshAvail(Node avail){
		// Empty avail
		while (avail != null) {
			Node oldav = avail;
			avail = avail.getRight();
			oldav.setLeft(null);
			oldav.setRight(null);
		}
	}
	
	
 public static class Node{

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

}
