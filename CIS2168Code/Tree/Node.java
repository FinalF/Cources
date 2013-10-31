

/**
 *
 * @author wang
 */
public class Node{
    private String s = null;
    private double data = 0;
    private Node left = null;
    private Node right = null;
    
    Node(){
        
    }
    
    Node(String s){
        this.s = s;
    }
    
    void setData(double a){
        data = a;
    }
    
    double getData(){
        return data;
    }
    
     void setLeft(Node n){
        this.left = n;
    }
    
     void setRight(Node n){
        this.right = n;
    }
    
    void setValue(String s){
        this.s = s;
    }
     String getValue(){
        return s;
    }
    
     Node getLeft(){
        return left;
    }
    
     Node getRight(){
        return right;
    }
}
