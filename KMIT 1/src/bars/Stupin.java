package bars;

import java.util.Scanner;

public class Stupin {

	static Scanner sc = new Scanner(System.in);
		
	public static void main(String[] args) {
		
		System.out.println ("������ ������");
		double step = sc.nextInt();
		
		System.out.println ("������ �����");
		double num = sc.nextInt();
		double res = num;
		
		step--;
		for (int i = 0; i<step; i++){
			res = res*num;
		}
		
		System.out.println ("���������: "+res);
		
		step++;
				
		res = Math.pow(num, step);
		
		System.out.println ("���������: "+res);
	}

}
