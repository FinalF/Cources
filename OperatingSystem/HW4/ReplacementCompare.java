import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class ReplacementCompare {

	static int P = 2000;
	static int t = 700;
	
	public static void main(String args[]) throws FileNotFoundException{	
		PageRefGen.generate(P);
//		Scanner in = new Scanner(PageRefGen.returnFile());


		/*Working set algotrithm*/
		WorkingSetAlg WS = new WorkingSetAlg( P, t, new Scanner(PageRefGen.returnFile()));
		WS.PageFaultCalculation();
		System.out.println("WS--> The page fault is: "+WS.returnPageFault());
		System.out.println("WS--> The page fault rate is: "+WS.returnFaultRate()*100+"%");
		System.out.println("WS--> The average frames required is: "+WS.returnF());
		/*Optimal page-replacement algorithm*/
		OptimalPageReplaceAlg OPR = new OptimalPageReplaceAlg( P, t, new Scanner(PageRefGen.returnFile()));
		OPR.PageFaultCalculation();
		System.out.println("OPR--> The page fault is: "+OPR.returnPageFault());
		System.out.println("OPR--> The page fault rate is: "+OPR.returnFaultRate()*100+"%");
		System.out.println("OPR--> The average frames required is: "+OPR.returnF());
		/*Page fault frequency algorithm*/
		PageFaultFrequencyAlg PFF = new PageFaultFrequencyAlg( P, t, new Scanner(PageRefGen.returnFile()));
		PFF.PageFaultCalculation();
		System.out.println("PFF--> The page fault is: "+PFF.returnPageFault());
		System.out.println("PFF--> The page fault rate is: "+PFF.returnFaultRate()*100+"%");
		System.out.println("PFF--> The average frames required is: "+PFF.returnF());
		
		/*compare the variations of page fault when t ranges from 100 to 1000*/
		statistics(50,1001,50);
		
	}
	
	static void statistics(int min, int max, int interval) throws FileNotFoundException{
		PrintWriter out = new PrintWriter(new File("ResultRecord.txt"));
		out.flush();
		for(int i = min; i < max; i+=interval){
			out.print(i);
			out.print(",");
			
			WorkingSetAlg WS = new WorkingSetAlg( P, i, new Scanner(PageRefGen.returnFile()));
			WS.PageFaultCalculation();
			out.print(WS.returnPageFault());
			out.print(",");
			out.print(WS.returnFaultRate()*100);
			out.print(",");
			
			OptimalPageReplaceAlg OPR = new OptimalPageReplaceAlg( P, i, new Scanner(PageRefGen.returnFile()));
			OPR.PageFaultCalculation();
			out.print(OPR.returnPageFault());
			out.print(",");
			out.print(OPR.returnFaultRate()*100);
			out.print(",");
			
			PageFaultFrequencyAlg PFF = new PageFaultFrequencyAlg( P, i, new Scanner(PageRefGen.returnFile()));
			PFF.PageFaultCalculation();
			out.print(PFF.returnPageFault());
			out.print(",");
			out.println(PFF.returnFaultRate()*100);
		}
		out.close();
		System.out.println("Finished!");
	}
	
}
