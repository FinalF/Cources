/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package josephus;

import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author wang
 */
public class Josephus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner in = new Scanner(System.in);
        System.out.println("PLS input the number of people");
        int n = in.nextInt();
        System.out.println("PLS input the number of count");
        int m = in.nextInt();
        
        CircleList list = new CircleList();
        list.InitialList(n);
        int count = 0;

            while(list.hasNext() && n>1){
//            System.out.println(list.next().num);  
                count++;
                Node tmp = list.next();
                if(count == m){
                    list.DeleteNode(tmp);
                    count = 0;
                     n--;
                }

            }
        System.out.println("The initial position of Josephus should be: " + list.next().num);
        
    }
}
