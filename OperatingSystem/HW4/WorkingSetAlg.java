
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


/*
 * VM[] records whether the page p is currently resident
 * WIN(t+1) contains page references during the last t+1 steps
 * t: constant
 */

public class WorkingSetAlg {
	private int P;
	private int t;
	private int[] VM = null;
	private Queue<Integer> WIN = new LinkedList<Integer>();
	private Scanner in = null;
	private int totalPage = 0;
	private int pageFault = 0;
	
	
	WorkingSetAlg(int P, int t,Scanner in){
		this.P = P;
		this.t = t;
		this.in = in;
		VM = new int[P];
	}
	
	
	void PageFaultCalculation(){
		int q;
		while(in.hasNext()){
			int page = in.nextInt();
			WIN.add(page);
				if(WIN.size() > t+1){
					q = WIN.remove();
						if(!WIN.contains(q)){
							VM[q] = 0;
						}
				}
			if(VM[page]==0){
				VM[page] = 1;
				pageFault++;
			}
			totalPage++;
		}
	}
	
	int returnPageFault(){
		return pageFault;
	}
	
	double returnFaultRate(){
		return (double) pageFault/totalPage;
	}
}
