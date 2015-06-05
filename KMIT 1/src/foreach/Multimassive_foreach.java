package foreach;

import java.util.Arrays;

public class Multimassive_foreach {

	public static void main(String[] args) {
        int[][] array = {
                {10, 20, 30},
                {40, 50, 60},
                {70, 80, 90},
        };
        for (int[] row : array) {
            System.out.println(Arrays.toString(row));
        }
    }
	
}
