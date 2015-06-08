
public class Penya {
	
	public static void main(String[] args) {

		int days; // количество дней 
		int summa; // сумма задолженности
		double stavka; // размер пени за день просрочки
		double penya; // общий размер пени


		days = 50 ;
		summa = 100000;
		stavka = 0.05;

		penya =  ((summa * stavka)/100)*days; // вычисляем общий размер пени


		System.out.print ("Пеня за " + days);
		System.out.print (" дней составит ");
		System.out.println (penya + " гривен");

	}
}

