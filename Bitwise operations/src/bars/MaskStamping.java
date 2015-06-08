package bars;

/* Штампування за маскою 
 * 
 * Написати функцію char stamp(char c, byte s), яка повертає значення, 
 * в якому чотири молодші біти співпадають з молодшими бітами числа s, 
 * а всі інші співпадають з відповідними бітами символу c.*/

public class MaskStamping {

	public static void main(String[] args) {
		
		byte var_s = 'a';
		char var_c = 'z';
		char res = 0;
		
		res = stamp (var_c, var_s);
		
		System.out.println(Integer.toString(var_s, 2));
		System.out.println(Integer.toString(var_c, 2));
		System.out.println(Integer.toString(res, 2));
		
	}
	
	public static char stamp (char c, byte s){
		
		s = (byte) (s << 4);
		s = (byte) (s >> 4);
		
		c = (char) (c >> 4);
		c = (char) (c << 4);
		
		s = (byte) (s|c);
		
		return (char) s;
	}

}
