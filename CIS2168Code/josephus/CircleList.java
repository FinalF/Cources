/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package josephus;

import java.util.ArrayList;

/**
 *
 * @author wang
 */
public class CircleList {
    
    
    Node head = null;
    Node tail = null;
    Node current = head;
//    ArrayList<Node> list = null;
    
    CircleList(){
        
    }
    
    
    
    
    
    void InitialList(int n){
//        this.list = new ArrayList<Node>(n);       
        for(int i = 1; i <= n; i++){
//                System.out.println(i+"th node added");
            Node node = new Node(i);
//            list.add(node);
            if(i==1){
                 head = node;
                 head.before = node;
                 head.next = node;
            }else if(i==n){
                 tail = node;
                 tail.next = head;
                 current.next = node;
                 node.before = current;
                 head.before = node;
            }else{              
                 AddNode(node);
            }
            current = node;
        }
        current = head;
    }
    
    void AddNode(Node x){      

        x.before = current;
        x.next = current.next;
        current.next.before = x;
        current.next = x;
    }
 
    void DeleteNode(Node n){
         System.out.println(n.num+" has been chosen");
         n.before.next = n.next;
         n.next.before = n.before;
         current = n.next;
//         list.remove(n);

    }
    
    boolean hasNext(){
        if(current!=null){
            return true;
        }else{
            return false;
        }
    }
    
    Node next(){
        Node tmp =  current;
        current = current.next;
        return tmp;
    }
      
    
}
