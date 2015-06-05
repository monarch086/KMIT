package BestStudent;

public class MainStudent {

	public static void main(String[] args) {
			
		Student massive [] = new Student [3];
			
		massive[0] = new Student ("Ivan", 0, 0);
		massive[1] = new Student ("Vasya", 90, 91);
		massive[2] = new Student ("Ihor", 50, 50);
			
		double max_rating = massive[0].getAverage();
			
		for (int i = 1; i<massive.length; i++){
			if (massive[i].getAverage()>max_rating){
				max_rating = massive[i].getAverage();
			}
		}
		
		System.out.println("Max rating = "+max_rating);
	}
}
