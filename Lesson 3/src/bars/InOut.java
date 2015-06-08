package bars;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class InOut {
	
	public static void main(String[] args) throws Exception {
	
			BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
			System.out.println("Please enter your name");
			String s = br.readLine();
			System.out.println("Hello, "+s);
			//throw new Exception();
	}
}


