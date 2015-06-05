package bars;

import java.util.Scanner;

public class Korrektnist {

	static Scanner sc = new Scanner(System.in);
	static int a;
	
	public static void main(String[] args) {
		
		System.out.println ("¬вед≥ть ц≥ле число");
		try{
			a = sc.nextInt();
		} catch (Exception e) {
			System.out.println ("¬вед≥ть нормальне число");
			return;
		}

		System.out.println (" вадрат введеного числа: "+a*a);
		
	}

}
