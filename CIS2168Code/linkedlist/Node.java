/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package linkedlist;

/**
 *
 * Name: Yanling Chen
 * TuID: 914978493
 * 
 * 
 */
public class Node {
    int num;
    Node next=null;
    
    Node(int num){
        this.num = num;       
    }

    void changValue(int num){
        this.num = num;
    }
}
