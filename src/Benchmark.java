

import java.util.Random;

public class Benchmark {

	private int[] array; 
	private int size = 10;	// Default Size for Instance
	private int min = 1;	// Default Minimum value in Instance
	private int max = 10;	// Default Maximum Value in Instance
	private int seed = 0;	// Default Seed. Values should be 0,1,2,3,4,5,6,7,8,9
	
	//  Constructor with default size, min and max
	public Benchmark() {
		generate(this.size, this.min, this.max, this.seed);
	}

    //  Constructor with default min and max	
	public Benchmark(int size) {
		generate(size, this.min, this.max, this.seed);
	}

	//  Constructor with specified parameters	
	public Benchmark(int size, int min, int max) {
		generate(size, min, max, this.seed);
	}
	
	//  Constructor with specified parameters	
	public Benchmark(int size, int min, int max, int seed) {
		generate(size, min, max, seed);
	}

	//  Constructor with specified parameters
	public void generate(int size, int min, int max, int seed) {
		array = new int[size];
		Random rg = new Random(seed);
	    for (int i = 0; i < size; i++)
	        array[i] = rg.nextInt((max - min + 1) + min);
	}

	public int[] getArray() {
		return array;
	}
	
	public int getSum() {
		int sum = 0;
		for(int i=0; i<array.length; i++)
			sum += array[i];
		return sum;
	}

}
