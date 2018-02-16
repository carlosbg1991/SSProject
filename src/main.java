import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class main {
	
	public main(){ }
	
	public static void cleanDirectory(String basicPath1){
		File pathTemp = new File(basicPath1);
		for(File f: pathTemp.listFiles()) 
			f.delete();
	}

	public static void generateAMPLData(String basicPath1, int[] instance,
										int size, double dens, int seed, int target) throws IOException{
//		double dens1 = Math.round((dens * 100) * 10) / 1000.0;		// Rounding to 1st decimal
		String temp;

		// File name for AMPL Output
		String outFileName;
		outFileName = basicPath1 + "inst_" + seed + ".txt";
		BufferedWriter bw = new BufferedWriter(new FileWriter(outFileName));

		// Write Instance on File for AMPL
	    bw.write("data;"); bw.write("\n");
	    temp = "param N := " + size + ";";
	    bw.write(temp); bw.write("\n");
	    temp = "param target := " + target + ";";
		bw.write(temp); bw.write("\n");
		temp = "param Numbers := ";
		bw.write(temp);
	    for (int i = 0; i < instance.length; i++){
	    	temp = "[" + i + "] " + instance[i] + "  ";  
	    	bw.write(temp);
	    }
	    bw.write(";"); bw.write("\n");
	    bw.write("end;");
		bw.close();
	}

	public static void generateAMPLBasicData(String basicPath1,
			int maxIter) throws IOException{
		// File name for AMPL Output
		String outFileName;
		outFileName = "/Users/carlosbocanegra/workspace/SSProject/BasicData.txt";
		BufferedWriter bw = new BufferedWriter(new FileWriter(outFileName));

		// Write Instance on File for AMPL
		bw.write("data;"); bw.write("\n");
		String temp = "param numInst := " + maxIter + ";";
		bw.write(temp); bw.write("\n");
		bw.write("end;");
		bw.close();
	}

	public static int defineTarget(String policy, Benchmark bmk, double alpha, int min, int max){
		switch(policy) {
			case "BCS":
				return min;														// Best-Case-Scenario
			case "WCS":
				return max;														// Max
			case "MAX":
				return bmk.getSum();											// Worst-Case-Scenario
			case "CONF":
				return (int) Math.floor(alpha*min + (1-alpha)*bmk.getSum());	// Wheight;
			default:
				return max;
		}
	}

	public static void ILPGreedyComparator(int[] targets, int[] solGreedy, int[] solILP, int[] solExhaus, int size, double dens, double alpha){
		int ROS_greedy = 0 , ROS_ilp = 0 , ROS_exh = 0;
		ArrayList<Double> gapYesInstance_gr = new ArrayList<Double>();
		ArrayList<Double> gapNoInstance_gr = new ArrayList<Double>();
		ArrayList<Double> gapNoInstance_ilp = new ArrayList<Double>();
		for(int iter=0; iter<targets.length; iter++){
//			System.out.format("%3s - %3s %3s - %3s\n",targets[iter],solGreedy[iter],solILP[iter],solExhaus[iter]);
			// EXIST SOLUTION
			if (solExhaus[iter]>0)
				ROS_exh++;
			// YES GREEDY + YES ILP
			if(solGreedy[iter]==targets[iter] && solILP[iter] == targets[iter]) {
				ROS_greedy++;
				ROS_ilp++;
			} 
			// NO GREEDY + YES ILP
			else if(solGreedy[iter] != targets[iter] && solILP[iter] == targets[iter]) {
				ROS_ilp++;
				gapYesInstance_gr.add((double) (100*(targets[iter] - solGreedy[iter])/targets[iter]));
			}
			// NO GREEDY + NO ILP
			else if(solGreedy[iter] != targets[iter] && solILP[iter] != targets[iter]){
				gapNoInstance_gr.add((double) (100*(targets[iter] - solGreedy[iter])/targets[iter]));
				gapNoInstance_ilp.add((double) (100*(targets[iter] - solILP[iter])/targets[iter]));
			}
		}
		
		ROS_greedy = 100*ROS_greedy/targets.length;
		ROS_ilp = 100*ROS_ilp/targets.length;
		ROS_exh = 100*ROS_exh/targets.length;

		System.out.format("%3s\t%.1f\t%.2f\t%5s\t%5s\t%5s\n",size,dens,alpha,ROS_greedy,ROS_ilp,ROS_exh);

		double temp = 0.0;
		if(!gapYesInstance_gr.isEmpty()) {
			for(Double iter : gapYesInstance_gr)
				temp += iter;
			System.out.format("%3s\t%.1f\t%.2f\t%.1f\t",size,dens,alpha,temp/gapYesInstance_gr.size());
	    } else
	    	System.out.format("%3s\t%.1f\t%.2f\t%.1f\t",size,dens,alpha,0.0);

	    temp = 0.0;
		if(!gapNoInstance_gr.isEmpty()) {
			for(Double iter : gapNoInstance_gr)
				temp += iter;
			System.out.format("%.1f\t",temp/gapNoInstance_gr.size());
		} else
			System.out.format("%.1f\t",0.0);

		temp = 0.0;
		if(!gapNoInstance_ilp.isEmpty()) {
			for(Double iter : gapNoInstance_ilp)
				temp += iter;
			System.out.format("%.1f\n",temp/gapNoInstance_ilp.size());
		} else
			System.out.format("%.1f\n",0.0);	
	}
	
	public static void ILPParserTime(int[] solILP){
		
	}

	public static void main(String[] args) throws IOException {		
		int min = 1;		    // Max value in the instance (fixed)
		double alpha = 0.5;	    // Default. Weighting the Worst-Case-Scenario (WCS) and the Best-Case-Scenario (BCS)
		double dens = 1.0;	    // Default. density of the Instance (I), defined as card(I)/max(I)
		int size = 10;			// Default. Instance Size
		int seed = 1;		    // possible = 0,1,2,3,4,5,6,7,8,9 (Consistency amongst Solvers)

		int minSize = 10;	    // Minimum instance size (Cardinality)
		int maxSize = 30;	    // Maximum instance size (Cardinality)

		double minDens = 0.1;   // Minimum instance density ( dens = Cardinality(instance)/max(instance) ) 
		double maxDens = 1.0;   // Maximum instance density ( dens = Cardinality(instance)/max(instance) )

		double minAlpha = 0.0;   // Minimum Alpha: Target = alpha*min(N) + (1-alpha)*Sum(N);
		double maxAlpha = 1.0;   // Maximum Alpha: Target = alpha*min(N) + (1-alpha)*Sum(N);

		String policy = "CONF"; // Possible: BCS  = Best-Case-Scenario, Minimum of the instance
								//			 WCS  = Worst-Case-Scenario, Sum of all the elements
								//			 MAX  = Maximum possible value in the Benchmark
								// 			 CONF = Allows for flexible configuration with "alpha"

		int maxIter = 50;	 	// Number of Iterations per configuration

		// INSTANCES OF OUR SOLVERS
		ExhaustiveSolver exSol = new ExhaustiveSolver();
		GreedySolver greedySol = new GreedySolver();

		// PATH TO STORE DATA FOR AMPL
		String basicPathInp = "/Users/carlosbocanegra/workspace/SSProject/Inputs/";
		String basicPathOut = "/Users/carlosbocanegra/workspace/SSProject/Outputs/";
		String basicPathInp1 = basicPathInp;
		String basicPathOut1 = basicPathOut;

		// GENERATE BASIC AMPL INPUTS
		generateAMPLBasicData(basicPathInp, maxIter);

		int target;
		int totSum;
		long totEx;

		int targets[]   = new int[maxIter];	 // Targets
		int solExhaus[] = new int[maxIter];	 // Solutions found by Exhaustive Solver
		int solGreedy[] = new int[maxIter];	 // Solutions found by Greedy Solver
		int solILP[]    = new int[maxIter];	 // Solutions found by ILP Solver

		long startTime_tot, endTime_tot, duration_tot;
		int numsol_ex;						 // Number of available solutions in Instance 
		int idx_gr, idx_ilp, idx_gen;		 // Index for Greedy and ILP Solutions
		boolean sol_gr;						 // True if Greedy has found the solution

		// CLEAN DIRECTORY FOR AMPL RESULTS
		cleanDirectory(basicPathInp);	// INPUTS FOR AMPL
		cleanDirectory(basicPathOut);	// OUTPUTS FROM AMPL

//		for (size = minSize; size<=maxSize; size+=5){
		for (alpha = minAlpha; alpha<=maxAlpha; alpha+=0.1){
//		for (dens = minDens; dens<=maxDens; dens+=0.1){
				totSum = 0;
				totEx = 0;
				idx_gen = 0;
				idx_gr = 0;
				idx_ilp = 0;
				numsol_ex = 0;

//				basicPathInp = basicPathInp1 + "Size_" + size + "/";
				basicPathInp = basicPathInp1;
				new File(basicPathInp).mkdirs();
//				basicPathOut = basicPathOut1 + "Size_" + size + "/";
				basicPathOut = basicPathOut1;
				new File(basicPathOut).mkdirs();

				for (seed = 1; seed<=maxIter; seed++){
					// MAX VALUE BASED ON DENSITY
					int max = (int) (size/dens);

				    // INSTANCE GENERATION
				    Benchmark bmk = new Benchmark(size,min,max,seed);
				    int[] instance = bmk.getArray();

					// TARGET DEFINITION
				    target = defineTarget(policy, bmk, alpha, min, max);
				    targets[idx_gen] = target;

				    // GENERATE AMPL OUTPUT
				    generateAMPLData(basicPathInp, instance,
							size, dens, seed, target);
				    
					// EXHAUSTIVE SOLVER
					startTime_tot = System.nanoTime();
					numsol_ex = exSol.solve(instance, target);
					solExhaus[idx_gen] = numsol_ex; 
					endTime_tot = System.nanoTime();
					duration_tot = endTime_tot - startTime_tot;
//					System.out.format("EXHAUS Size %2s - Density %.1f - target %4s - NSols - %5s - ExecTime %.3f ms. %n", size, dens, target, numsol_ex, duration_tot/1000000.0);
					
//					long startTime_1 = System.nanoTime();
//					int NSol1 = exSol.solve_timeLim(instance, target, 60);
//					long endTime_1 = System.nanoTime();
//					long duration_1 = endTime_1 - startTime_1;
//					System.out.format("Size %d - target %d - rSols(1min) - %d/%d=%.2f - ExecTime %.3f ms. %n", size, target, NSol1, numsol, (float) 100*NSol1/numsol, duration_1/1000000.0);

//					long startTime_10 = System.nanoTime();
//					int NSol10 = exSol.solve_timeLim(instance, target, 60*10);
//					long endTime_10 = System.nanoTime();
//					long duration_10 = endTime_10 - startTime_10;System.out.format("Size %d - target %d - rSols(10min) - %d/%d=%.2f - ExecTime %.3f ms. %n", size, target, NSol10, numsol, (float) 100*NSol10/numsol, duration_10/1000000.0);

					// GREEDY SOLVER
					startTime_tot = System.nanoTime();
					QuickSort sorter = new QuickSort();
					instance = sorter.sort(instance);
					sol_gr = greedySol.solveBool(instance, target);
					endTime_tot = System.nanoTime();
					duration_tot = endTime_tot - startTime_tot;
//					System.out.format("GREEDY Size %2s - Density %.1f - target %4s - NSols - %5s - ExecTime %.3f ms. %n", size, dens, target, sol_gr, duration_tot/1000000.0);
					totEx += duration_tot; 
					if(sol_gr) totSum++;

					// GREEDY SOLVER FOR COMPARISON WITH ILP
					solGreedy[idx_gr] = greedySol.solveInt(instance, target);
//					System.out.format("GREEDY Size %2s - Density %.1f - target %4s - Solution - %5s %n", size, dens, target, solGreedy[idx_gr]);
					
					idx_gen++;
					idx_gr++;
				}
//				System.out.format("GREEDY RATE: Size %3s - Density %.1f - ROS %3s\n",size,dens,100*totSum/maxIter);
//				System.out.format("Size %3s - Alpha %.2f - ROS %3s\n",size,alpha,100*totSum/maxIter);
//				System.out.format("%3s %3s %.3f ms.\n",size,100*totSum/maxIter,totEx/1000000.0);

//				// ILP SOLVER
			    String[] cmd = { "bash", "-c", "/Applications/amplide.macosx64/ampl /Users/carlosbocanegra/workspace/SSProject/aut3.run"};
			    Process proc = Runtime.getRuntime().exec(cmd);

		    	// PARSE ILP RESULTS
		    	String s = null;
		    	BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		    	while ((s = stdInput.readLine()) != null) {
//		    		System.out.println(s);
		    		if(s.split(": ").length>1)
		    			solILP[idx_ilp] = Integer.parseInt(s.split(": ")[1]);
		    		else
		    			solILP[idx_ilp] = 0;	// There is no solution for the Problem
		    		idx_ilp++;
		    	}
		    	BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
//		    	System.out.println("ERROR OUTPUT (if any):\n");
		    	while ((s = stdError.readLine()) != null) { System.out.println(s); }

//		    	// ILP - GREEDY COMPARATOR
		    	ILPGreedyComparator(targets, solGreedy, solILP, solExhaus, size, dens, alpha);
			}
//			}
//			}
	}
}