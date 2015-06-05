package BestStudent;

public class Student {

	String fio;
	int javaMark;
	int mathMark;
	
	Student (String fio, int javaMark, int mathMark){
		this.fio = fio;
		this.javaMark = javaMark;
		this.mathMark = mathMark;
	}

	
	double getAverage(){
		return (javaMark + mathMark)/2.;
	}
}
