

/**
 *
 * @author wang
 */
public class BinarySearchTree {
    Node root = null;
    public BinarySearchTree(Node root) {
        this.root = root;
    }
   
    
    
    
    void insert(Node root, Node newNode){
    if (root.s.compareTo(newNode.s) > 0) {   
        if (root.left == null)
            root.left = newNode;
        else
            insert(root.left, newNode);
    } else if(root.s.compareTo(newNode.s) < 0){//root < new node
        if (root.right == null)
            root.right = newNode;
        else
            insert(root.right, newNode);
    }else{
        System.out.println("This node already exists");
    }

    }

    void delete(Node parent, Node root, Node oldNode){
        if(root == null){
            System.out.println("The tree is empty");
        }else if(root.s.compareTo(oldNode.s) < 0) {   //root < old node
            delete(root,root.right, oldNode);
        }else if(root.s.compareTo(oldNode.s) > 0){
            delete(root,root.left, oldNode);
        }else{         
                if(root.left == null && root.right == null){//leaf node
                    /*No child, Set the parent of local root to null*/
                    if(parent.left==root){
                        parent.left = null;
                    }else{
                        parent.right = null;
                    }  

                }else if(root.left != null && root.right == null){                    
                    /*One child, Set the parent of local root to null*/   
                    if(parent.left==root){
                        parent.left = root.left;
                    }else{
                        parent.right = root.left;
                    }                  
                }else if(root.left == null && root.right != null){
                    /*One child, Set the parent of local root to null*/
                    if(parent.left==root){
                        parent.left = root.right;
                    }else{
                        parent.right = root.right;
                    }
                }else{
                    if(root.left.right == null){
                    /*if the left child has no right child, set the parent 
                     * of local root reference the left child*/
                        if(parent.left==root){
                            parent.left = root.left;
                        }else{
                            parent.right = root.left;
                        }
                    }else{
                    /*Find the right most node in the right substree of left child
                     copy its dat into the local root's data and remove it by setting 
                     its parent to reference its left child*/   
                    Node rightMostParent = findRightMostParent(root.left.right);
                         root.s = rightMostParent.right.s;                         
                         rightMostParent.right = rightMostParent.right.left;
                    }
                }
        }
    }
    
    
    Node findRightMostParent(Node root){
        Node rightMostParent = null;
        if(root.right != null && root.right.right != null){
            rightMostParent = findRightMostParent(root.right);
        }else{
            rightMostParent = root;
        }
        return rightMostParent;
    }
    
    void displayTree(){
        StringBuilder sb = new StringBuilder();
        inOrderTraversal(root,1,sb);
        System.out.println("---------Current Binary search tree is:-------------");
        System.out.print(sb.toString());
        System.out.println("----------------------------------------------------\n");
    }
    
    private void inOrderTraversal(Node root,int depth, StringBuilder sb){

        if(root == null){
            for(int i = 1; i < depth; i++){
                sb.append(" ");
            }
            sb.append("\n");           
        }else{

            inOrderTraversal(root.left,depth+1,sb);
                for(int i = 1; i < depth; i++){
                    sb.append(" ");
                }
            sb.append(root.s);
            sb.append("\n");
            
            inOrderTraversal(root.right,depth+1,sb);
            
        }
    }
    
}
