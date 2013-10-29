/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
public interface ListInterface {
    boolean add(int num);
    boolean search(int num);
    boolean delete(int num);
    int removeFirst()throws MyException;
    int size();
    void merge( ListInterface b);
    void reverse();
    @Override
    public String toString();
    void printList();

}
