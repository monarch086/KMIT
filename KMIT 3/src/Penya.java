
public class Penya {
	
	public static void main(String[] args) {

		int days; // ���������� ���� 
		int summa; // ����� �������������
		double stavka; // ������ ���� �� ���� ���������
		double penya; // ����� ������ ����


		days = 50 ;
		summa = 100000;
		stavka = 0.05;

		penya =  ((summa * stavka)/100)*days; // ��������� ����� ������ ����


		System.out.print ("���� �� " + days);
		System.out.print (" ���� �������� ");
		System.out.println (penya + " ������");

	}
}

