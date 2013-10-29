/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banking;

/**
 *
 * @author wang
 */
public class Senior extends Customer{
    Senior(){
        super();
        this.SAVINGS_INTEREST = 0.2;
        this.CHECK_INTEREST = 0.1;
        this.CHECK_CHARGE = 0; 
        this.OVERDRAFT_PENALTY = 0;
    }
}
