package bars;

import java.util.Scanner;

public class Progression {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println ("������ ������� �����");
		int quantity = sc.nextInt();
		int massive [] = new int[quantity];
		
		for (int i = 0; i<quantity; i++){
			System.out.println ("������  �����");
			massive[i] = sc.nextInt();
		}

		boolean result = false;
		int koef = massive[1]/massive[0];
		
		quantity--;
		for (int i = 0; i<quantity; i++){
			if ((massive[i+1]/massive[i])==koef){
				result = true;
			}
			else {
				result = false;
				break;
			}
		}
		
		if (result == true){
			System.out.println ("������ ����� ���������� ����������� �������� � ������������ "+koef);
		}
		else System.out.println ("������ ����� �� ���������� ����������� ��������");
		
	}

}
