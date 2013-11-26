import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


/*
 * VM[] records whether the page p is currently resident
 * WIN(t+1) contains page references during the last t+1 steps
 * t: constant
 */
public class OptimalPageReplaceAlg {
	private int P;
	private int t;
	private int[] VM = null;
	private Queue<Integer> WIN = new LinkedList<Integer>();
	private Scanner in = null;
	private int totalPage = 0;
	private int pageFault = 0;

	
	OptimalPageReplaceAlg(int P, int t,Scanner in){
		this.P = P;
		this.t = t;
		this.in = in;
		VM = new int[P];
	}
	
	
	
	void PageFaultCalculation(){
		while(in.hasNext()){
				if(WIN.size() < t+1 ){//continue to read p into WIN
					WIN.add(in.nextInt());
				}else{//start to process
					WIN.add(in.nextInt());
					int page = WIN.remove();
						if(VM[page] == 0){
							pageFault++;
						}
					VMupdate();
					VM[page] = 1;
				}
		totalPage++;
		}
		
		while(!WIN.isEmpty()){
			int page = WIN.remove();
			if(VM[page] == 0){
				pageFault++;
			}
		VMupdate();
		VM[page] = 1;
		}
		
	}
	
	void VMupdate(){
		for(int i = 0; i < VM.length; i++){
			VM[i] = 0;
		}
		Queue<Integer> tmp  = WIN;
//		System.out.println("The size of tmp: "+tmp.size());
		while(!tmp.isEmpty()){
			VM[tmp.remove()] = 1;
		}		
	}
	
	int returnPageFault(){
		return pageFault;
	}
	
	double returnFaultRate(){
		return (double) pageFault/totalPage;
	}
}
