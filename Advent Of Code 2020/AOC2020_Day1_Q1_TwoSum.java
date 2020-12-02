/*
The Elves in accounting just need you to fix your expense report 
(your puzzle input); apparently, something isn't quite adding up. 

Specifically, they need you to find the two entries that sum to 
2020 and then multiply those two numbers together. For example, 
suppose your expense report contained the following:

1721
979
366
299
675
1456

In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 
1721 * 299 = 514579, so the correct answer is 514579. 

Of course, your expense report is much larger. Find the two entries 
that sum to 2020; what do you get if you multiply them together?
*/

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class TwoSum {

    static Map<Integer,Boolean> readInputs() {
        Map<Integer,Boolean> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextInt()) {
            map.put(scanner.nextInt(), true);
        }
        return map;
    }

    static int[] findTwoSum(Map<Integer,Boolean> map, int sum) {
        for (Integer input : map.keySet()) {
            int difference = sum - input;
            if (map.containsKey(difference)) {
                return new int[]{input, difference};
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<Integer,Boolean> map = readInputs();
        int[] result = findTwoSum(map, 2020);
        System.out.println(result[0] * result[1]);
    }
}
