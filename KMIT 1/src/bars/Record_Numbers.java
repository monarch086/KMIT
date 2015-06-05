package bars;

import java.util.Scanner;

public class Record_Numbers {
	
	static Scanner sc = new Scanner(System.in);

	public static void main (String [] args){
		
		System.out.println ("Введіть кількість");
		int a = sc.nextInt();
		
		int mas[] = new int [a];
		
		for (int i = 0; i<a; i++){
			System.out.println ("Введіть "+i+"-е число");
			mas [i] = sc.nextInt();
		}
		
		int min = mas[0];
		for (int i = 0; i<a; i++){
			if (min>mas[i]){
				min = mas[i];
			}
		}
		
		System.out.println ("Найменше число: "+min);
		
		sc.close();
	}
	
}
