package bars;

import java.util.Scanner;

public class Record_Numbers {
	
	static Scanner sc = new Scanner(System.in);

	public static void main (String [] args){
		
		System.out.println ("������ �������");
		int a = sc.nextInt();
		
		int mas[] = new int [a];
		
		for (int i = 0; i<a; i++){
			System.out.println ("������ "+i+"-� �����");
			mas [i] = sc.nextInt();
		}
		
		int min = mas[0];
		for (int i = 0; i<a; i++){
			if (min>mas[i]){
				min = mas[i];
			}
		}
		
		System.out.println ("�������� �����: "+min);
		
		sc.close();
	}
	
}
