/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wang
 */
public class TreeTest {
    public static void main(String args[]){
        Node root = new Node("b");
        BinarySearchTree tree = new BinarySearchTree(root);
        tree.insert(root, new Node("d"));
        tree.displayTree();
        
        tree.insert(root, new Node("f"));
        tree.displayTree();
//        
        tree.insert(root, new Node("e"));
        tree.displayTree();
////        
        tree.insert(root, new Node("a"));
        tree.displayTree();

        
        tree.delete(null,root, new Node("f"));
        tree.displayTree();
        
        tree.delete(null,root, new Node("d"));
        tree.displayTree();
        
        
        
        preorder(root);
        
       
    }
  
    
    
            
static void preorder(Node n){
  if (n != null)
  {
   System.out.print(n.s+ " ");
   preorder(n.left);
   preorder(n.right);
  }
 }
}
