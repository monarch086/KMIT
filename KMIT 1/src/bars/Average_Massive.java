package bars;

public class Average_Massive {

	public static void main(String[] args) {
		
		double m[] = {15, 25, 18, 39};
		try{
			double result = average(m);
			System.out.println ("Середнє арифметичне масиву становить: "+result);
		} catch (Exception e){
			System.out.println ("Помилка!");
		}
	}

	public static double average (double [] mas) {
		double sum = 0;
		
		if (mas.length == 0) {
			throw new RuntimeException ();
		}
		
		else {
			for (int i = 0; i < mas.length; i++){
				sum += mas [i];
			}
			return sum/mas.length;
		}
	}
	
}
