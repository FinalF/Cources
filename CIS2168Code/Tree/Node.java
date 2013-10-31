

/**
 *
 * @author wang
 */
public class Node{
    String s = null;
    Node left = null;
    Node right = null;
    
    Node(){
        
    }
    
    Node(String s){
        this.s = s;
    }
    
    public void setLeft(Node n){
        this.left = n;
    }
    
    public void setRight(Node n){
        this.right = n;
    }
    
    public void setValue(String s){
        this.s = s;
    }
    public String getValue(){
        return s;
    }
    
    public Node getLeft(){
        return left;
    }
    
    public Node getRight(){
        return right;
    }
}
