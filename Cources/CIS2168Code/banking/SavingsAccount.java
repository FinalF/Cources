/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banking;

/**
 *
 * @author wang
 */
public class SavingsAccount extends Account{
     public SavingsAccount() {
        super();
    }
    
    void deposit(int d){
        this.balance+=d;
        System.out.println("Deposit: "+d+"\nBlance: "+balance);
    }
    
    void withdraw(int w){
        if(balance>=w){
             this.balance-=w;
             System.out.println("Withdraw: "+w+"\nBlance: "+balance);
        }else{
             System.out.println("Overdrafting!!: "+w+"\nBlance only: "+balance);
        }
    }
    
    void addInterest(){
        //caculate interest
    }
}
