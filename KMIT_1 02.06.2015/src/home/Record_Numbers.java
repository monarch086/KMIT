package home;

import java.util.Scanner;

public class Record_Numbers {

	public static void main(String[] args) {
		
		System.out.println("������ ������� �������� ������ M:");
		Scanner sc = new Scanner (System.in);
		int quantity = sc.nextInt();
		
		int m [] = new int [quantity];
		int min = 0;
		
		System.out.println("������ �������� ������ M:");
		
		for (int x=0; x<quantity; x++){
			m[x] = sc.nextInt();
		}
		
		min = m[0];
		
		for (int x=0; x<quantity; x++){
			if (m[x]<min){
				min = m[x];
			}	
		}
		
		System.out.println("��������� ������� ������ ��������� "+min);
		sc.close();
	}

}
