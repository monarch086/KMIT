package bars;

public class AverageOfMassive {

	public static void main(String[] args) {
		
		double m [] = {9, 15, 30, 18, 19, 43};
		try{
			double result = Average(m);
			System.out.println("Середнє арифметичне дорівнює "+result);
		} catch (Exception e) {
			System.out.println ("Помилка");
		}
	}

	public static double Average (double [] mas) {
		
		double res = 0;
		
		if (mas.length==0){
			throw new RuntimeException();
		}
		else {
			for (int i = 0; i<mas.length; i++){
				res += mas[i];
			}
			return res/mas.length;
		}			
	}	
}
