package bars;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class bufferedReader_number {
	
	public static void main(String[] args) throws Exception {
	
			BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
			System.out.println("Please enter number");
			String s = br.readLine();
			
			int number = Integer.parseInt(s);
			System.out.println("Result = "+(number*number));
	}
}


