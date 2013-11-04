

/**
 * 914978493
 * yanling chen
 */
import java.util.Stack;
import java.util.StringTokenizer;
public class Tree {
    private static final String OPERATORS = "+-*/()";
    public static void main(String[] args) {
        String infix="(((3.2*25)-(4.3/2))+9.3)";
        System.out.println("The expression we are processing: (((3.2*25)-(4.3/2))+9.3)\n-------");
        Stack<Node> stack = new Stack<Node>();
        StringBuilder number = new StringBuilder();
        StringTokenizer infixTokens = new StringTokenizer(infix, OPERATORS+" ", true);

         while (infixTokens.hasMoreTokens()) {
                String nextToken = infixTokens.nextToken();
                char firstChar = nextToken.charAt(0);                    
                if (Character.isJavaIdentifierStart(firstChar)
                        || Character.isDigit(firstChar)) {
                           number.append(nextToken);
                    Node root = stack.peek();
                    if(root.getLeft()==null){
                        root.setLeft(new Node(number.toString()));
                        root.getLeft().setData(toDouble(number.toString()));
                    }else{
                        root.setRight(new Node(number.toString()));    
                        root.getRight().setData(toDouble(number.toString())); 
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
                        n.setData(cal(n.getLeft().getData(),n.getValue(),n.getRight().getData())); 
                        System.out.println("Get one unit tree structure (Exp):"
                                            +n.getLeft().getData()
                                            +n.getValue()
                                            +n.getRight().getData()
                                            +" = "
                                            +n.getData());
                        //System.out.println("The value of this node: "+n.getData());
                        if(stack.isEmpty()){
                            System.out.println("-------\nThe value of the formula is: "+n.getData());
                            System.out.println("\n=======\nThe preorder traversal: ");
                            preorder(n);                            
                            System.out.println("\nThe inorder traversal: ");
                            inorder(n);
                            System.out.println("\nThe postorder traversal: ");
                            postorder(n);  
                            System.out.println("\n=======");     
                        }
                    }
                    else{
                        Node n = stack.peek();
                        n.setValue(Character.toString(firstChar));
                    }
                }
         }	

}

static double cal(double a, String op,  double b){
    double result = 0;
    if(op.equals("+")){
        result = a + b;
    }else if(op.equals("-")){
        result = a - b;
    }else if(op.equals("*")){
        result = a * b;
    }else if(op.equals("/")){
        result = a / b;
    }
    return result;
        
}
static double toDouble(String s){
    return Double.valueOf(s);
}
    
    
private static boolean isOperator(char ch) {
        return OPERATORS.indexOf(ch) != -1;
}
    
    
static void preorder(Node n){
  if (n != null)
  {
   System.out.print(n.getValue()+ " ");
   preorder(n.getLeft());
   preorder(n.getRight());
  }
 }
    
    
static void inorder(Node n){
  if (n != null)
  {
   inorder(n.getLeft());
   System.out.print(n.getValue()+ " ");
   inorder(n.getRight());
  }
 }

static void postorder(Node n){
  if (n != null)
  {
   postorder(n.getLeft());
   postorder(n.getRight());
   System.out.print(n.getValue()+ " ");
  }
 }

}
