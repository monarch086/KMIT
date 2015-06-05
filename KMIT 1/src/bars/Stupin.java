package bars;

import java.util.Scanner;

public class Stupin {

	static Scanner sc = new Scanner(System.in);
		
	public static void main(String[] args) {
		
		System.out.println ("¬вед≥ть степ≥нь");
		double step = sc.nextInt();
		
		System.out.println ("¬вед≥ть число");
		double num = sc.nextInt();
		double res = num;
		
		step--;
		for (int i = 0; i<step; i++){
			res = res*num;
		}
		
		System.out.println ("–езультат: "+res);
		
		step++;
				
		res = Math.pow(num, step);
		
		System.out.println ("–езультат: "+res);
	}

}
