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
public class Account {
    static int n = 20;
    int balance;
    String accountNumber;
    Transanction[] transanctions = new Transanction[n];
    Customer customer;
    int count = 0;
    
    Account(){
        
    }
    
     Transanction[] reallocate(Transanction[] list){  
        Transanction[] arr1=new Transanction[list.length*2];
        System.arraycopy(list, 0, arr1, 0, list.length);
        n = n*2;
        return arr1;
      }
     
     int getBalance(){
         return balance;
     }
     
     Customer getCustomer(){
         return customer;
     }
     
    void deposit(int d){
        this.balance+=d;
        transanctions[count].balance = this.balance;
        System.out.println("Deposit: "+d+"\nBlance: "+balance);

        
        
    }
    
    void withdraw(int w){
        if(balance>=w){
             this.balance-=w;
             transanctions[count].balance = this.balance;
             System.out.println("Withdraw: "+w+"\nBlance: "+balance);
        }else{
             System.out.println("Overdrafting!!: "+w+"\nBlance only: "+balance);
        }
    }
     
     void setCustomer(int i){
         if(i==1){
             customer = new Student();
         }else if(i==2){
             customer = new Adult();
         }else {
             customer = new Senior();
         }

     }
      
     void newTran(Transanction newt){
         transanctions[count++] = newt;
     }
         
     void toDisplay(){
         System.out.println("name # address # age # tele # customernumber");
         System.out.println(customer.name +" # "+customer.address+" # "
                            +customer.age + " # " + customer.telephoneNumber +" # "            
                            +customer.customerNumer);
         System.out.println("Amount # Type # Balance # Date # customerNumber");
         for(int i = 0; i < transanctions.length; i++){
             if(transanctions[i] != null){
                 System.out.println(transanctions[i].amount+" # "
                                +transanctions[i].transType+" # "
                                +transanctions[i].balance+" # "
                                +transanctions[i].date+ " # "
                                +transanctions[i].customerNum);
             }
         }
         
         
     }
     

}
