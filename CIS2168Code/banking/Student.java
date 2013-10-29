/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package banking;

/**
 *
 * @author wang
 */
public class Student extends Customer{

    Student(){
        super();
        this.SAVINGS_INTEREST = 0.05;
        this.CHECK_INTEREST = 0.025;
        this.CHECK_CHARGE = 0; 
        this.OVERDRAFT_PENALTY = 0;
    }
}
