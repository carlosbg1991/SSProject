import java.util.ArrayList;
import java.util.Random;

public class main {
	
	
	public main(){		
	}

	public static void main(String[] args) {
		int min = 1;		  // Max value in the instance (fixed)
		double alpha = 0.5;	  // Weighting the Worst-Case-Scenario (WCS) and the Best-Case-Scenario (BCS)
		int seed = 1;		  // possible = 0,1,2,3,4,5,6,7,8,9 (Consistency amongst Solvers)
		
		int minSize = 25;	  // Minimum instance size (Cardinality)
		int maxSize = 35;	  // Maximum instance size (Cardinality)
		
		double minDens = 0.1; // Minimum instance density ( dens = Cardinality(instance)/max(instance) ) 
		double maxDens = 1;	  // Maximum instance density ( dens = Cardinality(instance)/max(instance) )

		ExhaustiveSolver exSol = new ExhaustiveSolver();
		GreedySolver greedySol = new GreedySolver();

		for (int size = minSize; size<maxSize; size++){
			for (double dens = minDens; dens<=maxDens; dens+=0.1){

				// Determine Max value given density and size
				int max = (int) (size/dens);
				
			    // Benchmark creator class
			    Benchmark bmk = new Benchmark(size,min,max,seed);
			    int[] instance = bmk.getArray();
//			    for (int i = 0; i < instance.length; i++)
//			        System.out.print(instance[i] + " ");
//				System.out.println(" ");
	
				// Target definition
//				int target = min;									//BCS
//				int target = max;									//Max
//				int target = bmk.getSum();							//WCS
			    int target = (int) Math.floor(alpha*min + (1-alpha)*bmk.getSum());	//Ponderate
				System.out.println("Sum = " + target);
	
//				// Execution of the Exhaustive solver
//				long startTime_tot = System.nanoTime();
//				int numsol = exSol.solve(instance, target);
//				long endTime_tot = System.nanoTime();
//				long duration_tot = endTime_tot - startTime_tot;
//				System.out.format("Size %d - Density %.1f - target %d - NSols - %d - ExecTime %.3f ms. %n", size, dens, target, numsol, duration_tot/1000000.0);
//				
//				long startTime_1 = System.nanoTime();
//				int NSol1 = exSol.solve_timeLim(instance, target, 60);
//				long endTime_1 = System.nanoTime();
//				long duration_1 = endTime_1 - startTime_1;
//				System.out.format("Size %d - target %d - rSols(1min) - %d/%d=%.2f - ExecTime %.3f ms. %n", size, target, NSol1, numsol, (float) 100*NSol1/numsol, duration_1/1000000.0);
//				
//				long startTime_10 = System.nanoTime();
//				int NSol10 = exSol.solve_timeLim(instance, target, 60*10);
//				long endTime_10 = System.nanoTime();
//				long duration_10 = endTime_10 - startTime_10;System.out.format("Size %d - target %d - rSols(10min) - %d/%d=%.2f - ExecTime %.3f ms. %n", size, target, NSol10, numsol, (float) 100*NSol10/numsol, duration_10/1000000.0);
				
				// Execution of the Greedy Solver
				long startTime_g_tot = System.nanoTime();
				QuickSort sorter = new QuickSort();
				instance = sorter.sort(instance);
				int numsol_g = greedySol.solve(instance, target);
				long endTime_g_tot = System.nanoTime();
				long duration_g_tot = endTime_g_tot - startTime_g_tot;
				System.out.format("Size %d - Density %.1f - target %d - NSols - %d - ExecTime %.3f ms. %n", size, dens, target, numsol_g, duration_g_tot/1000000.0);

			}
		}
	}
}
