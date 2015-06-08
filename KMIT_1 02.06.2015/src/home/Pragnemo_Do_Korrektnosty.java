package home;

import java.util.Scanner;

public class Pragnemo_Do_Korrektnosty {

	static Scanner sc;
	static int quantity;
	
	public static void main(String[] args) {

		System.out.println("¬вед≥ть ц≥ле число:");
		
		try {
			sc = new Scanner (System.in);
			quantity = sc.nextInt();
			quantity = quantity*quantity;
		} catch (Exception e) {
			System.out.println("¬вед≥ть нормальне число!");
			return;
		}
		
		System.out.println(" вадрат числа становить "+quantity);
		sc.close();
	}
}
