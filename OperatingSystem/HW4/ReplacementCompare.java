import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ReplacementCompare {

	static int P = 2000;
	static int t = 200;
	
	public static void main(String args[]) throws FileNotFoundException{	
		PageRefGen.generate(P);
//		Scanner in = new Scanner(PageRefGen.returnFile());


		/*Working set algotrithm*/
		WorkingSetAlg WS = new WorkingSetAlg( P, t, new Scanner(PageRefGen.returnFile()));
		WS.PageFaultCalculation();
		System.out.println("WS--> The page fault is: "+WS.returnPageFault());
		System.out.println("WS--> The page fault rate is: "+WS.returnFaultRate());
		/*Optimal page-replacement algorithm*/
		OptimalPageReplaceAlg OPR = new OptimalPageReplaceAlg( P, t, new Scanner(PageRefGen.returnFile()));
		OPR.PageFaultCalculation();
		System.out.println("OPR--> The page fault is: "+OPR.returnPageFault());
		System.out.println("OPR--> The page fault rate is: "+OPR.returnFaultRate());
		/*Page fault frequency algorithm*/
		PageFaultFrequencyAlg PFF = new PageFaultFrequencyAlg( P, t, new Scanner(PageRefGen.returnFile()));
		PFF.PageFaultCalculation();
		System.out.println("OPR--> The page fault is: "+PFF.returnPageFault());
		System.out.println("OPR--> The page fault rate is: "+PFF.returnFaultRate());
	}
	
	
	
}
