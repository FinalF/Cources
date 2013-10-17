/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package linkedlist;

import java.util.Scanner;

/**
 *
 * @author flame 2013/10/16
 */
public class ListTest {
    
   public static void main(String args[]) throws MyException{
       LinkedList list = new LinkedList();
/*This part is to shwo that there will be an exception, if we try to remove
 * the first node from the empty list. 
 */
 /*     
       list.removeFirst();
       list.printList();
 */
       queryExample("Add a node with value 9 into the list?");
       list.add(9);
       list.printList();
       queryExample("Add a node with value 10 into the list?");       
       list.add(10);
       list.printList();
       queryExample("Try to add an extra node with value 10 into the list?");       
       list.add(10);
       queryExample("Add a node with value 5 into the list?"); 
       list.add(5);
       list.printList();
       queryExample("Wonna see current size of the list?");               
       System.out.println("Current size: "+list.size()+"\n");
       queryExample("Wonna remove the first node from the list?"); 
       list.removeFirst();
       list.printList();
       queryExample("Wonna search node of value 10 in the list?"); 
       System.out.println("Search for 10: " + list.search(10));
       queryExample("Wonna search node of value 7 in the list?"); 
       System.out.println("Search for 7: " + list.search(7));
       queryExample("Wonna delete node of value 10 in the list?"); 
       System.out.println("Delete for 10: " + list.delete(10));
       list.printList();
       queryExample("Wonna delete node of value 7 in the list?");       
       System.out.println("Delete for 7: " + list.delete(7));
       list.printList();
                  
        queryExample("Wonna create another list by adding node with values 15, 3?");           
        LinkedList list2 = new LinkedList();
        list2.add(15);
        list2.add(3);
        System.out.print("New ");
        list2.printList();
        queryExample("Wonna merge these two lists?");         
        list.merge(list2);
        list.printList();
        queryExample("Wonna reverse the order of the list?");          
        list.reverse();
        list.printList();
        queryExample("Wonna translate it to a simple string?");  
        System.out.println(list.toString());
   }

   static void queryExample(String x){
       System.out.println(x);
       System.out.println("enter 'y' to continue");
       Scanner in = new Scanner(System.in);
       String s = in.nextLine();
       if(!s.equals("y")){
           System.out.println("Thanks for using");
           System.exit(0);
       }
       
   }
   
   static void query(){
          System.out.println("You're give 2 lists, you have options:\n "
                  + "'1' Add a node"
                  + "'2' Search a node"
                  + "'3' Delete a node"
                  + "'4' Remove the first node from the list"
                  + "'5' Get the size of the list"
                  + "'6' Merge this two list"
                  + "'7' Reverse the order of nodes in the list"
                  + "'8' Transform it to a string"
                  + "'0' quit" );       
 
       Scanner in = new Scanner(System.in);
       int s = in.nextInt();
       if(s==0){
           System.out.println("Thanks for using!");
           System.exit(0);
       }else{
           System.out.println("");
           switch(s){
               case 1:
                   break;
               case 2:
                   break;
               case 3:
                   break;
               case 4:
                   break;
               case 5:
                   break;
               case 6:
                   break;
               case 7:
                   break;
               case 8:
                   break;
           }
       }
       
   }
}
