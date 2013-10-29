/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banking;

/**
 *
 * @author wang
 */
public class Transanction {
    String customerNum;
    int transType; //1:deposit 2:withdraw
    int amount;
    String date;
    String fees;
    int balance;
    
    Transanction(){
        
    }
    
    void processTran(Bank b, String customerNum, int transType,  int amount, String date, String fees){
      //  System.out.println("PLS input customerNUM, transancType(1.deposit 2. withdraw),amount,date,fees");
        this.customerNum = customerNum;
        this.transType = transType;
        this.amount = amount;
        this.date = date;
        this.fees = fees;
        if(transType == 1){
            b.makeDeposit(customerNum,amount);
        }else if(transType == 2){
            b.makeWithdraw(customerNum,amount);
        }
        
        
    }
}
