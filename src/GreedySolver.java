public class GreedySolver {
	public GreedySolver(){
	}
	
	public boolean solveBool(int[] instance, int target){
		for (int i = instance.length-1; i >= 0; i--) {
			if (target == instance[i])
				return true;
			else if (target > instance[i])
				target -=  instance[i];
		}
		
		return false;
	}
	
	public int solveInt(int[] instance, int target){
		int solution = 0;
		for (int i = instance.length-1; i >= 0; i--) {
			if (target >= solution + instance[i])
				solution += instance[i];
		}
		return solution;
	}
}