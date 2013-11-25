import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


/*
 * Yufeng Wang
 * 2013.11.25
 */

public class PageRefGen {
	/*
	 * Used to generate a reference string uesd for 
	 * virtual memory replacement algorithms
	 * 
	 * P: size of allocated virtual memory (thousands)
	 * m: times of picking references
	 * p,e: reference address range, choose references randomly 
	 * 		from [p,p+e] each time
	 * t: probability that p moves to a new location; otherwise, p=p+1
	 * 
	 */
	
	private static int P = 2000;
	private static int m = 200;
	private static int p = 100;
	private static int e = 90;
	private static int t = 2000;
	private static File f = new File("reference_string.txt");
	
	PageRefGen(){
		
	}
	
	PageRefGen(int P, int m, int p, int e, int t, File f){
		PageRefGen.P = P;
		PageRefGen.m = m;
		PageRefGen.p = p;
		PageRefGen.e = e;
		PageRefGen.t = t;
		PageRefGen.f = f;
	}
	
	static void generate(){
		try {
			PrintWriter out = new PrintWriter(f);
			out.flush();
			int tmp = 0; //reference
			
			for(int i = 0; i < m; i ++){
			tmp = p + (int)(Math.random() * (e + 1)); //generate a reference addressed [p,p+e]
			out.println(tmp);
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
}
