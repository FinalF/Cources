/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wang
 */
import java.util.Stack;
import java.util.StringTokenizer;
public class Tree {
    private static final String OPERATORS = "+-*/()";
    public static void main(String[] args) {
        String infix="(((3.2*25)-(4.3/2))+9.3)";
        Stack<Node> stack = new Stack<Node>();
        StringBuilder number = new StringBuilder();
        StringTokenizer infixTokens = new StringTokenizer(infix, OPERATORS+" ", true);

         while (infixTokens.hasMoreTokens()) {
                String nextToken = infixTokens.nextToken();
                char firstChar = nextToken.charAt(0);                    
                if (Character.isJavaIdentifierStart(firstChar)
                        || Character.isDigit(firstChar)) {
                           number.append(nextToken);
        //		               number.append(' ');
//                    System.out.println("It's a number "+number);
                    Node root = stack.peek();
                    if(root.getLeft()==null){
                        root.setLeft(new Node(number.toString()));
                    }else{
                        root.setRight(new Node(number.toString()));                       
                    }
                    number.setLength(0);
                       
                }else if (isOperator(firstChar)) {

                    if(firstChar=='('){
                        /*Next is the root*/
                        Node n = new Node();
                        if(!stack.isEmpty()){
                            if(stack.peek().getLeft()==null){
                                stack.peek().setLeft(n);
                            }
                            else{
                                stack.peek().setRight(n);
                            }
                        }stack.add(n);
                    }
                    else if(firstChar==')'){
                        /*this is the end of a node*/
                        Node n = stack.pop();
                        if(stack.isEmpty()){
                            System.out.println("\nThe preorder traversal: ");
                            preorder(n);                            
                            System.out.println("\nThe inorder traversal: ");
                            inorder(n);
                            System.out.println("\nThe postorder traversal: ");
                            postorder(n);                            
                        }
                    }
                    else{
                        Node n = stack.peek();
                        n.setValue(Character.toString(firstChar));
//                        System.out.println("Set value as: "+n.getValue());
                    }
//                  System.out.println("operator:"+firstChar);
                }
         }	

}
	
    private static boolean isOperator(char ch) {
            return OPERATORS.indexOf(ch) != -1;
    }
    
    
public static void preorder(Node n){
  if (n != null)
  {
   System.out.print(n.getValue()+ " ");
   inorder(n.getLeft());
   inorder(n.getRight());
  }
 }
    
    
public static void inorder(Node n){
  if (n != null)
  {
   inorder(n.getLeft());
   System.out.print(n.getValue()+ " ");
   inorder(n.getRight());
  }
 }

public static void postorder(Node n){
  if (n != null)
  {
   inorder(n.getLeft());
   inorder(n.getRight());
   System.out.print(n.getValue()+ " ");
  }
 }

}
