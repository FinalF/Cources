/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banking;

/**
 *
 * @author wang
 */
public class main {
    
    public static void main(String args[]){
   //create 2 accounts and do several transanctions on them:
    Bank BOA = new Bank();
    BOA.addAccount();  //customer number input : 001

    Transanction OP1 = new Transanction();
    Transanction OP2 = new Transanction();
    Transanction OP3 = new Transanction();
    OP1.processTran(BOA, "001", 1, 555, "2013-09-02", "0");
    BOA.getAccount(OP1.customerNum).newTran(OP1);
    OP2.processTran(BOA, "001", 2, 111, "2013-09-02", "0");
    BOA.getAccount(OP2.customerNum).newTran(OP2);
    BOA.getAccount("001").toDisplay(); //display account "001"
    
    
    BOA.addAccount();   //customer number input : 002
    OP3.processTran(BOA, "002", 1, 1000, "2013-09-02", "0");    
    BOA.getAccount("002"); //display account "002"
    }
    
    
}
