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
public class Bank {
    
    static int n = 100;
    Account[] record = new Account[n];
    int i = 0;
    
    Bank(){
        
    }
    
    Account[] reallocate(Account[] list){  
        Account[] arr1=new Account[list.length*2];
        System.arraycopy(list, 0, arr1, 0, list.length);
        n = n*2;
        return arr1;
      }
    
    
    void makeDeposit(String cus,int d){
//        System.out.println("input the customerNumber: ");
//        Scanner in = new Scanner(System.in);
        for(int count=0;count<i;count++){
            if(record[count].getCustomer().customerNumer.equals( cus)){
//                record[count].transanctions[record[count].count] = 
                record[count].deposit(d);
                
            }
        }
    }
    
    void makeWithdraw(String cus,int d){
//        System.out.println("input the customerNumber: ");
//        Scanner in = new Scanner(System.in);
        for(int count=0;count<i;count++){
            if(record[count].getCustomer().customerNumer.equals(cus)){
                record[count].withdraw(d);
//                r.transanctions[r.count++] = ;
            }
        }
    }
    
    Account getAccount(String cusNum){
        int count = 0;
        for(count=0; count < i; count++){
            if(record[count].getCustomer().customerNumer.equals(cusNum)){
            break;
            }         
        }
        return record[count];
    }
    
    void addAccount(){
        
        if(i==n){
           reallocate(record); 
        }
        System.out.println("Choose account category:\n1.checking\n2.saving");
        Scanner in = new Scanner(System.in);
        switch(in.nextInt()){
            case 1:
                record[i] = new CheckingAccount();
                break;
            case 2:
                record[i] = new SavingsAccount();
                break;
            default:
                System.out.println("Error input!");     
                break;
        }
        
        System.out.println("Choose customer category:\n1.students\n2.aldults\n3.Senior");
        switch(in.nextInt()){
            case 1:
                record[i++].setCustomer(1);
                break;
            case 2:
                record[i++].setCustomer(2);
                break;
            case 3:
                record[i++].setCustomer(3);
                break;
            default:
                System.out.println("Error input!");     
                break;
        }
    
    

    }
}
