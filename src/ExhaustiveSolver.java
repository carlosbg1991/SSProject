public class ExhaustiveSolver {
	public ExhaustiveSolver(){
	}
	
	public int solve(int[] instance, int target){
		int size = instance.length;
		long numPoss = (long) Math.pow(2, size);
		int numsol = 0;
		int tempsum;
	    long[] poss;
	    BinaryGenerator bingen = new BinaryGenerator(size);

	    for(long i=0 ; i<numPoss ; i++){
	    	tempsum = 0;
	    	poss = bingen.generate(i,size);
		    for(int j=0 ; j<size ; j++){
		    	if(poss[j] == 1){
		    		tempsum += instance[j];
		    	}
		    }
		    if(tempsum == target){
//		    	System.out.print("Combination found! -> ");
		    	numsol += 1;
//		    	for(int j=0 ; j<size ; j++){
//			    	if(poss[j] == 1){
//			    		System.out.print(instance[j] + " ");
//			    	}
//			    }
//		    	System.out.println(" ");
		    }
	    }
	    
	    return numsol;
	}
	
	public int solve_timeLim(int[] instance, int target, int timeLim){
		int numsol = 0;
		long start = System.currentTimeMillis();
		long end = start + timeLim*1000; // timeLim (seconds) * 1000 (ms/sec)
		
		int size = instance.length;
		long numPoss = (long) Math.pow(2, size);
		int tempsum;
	    long[] poss;
	    BinaryGenerator bingen = new BinaryGenerator(size);

	    int i = 0;
    	while ((System.currentTimeMillis() < end) && i<numPoss) {
	    	tempsum = 0;
	    	poss = bingen.generate(i,size);
		    for(int j=0 ; j<size ; j++){
		    	if(poss[j] == 1){
		    		tempsum += instance[j];
		    	}
		    }
		    if(tempsum == target){
//		    	System.out.print("Combination found! -> ");
		    	numsol += 1;
//			    	for(int j=0 ; j<size ; j++){
//				    	if(poss[j] == 1){
//				    		System.out.print(instance[j] + " ");
//				    	}
//				    }
//			    	System.out.println(" ");
		    }
		    
		    i++;
	    }
		
		return numsol;
	}
}
