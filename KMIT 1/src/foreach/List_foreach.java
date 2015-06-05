package foreach;

import java.util.List;

import static java.util.Arrays.asList;

public class List_foreach {

	public static void main(String[] args) {
        List<String> list = asList("A", "B", "C");
        // foreach
        for (String elem : list) {
            System.out.print(" " + elem);
        }
    }
}
