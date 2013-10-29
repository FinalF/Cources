/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package josephus;

/**
 *
 * @author wang
 */
public class Node {
    int num = 0;
    Node next = null;
    Node before = null;
    
    Node(){
        
    }
    Node(int n){
        this.num = n;
    }



   boolean checkNum(int n){
            if(num == n){
                return true;
            }else{
                return false;
            }
    }   
}