
package Assignment1_topologicalsort;

/**
 *ListArray : the data structure used for count array, succ array, bag and output
 * @author  yufeng
 */
public class ListArray_version3 {
    

    int size = 0; // define size of ListArray
	ListNode head = null; // define first node of ListArray
	ListNode tail = null; // define last node ofListArray
	ListNode current = head; // define current node of ListArray
	ListNode current2 = head;

    
int Size() {
		// get ListArray current size
		return size;
	}
       

void add(int x) {
		// ListArray add new node
		ListNode newnode = new ListNode(x);
//		int data = newnode.dataReturn();
		if (size == 0) {
			tail = newnode;
			head = newnode;
			current = head;
			current2 = head;
			
			
			
		} 
		else if (size == 1) {
			tail = newnode;
			head.next = tail;
			current = head;

			tail.ahead = head;
			current2 = head;

		} 
		
		else {
			// While there exists other nodes between head and tail
			current2 = tail;
			tail.next = newnode;
			tail = newnode;
			current2.next = tail;
			tail.ahead = current2;
			current = head;

		}
		// update current size
		size++;
	}
        

int delete(int x) {
		// remove the x'th node in ListArray
		if ((x > size) | (x <= 0)) {
			// when index is beyond size
			 System.out.println("Input is exceeding the index");
			 return 000;
		} 
        else {
			// when index is whithin size
			if (x == 1) {
				// remove the first node
				if (size == 1) {
					// when size is 1
					int temp = head.data;
					head = null;
                    tail = null;
                    size = 0;
					return temp;
				}  
				else {
         // While there exists other nodes between head and tail
					int temp = head.data;
					head = head.next;					
					current = head;
					size--;
					return temp;
				}
			} 

          else if (x == size ) {
				// remove the last node
				int temp = tail.data;
				current = head;
                 //move the pointer
//				for (int j = 1; j <= size - 2; j++) {
//					current = current.next;
//				}
				tail = tail.ahead;
				tail.next = null;
				current = head;
				current2 = tail.ahead; 
				size--;
				return temp;
			} 
          else {
				//  the x'th node is neither the head nor the tail
				current = head;
				for (int j = 1; j <= x - 2; j++) {
					current = current.next;
				}
				int temp = current.next.data;
				current.next = current.next.next;
				current = head;
				size--;
				return temp;
			}
		}

        }

        
void display() {
		// print out the data in the ListArray
		current = head;
		if (head == null) {
			System.out.println("ListArray is empty");
		}
		else {
			for (int i = 1; i < size; i++) {
				System.out.print(current.data + "¡ú");
				current = current.next;
			}
			System.out.println(tail.data);
			current = head;
		}
}

void pointerMove(int k){
	for (int i = 0; i < k; i++){
	current = current.next;
	}
}





public class ListNode{
    /*To define the data structure for the Successors List
    *each record in this list contains data + "pointer" to the
    * next record
    * @author Wang Yufeng
    */
    int data;           //define the data
    ListNode next;      //define the next record
    ListNode ahead;
        ListNode(int d){
        data = d;
        next = null;
        ahead = null;
        }
    int dataReturn(){
    	return data;
    }

}
}
