
public class BinaryGenerator {

	long[] results;
	
	public BinaryGenerator(int numbits){
		results = new long[numbits];
	}
	
	public long[] generate(long number, int numbits){
	
		// GENERATE SEQUENCE OF POSSIBILITIES
		long tmp = number;
		for ( int j = 0; j < numbits; j++ ) {
			results[j] = tmp % 2;
			tmp = ( tmp - results[j] ) / 2;
		}

	    // PRINT RESULTS
//    	System.out.print(number + " -- ");
//	    for ( int j = numbits-1; j >= 0; j-- ) {
//	    	System.out.print(results[j] + " ");
//	    }
//	    System.out.println(" ");
	    
	    return results;
	}
}
