
package linkedlist;

/**
 *  flame   2013/10/16
 * This is a sorted single-linked list
 * contains Node in which there is an int value and a pointer
 * 1) add(int num)
 * 2) search(int num)
 * 3) delete(int num)
 * 4) removeFirst()
 * 5) size()
 * 6) merge(LinkedList b) merge it with the current one
 * 7) reverse()
 * 8) toString()
 */
public class LinkedList implements ListInterface{
    
    Node head = null;
    Node p = null; //record the current node
    Node pb = null;//record the node before current node
    
    LinkedList(){
        
    }
    

    public boolean add(int num){
    /*
     * add a new value (and return true) if it is not already in the list, 
     * otherwise do nothing and return false
     */
        p = head;
        boolean notExist = true;
        if(head==null){
            head = new Node(num);
            p = head;
        }else{
        //we operate on p, compare its value with the new value  
            while(p!=null){
                if(p.num<num){
                    //put the new node after p
                    if(p.next!=null){
                        pb = p; 
                        p = p.next;
                    }else{
                        p.next = new Node(num);
                        p = head;
                        break;
                    }
                }else if(p.num>num){
                    //put the new node before p
                    Node tmp = new Node(num);
                    tmp.next = p;
//                    if(pb!=null){
//                        pb.next = tmp;
//                    }
                    if(head.num>num){
                        head = tmp;
                        p = head;
                    }
                    break;
                }else{
                    notExist = false;
                    System.out.println(num+" Already exists. We do nothing");
                    break;
                }
            }
            
        }
        return notExist;
    }
    

    

    public    boolean search(int num){
     /*
     * search for a given value and return a Boolean to indicate if it is in the list
     */
        p = head;
        boolean exists = false;
        if(p==null){
            System.out.println("It's empty");
        }
        
        while(p!=null){
            if(p.num == num){
                exists = true;
                break;
            }else{
                p = p.next;
            }
        }
        return exists;
    }


    public    boolean delete(int num){
    /*
     * delete a given value (and return true) if it is already in the list, 
     * otherwise do nothing and return false
     */
        p = head;
        boolean success = false;
        if(p==null){
            System.out.println("It's empty");
        }        
        
        while(p!=null){
            if(p.num == num){
            success = true;
                if(p!=head){
                    pb.next = p.next;
                }else{
                    head = head.next;
                }
                break;
            }else{
                pb = p;
                p = p.next;
            }
        }
        return success;
    }
    
    

     public  int removeFirst() throws MyException{
    /*
     * remove the first node and return its value if the list is not empty,
     * otherwise throw an exception
     */
        int tmp;
        if(head==null){
            throw new MyException("The list is empty"); 
        }else{
            tmp = head.num;
            head = head.next;
        }
        return tmp;
    }
    
    
    
     public   int size(){
    /*
    * report the current size of the list
    */
        p = head;
        int l = 0;
        while(p!=null){
            l++;
            p = p.next;
        }
        p = head;
        return l;
    }
    
     public   void merge(ListInterface b){
    /*
     * merge this list with another one of the same class
     */
        LinkedList tmp=(LinkedList)b;
        tmp.printList();
        p = head;
        tmp.p = tmp.head;
        while(tmp.p!=null){
            add(tmp.p.num);
            tmp.p = tmp.p.next;
        }
        
//         System.out.println("Current head is: "+head.num);
        
    }
    
    public    void reverse(){
        /*
         * reverse the order of the values in the list
         */
        p = head;
//        System.out.println("Size is: "+size());
        int[] stack = new int[size()];
        int count=0;
//               System.out.println("Current head is: "+p.num);
        while(p != null){
            stack[count] = p.num;
//            System.out.println("this value is: "+stack[count] +" "+p.num);
            count++;
            p = p.next;
        }
        
        p = head;
        count = size();
        while(p != null){
            p.changValue(stack[--count]);
            p = p.next;
        }
        
    }
    
    
    @Override
    public String toString(){
        String s = "";
        p = head;
        while(p!=null){
          s = s + " " + p.num;   
          p = p.next;
        }
        return s;
    }
    
    public   void printList(){
        p = head;
        if(p==null){
            System.out.println("It's empty");
        }
        System.out.println("\n List:"); 
        while(p!=null){
            if(p.next!=null){
            System.out.print(p.num+" --> ");
            }else{
            System.out.print(p.num+" --> [null]\n");               
            }
            p=p.next;
        }
        System.out.println();
    }
    

}
