package bars;

public class Arguments {

	public static void main(String[] args){
		
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);
		System.out.println(args[3]);
		
		double result = Double.parseDouble(args[0]);
		result = result + Double.parseDouble(args[1]);
		result = result + Double.parseDouble(args[2]);
		result = result + Double.parseDouble(args[3]);
		
		System.out.println(result);
		
	}
	
}
