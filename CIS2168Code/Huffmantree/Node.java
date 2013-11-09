/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmantree;

/**
 *
 * @author wang
 */
public class Node {
    Node left = null;
    Node right =null;
    char c = '\u0000';
    int count;
    /*leaf node*/
    Node(char c,int count){
        this.c = c;
        this.count = count;
    }
    /*internal node*/
    Node(Node subLeft,Node subRight){
        this.count = subLeft.count + subRight.count;
        this.left = subLeft;
        this.right = subRight;
                
    }
    
     String print(){
        return this.c+" , "+this.count;
    }
}
