package home;

import java.util.Scanner;

public class Best_Student {

	public static void main(String[] args) {
		
		System.out.println("¬вед≥ть к≥льк≥сть студент≥в:");
		Scanner sc = new Scanner (System.in);
		int quantity = sc.nextInt();
		
		Student students [] = new Student [quantity];
		
		for (int x=0; x<quantity; x++){
			System.out.println("¬вед≥ть ≥м'€ студента є"+x+" :");
			students[x].setName(sc.next());
			System.out.println("¬вед≥ть оц≥нку студента є"+x+" з предмету Java:");
			students[x].setSubject_java(sc.nextInt());
			System.out.println("¬вед≥ть оц≥нку студента є"+x+" з предмету Math:");
			students[x].setSubject_math(sc.nextInt());
		}
		
		int max_rate = 0;
		int index_of_best = 0;
		
		for (int x=0; x<quantity; x++){
			int rate = (students[x].getSubject_java() + students[x].getSubject_math())/2;
			if (rate>max_rate){
				max_rate = rate;
				index_of_best = x;
			}
				
		}
		
		System.out.println("≤мена студент≥в з найвищим середн≥м рейтингом:");
		for (int x=0; x<quantity; x++){
			int rate = (students[x].getSubject_java() + students[x].getSubject_math())/2;
			if (rate==max_rate){
				System.out.println(students[x].getName()+students[x].getSubject_java()+students[x].getSubject_math());
				System.out.println("–ейтинг студента: "+rate);
			}
		}
		
		sc.close();

	}
}
