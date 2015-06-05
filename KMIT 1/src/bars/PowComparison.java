package bars;

public class PowComparison {
	public static void main( String[] args) {
		
		final int base = 2;
		final int power = 10;
		
		long timeStamp = System.nanoTime();
		double res = Math.pow(base,power);
		long timeStamp2 = System.nanoTime();
		System.out.println(timeStamp2 - timeStamp);
		
		long timeStamp3 = System.nanoTime();
		long res2 = 1;
		for (int i = 1; i <= power; i++) {
			res2 *= base;
		}
		long timeStamp4 = System.nanoTime();
		System.out.println(timeStamp4 - timeStamp3);
		
		double resultFinal = (timeStamp2 - timeStamp)/(timeStamp4 - timeStamp3);
		System.out.println(res);
		System.out.println(res2);
		System.out.println(resultFinal);
	}
}
