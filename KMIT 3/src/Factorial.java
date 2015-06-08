import java.math.BigInteger;


public class Factorial {

	public static void main(String[] args) {
		
		BigInteger result=BigInteger.valueOf(1);
				
			for (int i=1; i<101; i++){
				result = result.multiply(BigInteger.valueOf(i));
				System.out.println ("For "+i+" result = "+result);
			}
		
	}

}
