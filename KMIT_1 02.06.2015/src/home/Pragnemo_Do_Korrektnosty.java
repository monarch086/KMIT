package home;

import java.util.Scanner;

public class Pragnemo_Do_Korrektnosty {

	static Scanner sc;
	static int quantity;
	
	public static void main(String[] args) {

		System.out.println("������ ���� �����:");
		
		try {
			sc = new Scanner (System.in);
			quantity = sc.nextInt();
			quantity = quantity*quantity;
		} catch (Exception e) {
			System.out.println("������ ��������� �����!");
			return;
		}
		
		System.out.println("������� ����� ��������� "+quantity);
		sc.close();
	}
}
