/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banking;

import java.util.Scanner;

/**
 *
 * @author wang
 */
public class Customer {
 String name, address, telephoneNumber, customerNumer;
 int age;
 double SAVINGS_INTEREST, CHECK_INTEREST, CHECK_CHARGE, OVERDRAFT_PENALTY;
 
 Customer(){
         Scanner in = new Scanner(System.in);
         System.out.println("customer name: ");
         this.name = in.next();
         System.out.println("customer address: ");
         this.address = in.next();
         System.out.println("customer age: ");
         this.age = in.nextInt();
         System.out.println("customer telephone: ");
         this.telephoneNumber = in.next();
         System.out.println("customer number: ");
         this.customerNumer = in.next();
 }
  
 double getSavingInterest(){
     return SAVINGS_INTEREST;
 }
 
 double getCheckInterest(){
     return CHECK_INTEREST;
 }
    
  double getCheckCharge(){
     return CHECK_CHARGE;
 }
 
}

