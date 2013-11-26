import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;



/*
 * VM[PFFNode] 
 */


public class PageFaultFrequencyAlg {
	private int P;
	private int t;
	private PFFNode[] VM = null;
	private Scanner in = null;
	private int totalPage = 0;
	private int pageFault = 0;

	
	PageFaultFrequencyAlg(int P, int t,Scanner in){
		this.P = P;
		this.t = t;
		this.in = in;
		VM = new PFFNode[P];
		for(int i = 0; i < VM.length; i++){
			VM[i] = new PFFNode();
		}
	}
	
	void PageFaultCalculation(){
		int faultRecord = 0;
		while(in.hasNext()){
			int page = in.nextInt();
			VM[page].u = 1;
				if(VM[page].res == 0){
					pageFault++;
					VM[page].res = 1;
					if((totalPage - faultRecord) > t){
						VMclean();
					}
					faultRecord = totalPage; 
					VMreset();
				}
			totalPage++;
		}
		
	}
	
	void VMclean(){
		for(int i = 0; i < VM.length; i++){
			if(VM[i].res == 1 && VM[i].u == 0){
				VM[i].res = 0;
			}
		}
	}
	
	void VMreset(){
		for(int i = 0; i < VM.length; i++){
			VM[i].u = 0;
		}
	}
	
	int returnPageFault(){
		return pageFault;
	}
	
	double returnFaultRate(){
		return (double) pageFault/totalPage;
	}
	
}
