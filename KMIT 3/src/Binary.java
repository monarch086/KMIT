
public class Binary {

	public static void main(String[] args) {
		
		int var = 35;
		
		System.out.println(Integer.toString(var, 2));
		
		int a = var;
		int b;
		StringBuilder res = new StringBuilder();
        while(a !=0 ) {  
            b = a%2;  
            res.append(b);
            a = a/2;
        }
        res.reverse();
        System.out.println(res);
	}

}
