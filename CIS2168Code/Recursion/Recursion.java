

//package Recursion;
import java.util.*;
public class Recursion {

    /**Author: Flame
     * Date: 2013/10/22
     * Define a base, in which there're n positive numbers
     * input a number x, find whether x equals sum of some of 
     * elements in this base
     * optional: output elements combinations whose sum equals x
     */
    static int N = 6;
    static int[] base = {3,3,7,8,2,6};
    static int total = 0;
    
    public static void main(String[] args) {
        Scanner Num = new Scanner(System.in);       
        System.out.println("Input the total value");
        total = Num.nextInt();
        
        System.out.println("Options u have: \n1. Use the number example base {3,3,7,8,2,6}"
                + "\n2. Build ");
        int choice = Num.nextInt();
        switch(choice){
            case 1:
                break;
            case 2: 
                System.out.println("How many number you wonna use?");
                N = Num.nextInt();
                System.out.println("Pls type in these numbers one by one (total "+N+" numers)");
                int c = 0;
                base = new int[N];
                while(c<N){
                    System.out.println("Type in the  numer: ");
                    int tmp = Num.nextInt();
                    base[c] = tmp;
                    c++;
                }
                c=0;
                System.out.print("The numbers in the base is:{");
                while(c<N){
                    System.out.print(" "+base[c]);
                    c++;
                }
                System.out.println(" }");
                break;
            default:
                break;
        }
        
        if(total>sum(base)){
            System.out.println("No");
        }else{
            System.out.println("The total value to be processed: "+total);
            Check(0);
        }
        
        if(found=false){
            System.out.println("no");
        }
      
    }
    
    static int baseSum = 0;
    static Stack<Integer> st = new Stack<Integer>();
    static boolean found = false;    
    static void Check(int indexStart){
        int i = 0;
        for( i = indexStart; i < N; i++){
            Stack<Integer> sttmp = null;
                baseSum+=base[i];
                st.push(new Integer(base[i]));
            if(baseSum==total){
                found = true;
                sttmp = stackTmp(st);
                System.out.println("\nfound!");
                System.out.print("The given total value can be the sum of elements: { ");                
                while(!sttmp.isEmpty())
                    System.out.print(sttmp.pop().intValue()+" ");
                System.out.println("}");
            }
 
            /*Recursion part*/
        if(i+1<N-1)
            Check(i+1);
            baseSum-=base[i];
//                System.out.println("stack size: "+st.size());
            st.pop();

        }
    }
    
       static Stack<Integer> stackTmp(Stack<Integer> a){
        Stack<Integer> sttmp = new Stack<Integer>();
//        System.out.println("Original stack size: "+a.size());
        int[] l = new int[a.size()];
        int i = 0;
        while(!a.isEmpty()){          
            int tmp = a.pop();
            l[i] = tmp;
            i++;
        }
        for(i=0;i<l.length;i++){
            int tmp = l[i];
            a.push(tmp);
            sttmp.push(tmp);
        }
        return sttmp;
    }
           
    
    static int sum(int[] a){
        int sum = 0;
        for(int i = 0; i<a.length;i++)
            sum+=a[i];
        return sum;
    }
    

  
    
}

