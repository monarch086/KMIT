
public class Fibonacci {

	public static void main(String[] args) {

		//з використанням трьох змінних
		int prev = 0;
		int next = 1;
		int res;
		
		for (int i=2; i<10; i++){
			res = next+prev;
			prev = next;
			next = res;
			System.out.println ("For "+i+" result = "+res);
		}

		//з використанням двох змінних
		prev = 0;
		res = 1;
		
		for (int i=2; i<10; i++){
			res = res+prev;
			prev = res-prev;
			System.out.println ("For "+i+" result = "+res);
		}
	}

}
