package bars;

import java.util.Scanner;

public class Korrektnist {

	static Scanner sc = new Scanner(System.in);
	static int a;
	
	public static void main(String[] args) {
		
		System.out.println ("������ ���� �����");
		try{
			a = sc.nextInt();
		} catch (Exception e) {
			System.out.println ("������ ��������� �����");
			return;
		}

		System.out.println ("������� ��������� �����: "+a*a);
		
	}

}
